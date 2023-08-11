package com.zeng.spi.extension;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zeng.spi.extension.annotation.Activate;
import com.zeng.spi.extension.annotation.SPI;
import com.zeng.spi.extension.util.Holder;


/**
 * The type Extension loader. This is done by loading the properties file.
 *
 * @param <T> the type parameter
 * @see <a href=
 *      "https://github.com/apache/dubbo/blob/master/dubbo-common/src/main/java/org/apache/dubbo/common/extension/ExtensionLoader.java">ExtensionLoader</a>
 */
@SuppressWarnings("all")
public final class ExtensionLoader<T>
{

	private static final Logger LOG = LoggerFactory.getLogger(ExtensionLoader.class);
	/**
	 * SPI配置扩展的文件位置，扩展文件命名格式为SPI接口的全路径名，如：com.zeng.spi.extension.TestAnnotationSPI
	 */
	private static final String SPI_EXTENSION_DIRECTORY = "META-INF/spi-extension/";
	/**
	 * 扩展接口{@link Class}
	 */
	private final Class<T> type;
	/**
	 * 扩展接口和扩展加载器 {@link ExtensionLoader} 的缓存
	 */
	private static final Map<Class<?>, ExtensionLoader<?>> LOADERS = new ConcurrentHashMap<>();
	/**
	 * 扩展class 和 扩展点的实现对象的缓存
	 */
	private final Map<Class<?>, Object> activateInstances = new ConcurrentHashMap<>();
	/**
	 * 保存"扩展"实现的缓存{@link Class}
	 */
	private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
	/**
	 * 保存"扩展名"对应的扩展对象的Holder的缓存
	 */
	private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
	/**
	 * 保存扩展名缓存名称
	 */
	private final ConcurrentMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();

	/**
	 * 扩展点默认的 "名称" 缓存
	 */
	private String cachedDefaultName;

	/**
	 * Instantiates a new Extension loader.
	 *
	 * @param type
	 */
	public ExtensionLoader(final Class<T> type)
	{
		this.type = type;
		if (!Objects.equals(type, ExtensionFactory.class))
		{
			ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getExtensionClasses();
		}
	}

	/**
	 * @return
	 */
	private Map<String, Class<?>> getExtensionClasses()
	{
		// 扩区SPI扩展实现的缓存，对应的就是扩展文件中的 key=value
		Map<String, Class<?>> classes = this.cachedClasses.get();
		if (Objects.isNull(classes))
		{
			synchronized (this.cachedClasses)
			{
				classes = this.cachedClasses.get();
				if (Objects.isNull(classes))
				{
					// 加载扩展
					classes = loadExtensionClass();
					// 缓存扩展实现集合
					this.cachedClasses.set(classes);
				}
			}
		}
		return classes;
	}

	/**
	 * @return
	 */
	private Map<String, Class<?>> loadExtensionClass()
	{
		// 扩展接口Class，必须包含SPI注解
		final SPI annotation = this.type.getAnnotation(SPI.class);
		if (Objects.nonNull(annotation))
		{
			final String value = annotation.value();
			if (StringUtils.isNoneBlank(value))
			{
				this.cachedDefaultName = value;
			}
		}
		final Map<String, Class<?>> classes = new HashMap<>(16);
		// 从文件加载扩展实现
		loadDirectory(classes);
		return classes;
	}

	/**
	 * @param classes
	 */
	private void loadDirectory(final Map<String, Class<?>> classes)
	{
		// 文件名
		final String fileName = SPI_EXTENSION_DIRECTORY + this.type.getName();
		try
		{
			final ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
			// 读取配置文件
			final Enumeration<URL> urls = (Objects.nonNull(classLoader) ? classLoader.getResources(fileName)
					: ClassLoader.getSystemResources(fileName));
			if (Objects.nonNull(urls))
			{
				while (urls.hasMoreElements())
				{
					final URL url = urls.nextElement();
					// 加载资源
					loadResources(classes, url);
				}
			}
		}
		catch (final IOException e)
		{
			LOG.error("Load extension class error {}", fileName, e);
		}
	}

	private void loadResources(final Map<String, Class<?>> classes, final URL url)
	{
		// 读取文件到Properties，遍历Properties，得到配置文件key（名字）和value（扩展实现的Class）
		try (InputStream inputStream = url.openStream())
		{
			final Properties properties = new Properties();
			properties.load(inputStream);
			properties.forEach((k, v) -> {
				final String name = (String) k;
				final String classPath = StringUtils.isBlank((String) v) ? name : (String) v;
				if (StringUtils.isNoneBlank(name) && StringUtils.isNoneBlank(classPath))
				{
					try
					{
						// 加载扩展实现Class，就是想其缓存起来，缓存到集合中
						loadClass(classes, name, classPath);
					}
					catch (final ClassNotFoundException e)
					{
						LOG.error("Load name {} extension class {} not found", name, classPath, e);
					}
				}
			});
		}
		catch (final IOException e)
		{
			LOG.error("Load extension resources error {}", url, e);
		}
	}

