package com.plural.core.persistence;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * A link between two concepts
 */
public interface ConceptLink extends PluralEntity {

	/**
	 * Gets the {@link Concept} linked to the one passed as the argument
	 * @param originConcept The original {@link Concept} with another one linked to it
	 * @return The concept linked to the parameter {@link Concept}
	 */
	public Concept getLinkedConcept(Concept originConcept);
	
	/**
	 * Records an access to a linked {@link Concept} originating in another {@link Concept}
	 * @param from The {@link Concept} instance originating the access
	 */
	public void recordAccess(Concept from);
	
	/**
	 * @param from The {@link Concept} instance originating the accesses
	 * @return The total of accesses to a {@link Concept} originating in the argument {@link Concept}
	 */
	public long getAccessCount(Concept from);
	
}
