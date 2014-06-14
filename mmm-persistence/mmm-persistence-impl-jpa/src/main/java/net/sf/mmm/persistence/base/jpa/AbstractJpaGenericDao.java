/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.base.AbstractGenericDao;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.exception.api.ObjectNotFoundUserException;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.persistence.api.GenericDao} using the
 * {@link javax.persistence JPA} (Java Persistence API).
 *
 * @param <ID> is the type of the {@link GenericEntity#getId() primary key} of the managed
 *        {@link GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractJpaGenericDao<ID, ENTITY extends GenericEntity<ID>> extends
    AbstractGenericDao<ID, ENTITY> {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public AbstractJpaGenericDao() {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY find(ID id) throws ObjectNotFoundException {

    ENTITY entity = findIfExists(id);
    if (entity == null) {
      throw new ObjectNotFoundUserException(getEntityClass(), id);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY findIfExists(ID id) throws ObjectNotFoundException {

    NlsNullPointerException.checkNotNull("id", id);
    return getEntityManager().find(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY getReference(ID id) {

    return getEntityManager().getReference(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(ENTITY entity) {

    if (!getEntityManager().contains(entity)) {
      getEntityManager().persist(entity);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(ID id) {

    NlsNullPointerException.checkNotNull("id", id);
    ENTITY entity = getReference(id);
    getEntityManager().remove(entity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(ENTITY entity) {

    NlsNullPointerException.checkNotNull(GenericEntity.class, entity);
    if (getEntityManager().contains(entity)) {
      getEntityManager().remove(entity);
    } else {
      deleteById(entity.getId());
    }
  }

}
