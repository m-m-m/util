/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import net.sf.mmm.transaction.api.TransactionSettings;
import net.sf.mmm.transaction.base.AbstractTransactionExecutor;
import net.sf.mmm.transaction.base.TransactionCommitException;
import net.sf.mmm.transaction.base.TransactionRollbackException;

/**
 * This is the implementation of the {@link net.sf.mmm.transaction.api.TransactionExecutor} interface using
 * the {@link javax.transaction JTA} (Java Transaction API).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class JpaTransactionExecutor extends AbstractTransactionExecutor {

  /** @see #setEntityManager(EntityManager) */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpaTransactionExecutor() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param entityManager is the {@link EntityManager} to {@link #setEntityManager(EntityManager) set}.
   */
  public JpaTransactionExecutor(EntityManager entityManager) {

    super();
    this.entityManager = entityManager;
  }

  /**
   * This method sets the {@link EntityManager}. It will be called automatically for dependency-injection.
   * 
   * @param entityManager is the {@link EntityManager} to set.
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JpaTransactionAdapter openTransactionAdapter(TransactionSettings settings) {

    return new JpaTransactionAdapter();
  }

  /**
   * This is the implementation of the {@link net.sf.mmm.transaction.api.TransactionAdapter} interface using
   * JTA.
   */
  private class JpaTransactionAdapter extends AbstractTransactionAdapter<EntityTransaction> {

    /**
     * The constructor.
     */
    public JpaTransactionAdapter() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected EntityTransaction createNewTransaction() {

      EntityTransaction transaction = JpaTransactionExecutor.this.entityManager.getTransaction();
      transaction.begin();
      return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCommit() {

      try {
        getActiveTransaction().commit();
      } catch (Exception e) {
        throw new TransactionCommitException(e);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRollback() {

      try {
        getActiveTransaction().rollback();
      } catch (Exception e) {
        throw new TransactionRollbackException(e);
      }
    }

  }

}
