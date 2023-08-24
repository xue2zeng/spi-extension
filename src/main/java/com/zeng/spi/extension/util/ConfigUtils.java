package com.zeng.spi.extension.util;

/**
 *
 */
public class ConfigUtils
{
	/**
	 * @param value
	 * @return boolean
	 */
	public static boolean isEmpty(final String value)
	{

		return value == null || value.length() == 0 || "false".equalsIgnoreCase(value) || "0".equalsIgnoreCase(value)
				|| "null".equalsIgnoreCase(value) || "N/A".equalsIgnoreCase(value);
	}
}
