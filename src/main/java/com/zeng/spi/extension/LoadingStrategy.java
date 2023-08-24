package com.zeng.spi.extension;

import com.zeng.spi.extension.lang.Prioritized;

/**
 *
 */
public interface LoadingStrategy extends Prioritized
{
	/**
	 * @return load directory
	 */
	String directory();

	/**
	 * Indicates current {@link LoadingStrategy} supports overriding other lower prioritized instances or not.
	 *
	 * @return if supports, return <code>true</code>, or <code>false</code>
	 * @since 2.7.7
	 */
	default boolean overridden()
	{
		return false;
	}

	/**
	 * @return class name
	 */
	default String getName()
	{
		return this.getClass().getSimpleName();
	}
}
