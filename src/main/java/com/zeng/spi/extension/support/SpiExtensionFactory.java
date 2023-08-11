package com.zeng.spi.extension.support;

import java.util.Optional;

import com.zeng.spi.extension.ExtensionFactory;
import com.zeng.spi.extension.ExtensionLoader;
import com.zeng.spi.extension.annotation.SPI;


/**
 * The spi extension implementation factory
 */
public class SpiExtensionFactory implements ExtensionFactory
{

	@Override
	public <T> T getExtension(final String key, final Class<T> clazz)
	{
		return Optional.ofNullable(clazz).filter(Class::isInterface).filter(cls -> cls.isAnnotationPresent(SPI.class))
				.map(ExtensionLoader::getExtensionLoader).map(ExtensionLoader::getDefaultActivate).orElse(null);
	}

}
