/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.api.PersistenceEntity;
import net.sf.mmm.persistence.base.AbstractPersistenceEntityManager;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.PersistenceEntityManager} using the
 * {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @param <ID> is the type of the {@link PersistenceEntity#getId() primary key}
 *        of the managed {@link PersistenceEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the
 *        managed entity.
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
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.entityManager == null) {
      throw new ResourceMissingException("entityManager");
    }
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

    ENTITY entity = loadIfExists(id);
    if (entity == null) {
      throw new ObjectNotFoundException(getEntityClassImplementation(), id);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY loadIfExists(ID id) throws ObjectNotFoundException {

    NlsNullPointerException.checkNotNull("id", id);
    return getEntityManager().find(getEntityClassImplementation(), id);
  }

  /**
   * {@inheritDoc}
   */
  public ENTITY getReference(ID id) {

    return getEntityManager().getReference(getEntityClassImplementation(), id);
  }

  /**
   * {@inheritDoc}
   */
  public void save(ENTITY entity) {

    if (!entity.isPersistent()) {
      getEntityManager().persist(entity);
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
