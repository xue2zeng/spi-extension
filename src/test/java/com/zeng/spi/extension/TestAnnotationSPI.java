package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.SPI;


/**
 *
 */
@SPI
public interface TestAnnotationSPI {
	/**
	 * @return string
	 */
	String test();
}
