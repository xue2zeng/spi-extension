package com.zeng.spi.extension.lang;

import java.util.Comparator;


/**
 * {@code Prioritized} interface can be implemented by objects that should be sorted, for example the tasks in
 * executable queue.
 *
 * @since 2.7.5
 */
public interface Prioritized extends Comparable<Prioritized>
{

	/**
	 * The {@link Comparator} of {@link Prioritized}
	 */
	Comparator<Object> COMPARATOR = (one, two) -> {
		final boolean b1 = one instanceof Prioritized;
		final boolean b2 = two instanceof Prioritized;
		if (b1 && !b2)
		{ // one is Prioritized, two is not
			return -1;
		}
		else if (b2 && !b1)
		{ // two is Prioritized, one is not
			return 1;
		}
		else if (b1 && b2)
		{ //  one and two both are Prioritized
			return ((Prioritized) one).compareTo((Prioritized) two);
		}
		else
		{ // no different
			return 0;
		}
	};

	/**
	 * The maximum priority
	 */
	int MAX_PRIORITY = Integer.MIN_VALUE;

	/**
	 * The minimum priority
	 */
	int MIN_PRIORITY = Integer.MAX_VALUE;

	/**
	 * Normal Priority
	 */
	int NORMAL_PRIORITY = 0;

	/**
	 * Get the priority
	 *
	 * @return the default is {@link #NORMAL_PRIORITY}
	 */
	default int getPriority()
	{
		return NORMAL_PRIORITY;
	}

	@Override
	default int compareTo(final Prioritized that)
	{
		return Integer.compare(this.getPriority(), that.getPriority());
	}
}
