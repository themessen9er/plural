package com.plural.core.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.plural.core.persistence.Concept;
import com.plural.core.persistence.ConceptLink;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link ConceptLink}
 */
@Entity(name = "PLURAL_CONCEPT_LINK")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_concept_a", "id_concept_b"})) 
public class ConceptLinkEntity extends BaseEntity implements ConceptLink {

	@ManyToOne(optional = false, targetEntity = ConceptEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_concept_a", insertable = true, updatable = false, nullable = false)
	private ConceptEntity conceptA;
	
	@ManyToOne(optional = false, targetEntity = ConceptEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_concept_b", insertable = true, updatable = false, nullable = false)
	private ConceptEntity conceptB;
	
	@Column(name = "access_count_concept_a", insertable = true, updatable = true, nullable = false)
	private long accessCountToA;
	
	@Column(name = "access_count_concept_b", insertable = true, updatable = true, nullable = false)
	private long accessCountToB;

	
	/**
	 * @param a One concept
	 * @param b Another concept
	 */
	public ConceptLinkEntity(ConceptEntity a, ConceptEntity b) {
		setConceptA(a);
		setConceptB(b);
	}
	
	@Override
	public Concept getLinkedConcept(Concept originConcept) {
		assert(originConcept.getName().equals(conceptA.getName())
				||  originConcept.getName().equals(conceptB.getName()));
		return originConcept.getName().equals(conceptA.getName()) ? conceptB : conceptA;
	}

	@Override
	public void recordAccess(Concept from) {
		assert(from.getName().equals(conceptA.getName())
				||  from.getName().equals(conceptB.getName()));
		if(from.getName().equals(conceptA.getName())) {
			setAccessCountToB(this.accessCountToB + 1);
		} else {
			setAccessCountToA(this.accessCountToA + 1);
		}
	}

	@Override
	public long getAccessCount(Concept from) {
		assert(from.getName().equals(conceptA.getName())
				||  from.getName().equals(conceptB.getName()));
		return from.getName().equals(conceptA.getName()) ? this.accessCountToB : this.accessCountToA;
	}

	/**
	 * @param conceptA the conceptA to set
	 */
	private void setConceptA(ConceptEntity conceptA) {
		this.conceptA = conceptA;
	}

	/**
	 * @param conceptB the conceptB to set
	 */
	private void setConceptB(ConceptEntity conceptB) {
		this.conceptB = conceptB;
	}

	/**
	 * @param accessCountToA the accessCountToA to set
	 */
	private void setAccessCountToA(long accessCountToA) {
		this.accessCountToA = accessCountToA;
	}

	/**
	 * @param accessCountToB the accessCountToB to set
	 */
	private void setAccessCountToB(long accessCountToB) {
		this.accessCountToB = accessCountToB;
	}

}
