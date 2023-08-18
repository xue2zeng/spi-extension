package com.zeng.spi.extension;

import java.util.Map;

/**
 *
 */
public interface Protocol
{
	/**
	 * @param key
	 * @return string
	 */
	String getParameter(String key);

	/**
	 * @param key
	 * @return boolean
	 */
	boolean hasParameter(String key);

	/**
	 * @param key
	 * @param defaultValue
	 * @return string
	 */
	String getParameter(String key, String defaultValue);

	/**
	 * @return map
	 */
	Map<String, String> getParameters();
}
