package com.zeng.spi.extension.strategy;

import com.zeng.spi.extension.LoadingStrategy;


/**
 *
 */
public class ExtensionLoadingStrategy implements LoadingStrategy
{

	@Override
	public String directory()
	{
		// SPI配置扩展的文件位置，扩展文件命名格式为SPI接口的全路径名，如：com.zeng.spi.extension.TestAnnotationSPI
		return "META-INF/spi-extension/";
	}

	@Override
	public boolean overridden()
	{
		return true;
	}

	@Override
	public String getName()
	{
		return "SPI-EXTENSION";
	}

	@Override
	public int getPriority()
	{
		return NORMAL_PRIORITY;
	}
}
