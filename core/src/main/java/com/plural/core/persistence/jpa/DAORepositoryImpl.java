package com.plural.core.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.plural.core.persistence.DAO;
import com.plural.core.persistence.DAORepository;
import com.plural.core.persistence.Repository;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link DAO} for {@link RepositoryEntity}
 */
public class DAORepositoryImpl extends DAOImpl<Repository> implements DAORepository {

	/**
	 * @param entityManager The JPA entity manager
	 */
	@Inject
	public DAORepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected String getDefaultKeyFieldName() {
		return "id";
	}

	@Override
	protected Class<RepositoryEntity> getEntityClass() {
		return RepositoryEntity.class;
	}

}
