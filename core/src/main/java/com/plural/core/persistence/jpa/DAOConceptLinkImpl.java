package com.plural.core.persistence.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.plural.core.persistence.Concept;
import com.plural.core.persistence.ConceptLink;
import com.plural.core.persistence.DAO;
import com.plural.core.persistence.DAOConceptLink;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link DAO} for {@link ConceptLinkEntity}
 */
public class DAOConceptLinkImpl extends DAOImpl<ConceptLink> implements DAOConceptLink {

	/**
	 * @param entityManager The JPA entity manager
	 */
	@Inject
	public DAOConceptLinkImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	@Override
	public ConceptLink create(Concept cA, Concept cB) {
		assert(cA instanceof ConceptEntity);
		assert(cB instanceof ConceptEntity);
		return create(new ConceptLinkEntity((ConceptEntity) cA, (ConceptEntity) cB));
	}

	@Override
	public List<ConceptLink> getLinksForConcept(Concept concept) {
		assert(concept instanceof ConceptEntity);
		ConceptEntity conceptEntity = (ConceptEntity) concept;
		
		return new ArrayList<ConceptLink>(
				getEntityManager().createQuery(
				"SELECT l FROM " + getEntityClass().getCanonicalName() + " AS l " + 
				"WHERE l.conceptA.id=:cID OR l.conceptB.id=:cID",
				ConceptLinkEntity.class)
				.setParameter("cID", conceptEntity.getID())
				.getResultList());
	}
	
	@Override
	protected String getDefaultKeyFieldName() {
		return "id";
	}

	@Override
	protected Class<ConceptLinkEntity> getEntityClass() {
		return ConceptLinkEntity.class;
	}

}
