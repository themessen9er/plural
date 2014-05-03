package com.bacon.java;

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
	 * The delimiter character for the tags.
	 * e.g. @Tag("one, two, three")
	 */
	public static final char DELIMITER = ',';
	
	/**
	 * @return The set of related concepts
	 */
	String value();

}
