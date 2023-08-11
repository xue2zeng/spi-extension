package com.zeng.spi.extension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 *
 */
public class ExtensionLoaderTest {
	/**
	 *
	 */
	@Test
	public void getExtensionTest() {
		final TestAnnotationSPI spiImpl1 = ExtensionLoader.getExtensionLoader(TestAnnotationSPI.class).getActivate("testImpl1");
		Assertions.assertEquals(spiImpl1.test(), "SPI Impl 1");

		final TestAnnotationSPI spiImpl2 = ExtensionLoader.getExtensionLoader(TestAnnotationSPI.class).getActivate("testImpl2");
		Assertions.assertEquals(spiImpl2.test(), "SPI Impl 2");
	}
}
