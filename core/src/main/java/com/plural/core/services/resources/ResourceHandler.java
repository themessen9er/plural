package com.plural.core.services.resources;

import java.io.InputStream;
import java.net.URL;

import com.plural.core.persistence.Resource;


/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The handler for {@link Resource} instances.
 * Essentially, this class exists in order to interpret different kinds of resources
 * and make it possible to have specific behavior depending on the {@link Resource}'s kind.
 */
public interface ResourceHandler {

	/**
	 * @param resource A {@link Resource} instance
	 * @return true if the resource can be handled by this {@link ResourceHandler}
	 */
	public boolean handles(Resource resource);
	
	/**
	 * @param resource A {@link Resource} instance
	 * @return The representation of the {@link Resource}'s content in bytes
	 */
	public byte[] getContent(Resource resource);
	
	/**
	 * @param resource A {@link Resource} instance
	 * @return An {@link InputStream} to the representation of the {@link Resource}'s content
	 */
	public InputStream getContentAsStream(Resource resource);
	
	/**
	 * @param resource A {@link Resource} instance
	 * @return The URL to the given {@link Resource}
	 */
	public URL getResourceURL(Resource resource);
	
}
