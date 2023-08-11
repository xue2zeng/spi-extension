package com.zeng.spi.extension;

import com.zeng.spi.extension.annotation.Activate;


/**
 *
 */
@Activate
public class TestAnnotationSPIImpl2 implements TestAnnotationSPI {

	@Override
	public String test() {
		return "SPI Impl 2";
	}

}