	private void loadClass(final Map<String, Class<?>> classes, final String name, final String classPath)
			throws ClassNotFoundException
	{
		final ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
		// 反射创建扩展实现的Class
		final Class<?> subClass = Objects.nonNull(classLoader) ? Class.forName(classPath, false, classLoader)
				: Class.forName(classPath);
		// 扩展实现的Class必须是扩展接口的实现类
		if (!this.type.isAssignableFrom(subClass))
		{
			throw new IllegalArgumentException("Load extension class error, " + subClass + " not sub type of " + this.type);
		}
		// 扩展实现要有Activate注解
		if (!subClass.isAnnotationPresent(Activate.class))
		{
			throw new IllegalStateException(
					"Load extension resources error," + subClass + " without @" + Activate.class + " annotation");
		}
		// 缓存扩展实现Class
		final Class<?> oldClass = classes.get(name);
		if (Objects.isNull(oldClass))
		{
			classes.put(name, subClass);
		}
		else
		{
			LOG.error("Load extension class error, Duplicate class oldClass is " + oldClass + "subClass is" + subClass);
		}
	}

	/**
	 * Gets extension loader.
	 *
	 * @param <T>   the type parameter
	 * @param clazz the clazz
	 * @return the extension loader
	 */
	public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz)
	{
		// 参数非空校验
		Objects.requireNonNull(clazz, "Extension clazz is null");
		// 参数接口校验
		if (!clazz.isInterface())
		{
			throw new IllegalArgumentException("Extension clazz (" + clazz + ") is not interface.");
		}
		// 参数包含@SPI注解校验
		if (!clazz.isAnnotationPresent(SPI.class))
		{
			throw new IllegalArgumentException("Extension clazz (" + clazz + ") without @" + SPI.class + " annnotation.");
		}
		// 从缓存中获取扩展加载器，如果存在直接返回，如果不存在就创建一个扩展加载器并放到缓存中
		final ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) LOADERS.get(clazz);
		if (Objects.nonNull(extensionLoader))
		{
			return extensionLoader;
		}
		LOADERS.putIfAbsent(clazz, new ExtensionLoader<>(clazz));
		return (ExtensionLoader<T>) LOADERS.get(clazz);
	}

	/**
	 * Return default activate service instance, return <code>null</code> if it's not configured.
	 *
	 * @return the default activate extension service instance
	 */
	public T getDefaultActivate()
	{
		getExtensionClasses();
		if (StringUtils.isNoneBlank(this.cachedDefaultName))
		{
			return getActivate(this.cachedDefaultName);
		}
		return null;
	}

	/**
	 * Find the activate extension service with the given name. If the specified name is not found, then
	 * {@link IllegalStateException} will be thrown.
	 *
	 * @param name activate extension service instance name
	 * @return the activate extension service instance
	 */
	public T getActivate(final String name)
	{
		// 文件中的扩展名key
		Objects.requireNonNull(name, "Activate name is null.");

		// 根据扩展名key获取扩展对象存储缓存中的实例
		Holder<Object> holder = this.cachedInstances.get(name);
		// 如果扩展对象是空的，创建一个扩展对象并存储在缓存中
		if (Objects.isNull(holder))
		{
			this.cachedInstances.putIfAbsent(name, new Holder<>());
			holder = this.cachedInstances.get(name);
		}
		// 从扩展对象的存储中获取扩展对象
		Object instance = holder.get();
		// 如果对象是空的，就触发创建扩展，否则直接返回扩展对象
		if (Objects.isNull(instance))
		{
			synchronized (this.cachedInstances)
			{
				instance = holder.get();
				if (Objects.isNull(instance))
				{
					// 创建扩展对象
					instance = createExtension(name);
					holder.set(instance);
				}
			}
		}
		return (T) instance;
	}

	/**
	 * Create extension service instance by name
	 *
	 * @param name extension service name
	 * @return extension service instance
	 * @throws IllegalStateException failed to create an extension.
	 */
	protected Object createExtension(final String name)
	{
		// 根据扩展名字获取扩展的Class，从Holder中获取 key-value缓存，然后根据名字从Map中获取扩展实现Class
		final Class<?> classEntity = this.getExtensionClasses().get(name);
		if (Objects.isNull(classEntity))
		{
			throw new IllegalArgumentException(name + " name is error.");
		}
		Object o = this.activateInstances.get(classEntity);
		if (null == o)
		{
			try
			{
				// 创建扩展对象并放到缓存中
				this.activateInstances.putIfAbsent(classEntity, classEntity.getDeclaredConstructor().newInstance());
				o = this.activateInstances.get(classEntity);
			}
			catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e)
			{
				throw new IllegalStateException("Extension instance(name: " + name + ", class: " + classEntity
						+ ")  could not be instantiated: " + e.getMessage(), e);
			}
		}
		return o;
	}
}
