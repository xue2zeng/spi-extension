package com.zeng.spi.extension.constants;

import java.util.regex.Pattern;


/**
 * store common constants
 */
public interface CommonConstants
{
	/**
	 *
	 */
	String GROUP_KEY = "group";
	/**
	 * <code>,</code>分隔符
	 */
	Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
	/**
	 *
	 */
	String REMOVE_VALUE_PREFIX = "-";
	/**
	 *
	 */
	String DEFAULT_KEY = "default";

	/**
	 *
	 */
	Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");
}
