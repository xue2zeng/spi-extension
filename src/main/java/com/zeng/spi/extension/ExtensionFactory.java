package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.SPI;


/**
 * The interface Extension factory.
 */
@SPI
public interface ExtensionFactory
{
	/**
	 * Gets Extension.
	 *
	 * @param <T>   the type parameter
	 * @param key   the key
	 * @param clazz the clazz
	 * @return the extension
	 */
	<T> T getExtension(String key, Class<T> clazz);
}
