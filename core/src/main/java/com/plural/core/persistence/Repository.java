package com.plural.core.persistence;

import java.net.URL;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The representation of a generic repository where resources can be stored.
 */
public interface Repository extends PluralEntity {

	/**
	 * @return The description of this {@link Repository}
	 */
	public String getDescription();
	
	/**
	 * @return The {@link URL} to the {@link Repository}
	 */
	public URL getURL();
	
	/**
	 * Sets the description for the {@link Repository}
	 * @param description The {@link Repository} description
	 */
	public void setDescription(String description);
	
	/**
	 * Sets the {@link URL} to the {@link Repository}
	 * @param url The {@link Repository} access {@link URL}
	 */
	public void setURL(URL url);
	
}
