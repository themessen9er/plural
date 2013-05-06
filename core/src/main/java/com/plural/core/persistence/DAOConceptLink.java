package com.plural.core.persistence;

import java.util.List;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The {@link DAO} specification for {@link ConceptLink}
 */
public interface DAOConceptLink extends DAO<ConceptLink> {

	/**
	 * Creates and persists a {@link ConceptLink}
	 * @param cA The first {@link Concept}
	 * @param cB The second {@link Concept}
	 */
	ConceptLink create(Concept cA, Concept cB);

	/**
	 * Returns all links referencing the concept in the argument
	 * @param concept The referenced {@link Concept}
	 * @return a {@link List} of {@link ConceptLink}
	 */
	List<ConceptLink> getLinksForConcept(Concept concept);

}
