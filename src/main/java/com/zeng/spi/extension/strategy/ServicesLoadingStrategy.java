package com.zeng.spi.extension.strategy;

import com.zeng.spi.extension.LoadingStrategy;

/**
 *
 */
public class ServicesLoadingStrategy implements LoadingStrategy
{

	@Override
	public String directory()
	{
		return "META-INF/services/";
	}

	@Override
	public boolean overridden()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "SERVICES";
	}

	@Override
	public int getPriority()
	{
		return MIN_PRIORITY;
	}
}
