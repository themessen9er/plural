package com.plural.core.persistence;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * An utility class to ensure that a block of instructions
 * is executed inside a transaction context
 */
public abstract class TransactionWrapper<T> {

	protected DAO<?> dao;

	/**
	 * The class constructor
	 */
	public TransactionWrapper(DAO<?> dao) {
		this.dao = dao;
	}

	/**
	 * Executes a block of instructions in a transaction context
	 * @throws Exception A wrapper for any exception thrown inside the instruction block
	 */
	public T execute() throws Exception {
		boolean wasActive = dao.isTransactionActive();

		if(!wasActive) {
			dao.transactionBegin();
		}

		T result = null;
		try {
			result = executeTransactionCodeBlock();
		} catch (Exception e) {
			if(wasActive) {
				dao.transactionSetForRollback();
			} else {
				dao.transactionRollback();
			}
			throw e;
		}

		if(!wasActive) {
			dao.transactionCommit();
		}

		return result;
	}

	/**
	 * The abstract method which must be implemented and should contain
	 * The code meant to be executed in a transaction context.
	 * @return a result of the transaction block
	 */
	protected abstract T executeTransactionCodeBlock() throws Exception;

}