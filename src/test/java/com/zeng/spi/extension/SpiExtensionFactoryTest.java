package com.zeng.spi.extension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zeng.spi.extension.support.SpiExtensionFactory;

/**
 *
 */
public class SpiExtensionFactoryTest
{
	SpiExtensionFactory spiExtensionFactory;

	/**
	 *
	 */
	@BeforeEach
	public void init()
	{
		this.spiExtensionFactory = new SpiExtensionFactory();
	}

	/**
	 *
	 */
	@Test
	public void testNull()
	{
		Assertions.assertNull(this.spiExtensionFactory.getExtension("testImpl", TestAnnotationSPI.class));
	}

	/**
	 *
	 */
	@Test
	public void testNonNull()
	{
		Assertions.assertNull(this.spiExtensionFactory.getExtension("testImpl1", TestAnnotationSPI.class));
		Assertions.assertNull(this.spiExtensionFactory.getExtension("testImpl2", TestAnnotationSPI.class));
	}
}
