package com.plural.core.persistence.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import com.plural.core.persistence.DAO;
import com.plural.core.persistence.PluralEntity;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The abstract JPA implementation of {@link DAO}
 * @param <T> The {@link DAO} handled type
 */
public abstract class DAOImpl<T extends PluralEntity> implements DAO<T> {

	private EntityManager entityManager;
	
	/**
	 * @param entityManager The JPA entity manager to be used by this DAO
	 */
	@Inject
	public DAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
		getEntityManager().setFlushMode(FlushModeType.AUTO);
	}
	
	
	@Override
	public void transactionBegin() {
		getEntityManager().getTransaction().begin();
	}

	@Override
	public void transactionCommit() {
		getEntityManager().getTransaction().commit();
	}

	@Override
	public void transactionRollback() {
		getEntityManager().getTransaction().rollback();
	}

	@Override
	public void transactionSetForRollback() {
		getEntityManager().getTransaction().setRollbackOnly();
	}
	
	@Override
	public boolean isTransactionActive() {
		return getEntityManager().getTransaction().isActive();
	}

	@Override
	public <KEY_T> T get(KEY_T key) throws NoResultException {
		return getEntityManager().createQuery(
				"SELECT e " +
				"FROM " + getEntityClass().getCanonicalName() + " AS e " +
				"WHERE e." + getDefaultKeyFieldName() + " = :key",
				getEntityClass())
				.setParameter("key", key.toString())
				.getSingleResult();
	}

	@Override
	public T create(T entity) {
		assert(entity instanceof BaseEntity);
		getEntityManager().persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		assert(entity instanceof BaseEntity);
		getEntityManager().merge(entity);
		return entity;
	}

	@Override
	public <KEY_T> void delete(KEY_T key) {
		getEntityManager().createQuery(
				"DELETE e " +
				"FROM " + getEntityClass().getCanonicalName() + " AS e " +
				"WHERE e." + getDefaultKeyFieldName() + " = :key",
				getEntityClass())
				.setParameter("key", key.toString())
				.getSingleResult();
	}
	
	protected abstract String getDefaultKeyFieldName();
	
	protected abstract Class<? extends T> getEntityClass();
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

}
