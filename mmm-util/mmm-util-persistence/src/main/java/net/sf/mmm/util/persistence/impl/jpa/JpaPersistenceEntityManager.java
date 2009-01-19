/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.impl.jpa;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.persistence.api.PersistenceEntity;
import net.sf.mmm.util.persistence.base.AbstractPersistenceEntityManager;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.util.persistence.api.PersistenceEntityManager} using the
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
   * {@inheritDoc}
   */
  public ENTITY load(Object id) throws ObjectNotFoundException {

    return this.entityManager.find(getEntityClass(), id);
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
  @Resource
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

}
