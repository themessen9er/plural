package com.plural.core.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * Utility annotation for the tagging of concepts in Java code
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Tag {

	/**
	 * @return The set of related concepts
	 */
	String[] value();

}
