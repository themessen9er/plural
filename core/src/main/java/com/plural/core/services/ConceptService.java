package com.plural.core.services;

import java.util.Collection;

import com.plural.core.persistence.Concept;


/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 * 
 * The service for the management of {@link Concept}s
 */
public interface ConceptService {

	/**
	 * Records a relation between concepts.
	 * @param relatedConcepts The concepts to be recorded as being related to each other. If any concept does not exist, it will be added.
	 */
	public void linkConcepts(String... relatedConcepts);
	
	/**
	 * Deletes the relations between concepts.
	 * @param concept The original concept from which other concepts will be unlinked.
	 * @param relatedConcepts The related concepts to be unlinked.
	 */
	public void unlinkConcept(String concept, String... relatedConcepts);
	
	/**
	 * Deletes a set of concepts
	 * @param concepts The concepts to be deleted
	 */
	public void deleteConcepts(String... concepts);
	
	/**
	 * Gets the concepts related to a given concept
	 * @param concept The original concept
	 * @return A collection of concepts
	 */
	public Collection<String> getRelatedConcepts(String concept);
	
	/**
	 * Searches for concepts with similar names to the parameter string
	 * @param s The search string
	 * @return The matches found
	 */
	public Collection<String> search(String s);
	
}
