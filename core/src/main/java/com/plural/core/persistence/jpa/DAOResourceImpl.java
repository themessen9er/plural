package com.plural.core.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.plural.core.persistence.DAO;
import com.plural.core.persistence.DAOResource;
import com.plural.core.persistence.Resource;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link DAO} for {@link ResourceEntity}
 */
public class DAOResourceImpl extends DAOImpl<Resource> implements DAOResource {

	/**
	 * @param entityManager The JPA entity manager
	 */
	@Inject
	public DAOResourceImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected String getDefaultKeyFieldName() {
		return "id";
	}

	@Override
	protected Class<ResourceEntity> getEntityClass() {
		return ResourceEntity.class;
	}

}
