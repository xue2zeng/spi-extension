package com.zeng.spi.extension.util;

/**
 * Helper Class for hold a value.
 *
 * @param <T> the type parameter
 */
public class Holder<T>
{

	private volatile T value;

	/**
	 * @param value
	 */
	public void set(final T value)
	{
		this.value = value;
	}

	/**
	 * @return T
	 */
	public T get()
	{
		return this.value;
	}

}

