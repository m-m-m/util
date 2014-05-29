/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.persistence.base.sequence.AbstractSequenceManager;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.persistence.api.sequence.SequenceManager}
 * based on JPA. It uses the {@link EntityManager} to access a {@link Sequence} via native SQL.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class JpaSequenceManager extends AbstractSequenceManager {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpaSequenceManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getNextValue(Sequence sequence) {

    Query query = this.entityManager.createNativeQuery(createNextValueSql(sequence), Long.class);
    Long nextValue = (Long) query.getSingleResult();
    return nextValue.longValue();
  }

  /**
   * Creates the SQL statement to get the next value of the given {@link Sequence}.
   *
   * @param sequence is the {@link Sequence} to use.
   * @return the SQL statement to get the next value of the given {@link Sequence}.
   */
  protected abstract String createNextValueSql(Sequence sequence);

  /**
   * This method gets a thread-safe {@link EntityManager}. It acts as proxy to an {@link EntityManager}
   * associated with the current thread (created when the transaction is opened e.g. via
   * {@link net.sf.mmm.transaction.api.TransactionExecutor}).
   *
   * @return the according {@link EntityManager}.
   */
  protected final EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * This method injects a thread-safe {@link EntityManager} instance that acts as proxy to an
   * {@link EntityManager} associated with the current thread (created when the transaction is opened).
   *
   * @param entityManager is the {@link EntityManager} to inject.
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

}
