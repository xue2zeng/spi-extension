package com.zeng.spi.extension.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zeng.spi.extension.ExtensionLoader;


/**
 *
 */
public class PropertiesUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(ExtensionLoader.class);

	/**
	 * @param classLoader
	 * @param resourceName
	 * @return Properties
	 */
	public static Properties loadProperties(final ClassLoader classLoader, final String resourceName)
	{
		final Properties properties = new Properties();
		if (classLoader != null)
		{
			try
			{
				final Enumeration<java.net.URL> resources = classLoader.getResources(resourceName);
				while (resources.hasMoreElements())
				{
					final java.net.URL url = resources.nextElement();
					final Properties props = PropertiesUtils.loadFromUrl(url);
					for (final Map.Entry<Object, Object> entry : props.entrySet())
					{
						final String key = entry.getKey().toString();
						if (properties.containsKey(key))
						{
							continue;
						}
						properties.put(key, entry.getValue().toString());
					}
				}
			}
			catch (final IOException ex)
			{
				LOG.error("", "", "", "load properties failed.", ex);
			}
		}

		return properties;
	}

	/**
	 * @param url
	 * @return Properties
	 */
	public static Properties loadFromUrl(final URL url)
	{
		final Properties properties = new Properties();
		InputStream is = null;
		try
		{
			is = url.openStream();
			properties.load(is);
		}
		catch (final IOException e)
		{
			LOG.error("", "", "", "Load stream failed.", e);
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (final IOException e)
				{
					LOG.error("", "", "", "Close input stream failed.", e);
				}
			}
		}
		return properties;
	}
}
