package com.zeng.spi.extension.strategy;

import com.zeng.spi.extension.LoadingStrategy;


/**
 *
 */
public class ExtensionInternalLoadingStrategy implements LoadingStrategy
{

	@Override
	public String directory()
	{
		return "META-INF/spi-extension/internal/";
	}

	@Override
	public boolean overridden()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "SPI-EXTENSION-INTERNAL";
	}

	@Override
	public int getPriority()
	{
		return MAX_PRIORITY;
	}

}