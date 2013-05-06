package com.plural.core.persistence;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The base Data Access Object interface definition
 * @param <T> The entity type managed by the DAO
 */
public interface DAO<T extends PluralEntity> {
	
	/**
	 * Begins a transactional context
	 */
	public void transactionBegin();
	
	/**
	 * Commits the current transaction
	 */
	public void transactionCommit();
	
	/**
	 * Rolls back the current transaction
	 */
	public void transactionRollback();
	
	/**
	 * Sets the current transaction for rollback whilst keeping the transaction active
	 */
	public void transactionSetForRollback();
	
	/**
	 * @return true if there is already an active transaction
	 */
	public boolean isTransactionActive();
	
	/**
	 * Gets the persisted entity
	 * @param key The entity key
	 * @return The entity instance
	 */
	public <KEY_T> T get(KEY_T key); 
	
	/**
	 * Persists a new entity instance
	 * @param entity The entity to be persisted
	 * @return The persisted entity
	 */
	public T create(T entity);
	
	/**
	 * Updates a persisted entity
	 * @param entity The entity vto be updated
	 * @return The updated entity
	 */
	public T update(T entity);
	
	/**
	 * Deletes the entity for the given key
	 * @param key The key for the persisted entity
	 */
	public <KEY_T> void delete(KEY_T key); 
	
	
}
