package com.zeng.spi.extension.support;

import java.util.Comparator;

import com.zeng.spi.extension.annotation.Activate;


/**
 * @param <T>
 */
public class ActivateComparator<T> implements Comparator<Class<T>>
{
	@Override
	public int compare(final Class<T> o1, final Class<T> o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null)
		{
			return -1;
		}
		if (o2 == null)
		{
			return 1;
		}
		if (o1.equals(o2))
		{
			return 0;
		}

		/**
		 * to support com.zeng.spi.extension.annotation.Activate
		 */
		final Activate that = o1.getAnnotation(Activate.class);
		final Activate anthor = o2.getAnnotation(Activate.class);
		/**
		 * never return 0 even if n1 equals n2, otherwise, o1 and o2 will override each other in collection like HashSet
		 */
		return that.order() > anthor.order() ? -1 : 1;
	}
}
