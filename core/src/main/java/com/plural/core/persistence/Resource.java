package com.plural.core.persistence;

import java.net.URI;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * A resource related to a concept
 */
public interface Resource extends PluralEntity {

	/**
	 * @return Returns the repository where the resource is stored
	 */
	public Repository getRepository();
	
	/**
	 * @return Returns the {@link URI} of this resource.
	 */
	public URI getURI();
	
}
