package com.zeng.spi.extension.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * SPI Extend the processing.
 * <p>
 * All spi system reference the apache implementation of
 * <p>
 * <a href=
 * "https://github.com/apache/dubbo/blob/master/dubbo-common/src/main/java/org/apache/dubbo/common/extension">Apache
 * Dubbo Common Extension</a>.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI
{
	/**
	 * default extension name
	 */
	String value() default "";
}
