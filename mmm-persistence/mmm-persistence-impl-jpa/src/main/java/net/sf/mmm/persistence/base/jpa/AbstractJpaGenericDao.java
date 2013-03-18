/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.base.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.api.query.JpqlBuilder;
import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.persistence.base.AbstractGenericDao;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.nls.api.ObjectNotFoundUserException;

/**
 * This is the abstract base-implementation of a {@link net.sf.mmm.persistence.api.GenericDao} using the
 * {@link javax.persistence JPA} (Java Persistence API).
 * 
 * @param <ID> is the type of the {@link GenericEntity#getId() primary key} of the managed
 *        {@link GenericEntity}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractJpaGenericDao<ID, ENTITY extends GenericEntity<ID>> extends
    AbstractGenericDao<ID, ENTITY> {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /** @see #getJpqlBuilder() */
  private JpqlBuilder jpqlBuilder;

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
   * @see #newQuery()
   * 
   * @return the {@link JpqlBuilder}.
   */
  protected JpqlBuilder getJpqlBuilder() {

    return this.jpqlBuilder;
  }

  /**
   * @param jpqlBuilder is the {@link JpqlBuilder} to inject.
   */
  @Inject
  public void setJpqlBuilder(JpqlBuilder jpqlBuilder) {

    this.jpqlBuilder = jpqlBuilder;
  }

  /**
   * @see #getJpqlBuilder()
   * @see SearchQueryJpa
   * 
   * @return a new {@link JpqlFromClause} to build a dynamic JPQL query for finding instances of the
   *         {@link #getEntityClassImplementation() managed entity}.
   */
  protected JpqlFromClause<? extends ENTITY> newQuery() {

    return this.jpqlBuilder.from(getEntityClassImplementation());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY load(ID id) throws ObjectNotFoundException {

    ENTITY entity = loadIfExists(id);
    if (entity == null) {
      throw new ObjectNotFoundUserException(getEntityClassImplementation(), id);
    }
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY loadIfExists(ID id) throws ObjectNotFoundException {

    NlsNullPointerException.checkNotNull("id", id);
    return getEntityManager().find(getEntityClassImplementation(), id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ENTITY getReference(ID id) {

    return getEntityManager().getReference(getEntityClassImplementation(), id);
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
  public void delete(ENTITY entity) {

    getEntityManager().remove(entity);
  }

}
