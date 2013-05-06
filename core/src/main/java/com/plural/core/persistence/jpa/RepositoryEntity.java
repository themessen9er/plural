package com.plural.core.persistence.jpa;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.plural.core.persistence.Repository;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link Repository}
 */
@Entity(name = "PLURAL_REPOSITORY")
public class RepositoryEntity extends BaseEntity implements Repository {

	@Column(name = "description", insertable = true, updatable = true, nullable = false, length = 100, unique = true)
	private String description;
	
	@Column(name = "url", insertable = true, updatable = true, nullable = false, length = 255, unique = true)
	private String url;
	
	private RepositoryEntity() {}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public URL getURL() {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setURL(URL url) {
		this.url = url.toString();
	}

}
