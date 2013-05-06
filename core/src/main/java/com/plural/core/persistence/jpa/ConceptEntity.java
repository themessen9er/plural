package com.plural.core.persistence.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.plural.core.persistence.Concept;
import com.plural.core.persistence.Resource;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of the {@link Concept} entity
 */
@Entity(name = "PLURAL_CONCEPT")
public class ConceptEntity extends BaseEntity implements Concept {

	@Column(name = "name", insertable = true, updatable = false, nullable = false, unique = true, length = 100)
	private String name;

	@Column(name = "description", insertable = true, updatable = true, nullable = true, unique = false, length = 255)
	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ResourceEntity.class)
    @JoinTable(name = "PLURAL_CONCEPT_RESOURCE", joinColumns = { @JoinColumn(name = "id_concept") }, inverseJoinColumns = { @JoinColumn(name = "id_resource") })
	private List<ResourceEntity> resources;


	@SuppressWarnings("unused")
	private ConceptEntity() {}

	/**
	 * @param name The {@link Concept} name
	 * @param description The {@link Concept} description
	 */
	public ConceptEntity(String name, String description) {
		setName(name);
		setDescription(description);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Collection<Resource> getResources() {
		return new ArrayList<Resource>(this.resources);
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void addResource(Resource resource) {
		assert(resource instanceof ResourceEntity);
		this.resources.add((ResourceEntity) resource);
	}

	private void setName(String name) {
		this.name = name;
	}

}
