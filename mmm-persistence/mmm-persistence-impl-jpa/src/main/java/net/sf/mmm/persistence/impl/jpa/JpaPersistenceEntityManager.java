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
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class JpaPersistenceEntityManager<ENTITY extends PersistenceEntity> extends
    AbstractPersistenceEntityManager<ENTITY> {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpaPersistenceEntityManager() {

    super();
  }

  /**
   * @return the entityManager
   */
  protected EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
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
  public ENTITY load(Object id) throws ObjectNotFoundException {

    return this.entityManager.find(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   */
  public void save(ENTITY entity) {

    if (!entity.isPersistent()) {
      this.entityManager.persist(entity);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void delete(ENTITY entity) {

    this.entityManager.remove(entity);
  }

}
