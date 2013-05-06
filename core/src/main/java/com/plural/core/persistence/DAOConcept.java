package com.plural.core.persistence;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The {@link DAO} specification for {@link Concept}
 */
public interface DAOConcept extends DAO<Concept> {

	/**
	 * Creates and persists a new {@link Concept}
	 * @param name the name of the {@link Concept}
	 * @param description the description of the {@link Concept}
	 * @return the persisted {@link Concept} instance
	 */
	Concept create(String name, String description);

}
