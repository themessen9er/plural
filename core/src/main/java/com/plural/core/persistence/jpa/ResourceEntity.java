package com.plural.core.persistence.jpa;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.plural.core.persistence.Repository;
import com.plural.core.persistence.Resource;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link Resource}
 */
@Entity(name = "PLURAL_RESOURCE")
public class ResourceEntity extends BaseEntity implements Resource {

	@ManyToOne(optional = false, targetEntity = RepositoryEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_repository", insertable = true, updatable = false, nullable = false)
	private RepositoryEntity repository;
	
	@Column(name = "url", insertable = true, nullable = false, updatable = true)
	private String uri;
	
	
	/**
	 * @param repository The {@link Resource}'s {@link Repository}
	 * @param uri The {@link Resource}'s {@link URI}
	 */
	public ResourceEntity(RepositoryEntity repository, URI uri) {
		setRepository(repository);
		setUri(uri);
	}
	
	@Override
	public Repository getRepository() {
		return this.repository;
	}

	@Override
	public URI getURI() {
		try {
			return new URI(this.uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param repository the repository to set
	 */
	private void setRepository(RepositoryEntity repository) {
		this.repository = repository;
	}

	/**
	 * @param uri the uri to set
	 */
	private void setUri(URI uri) {
		this.uri = uri.toString();
	}
	
}
