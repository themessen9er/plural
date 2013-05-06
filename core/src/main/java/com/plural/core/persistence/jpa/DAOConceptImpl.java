package com.plural.core.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.plural.core.persistence.Concept;
import com.plural.core.persistence.DAO;
import com.plural.core.persistence.DAOConcept;
import com.plural.core.persistence.TransactionWrapper;
import com.plural.core.util.Tag;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link DAO} for {@link ConceptEntity}
 */
@Tag({"dao", "persistence", "concept"})
public class DAOConceptImpl extends DAOImpl<Concept> implements DAOConcept {

	/**
	 * @param entityManager The JPA entity manager
	 */
	@Inject
	public DAOConceptImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	@Tag({"daomethod", "persistencemethod", "conceptmethod"})
	public Concept create(final Concept entity) {
		try {
			return new TransactionWrapper<Concept>(this) {

				@Override
				protected Concept executeTransactionCodeBlock() throws Exception {
					try {
						return get(entity.getName());
					} catch(NoResultException e) {
						return superCreate(entity);
					}
				}
			}.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Concept superCreate(Concept c) {
		return super.create(c);
	}

	@Override
	public Concept create(String name, String description) {
		return this.create(new ConceptEntity(name, description));
	}

	@Override
	protected String getDefaultKeyFieldName() {
		return "name";
	}

	@Override
	protected Class<ConceptEntity> getEntityClass() {
		return ConceptEntity.class;
	}

}
