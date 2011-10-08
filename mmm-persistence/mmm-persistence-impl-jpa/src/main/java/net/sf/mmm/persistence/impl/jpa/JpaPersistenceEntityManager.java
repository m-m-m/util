/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.base.AbstractPersistenceEntityManager;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.PersistenceEntityManager} using the
 * {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}
 *        of the managed {@link PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class JpaPersistenceEntityManager<ID, ENTITY extends PersistenceEntity<ID>> extends
    AbstractPersistenceEntityManager<ID, ENTITY> {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpaPersistenceEntityManager() {

    super();
  }

  /**
   * This method gets a thread-safe {@link EntityManager}. It acts as proxy to
   * an {@link EntityManager} associated with the current thread (created when
   * the transaction is opened e.g. via
   * {@link net.sf.mmm.transaction.api.TransactionExecutor}).
   * 
   * @return the according {@link EntityManager}.
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY load(ID id) throws ObjectNotFoundException {

    ENTITY entity = getEntityManager().find(getEntityClass(), id);
    if (entity == null) {
      throw new ObjectNotFoundException(getEntityClass(), id);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY getReference(ID id) {

    return getEntityManager().getReference(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   */
  public void save(ENTITY entity) {

    if (!entity.isPersistent()) {
      getEntityManager().persist(entity);
      if (entity instanceof JpaManagedIdPersistenceEntity) {
        ((JpaManagedIdPersistenceEntity<?>) entity).setPersistent();
      }
    }
  }

  /**
   * This method injects a thread-safe {@link EntityManager} instance that acts
   * as proxy to an {@link EntityManager} associated with the current thread
   * (created when the transaction is opened).
   * 
   * @param entityManager is the entityManager to set
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

  /**
   * {@inheritDoc}
   */
  public void delete(ENTITY entity) {

    getEntityManager().remove(entity);
  }

}
