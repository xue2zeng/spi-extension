package com.zeng.spi.extension.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Join Adding this annotation to a class indicates joining the extension mechanism.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(
		{ ElementType.TYPE, ElementType.METHOD })
public @interface Activate
{

	/**
	 * It will be sorted according to the current serial number.
	 *
	 * @return int.
	 */
	int order() default 0;
}
