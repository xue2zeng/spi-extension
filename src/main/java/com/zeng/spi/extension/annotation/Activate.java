package com.zeng.spi.extension.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zeng.spi.extension.ExtensionLoader;
import com.zeng.spi.extension.Protocol;


/**
 * Activate Adding this annotation to a class indicates joining the extension mechanism.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(
		{ ElementType.TYPE, ElementType.METHOD })
public @interface Activate
{
	/**
	 * Activate the current extension when one of the groups matches. The group passed into
	 * {@link ExtensionLoader#getActivateExtension(Protocol, String, String)} will be used for matching.
	 *
	 * @return group names to match
	 * @see ExtensionLoader#getActivateExtension(Protocol, String, String)
	 */
	String[] group() default {};

	/**
	 * Activate the current extension when the specified keys appear in the URL's parameters.
	 * <p>
	 * For example, given <code>@Activate("cache, validation")</code>, the current extension will be return only when
	 * there's either <code>cache</code> or <code>validation</code> key appeared in the URL's parameters.
	 * </p>
	 *
	 * @return URL parameter keys
	 * @see ExtensionLoader#getActivateExtension(Protocol, String)
	 * @see ExtensionLoader#getActivateExtension(Protocol, String, String)
	 */
	String[] value() default {};

	/**
	 * It will be sorted according to the current serial number.
	 *
	 * @return int.
	 */
	int order() default 0;
}
