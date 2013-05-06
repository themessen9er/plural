package com.plural.java;

import java.net.URL;
import java.util.Set;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The annotation processor for {@link Tag}.
 * This interface describes a class that processes java compilation units and calls the
 * adequate Plural services in order to build the concept tree.
 */
public interface TagProcessor {

	/**
	 * Processes the resource at the specified {@link URL}
	 * looking for the {@link Tag} annotation and processing it
	 * @param url The {@link URL} to the resource to be processed
	 */
	public void process(URL url);
	
	/**
	 * Processes the resource at the specified {@link URL}s
	 * looking for the {@link Tag} annotation and processing it
	 * @param set The Set of {@link URL}s to the resources to be processed
	 */
	public void process(Set<URL> set);
	
}
