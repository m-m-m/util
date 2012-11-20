/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.api.jpa.query.JpqlFromClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlQueryBuilder;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This is the implementation of {@link JpqlQueryBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlQueryBuilderImpl extends AbstractJpqlPropertySupport implements JpqlQueryBuilder {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpqlQueryBuilderImpl() {

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
   * This method gets a {@link EntityManager} used to create queries.
   * 
   * @return the {@link EntityManager}.
   */
  protected final EntityManager getEntityManager() {

    return this.entityManager;
  }

  /**
   * This method injects the {@link EntityManager} instance.
   * 
   * @param entityManager is the {@link EntityManager}.
   */
  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {

    getInitializationState().requireNotInitilized();
    this.entityManager = entityManager;
  }

  /**
   * This method creates the default alias for the given <code>entityType</code>.
   * 
   * @param entityType is the {@link Class} reflecting the
   *        {@link net.sf.mmm.persistence.api.jpa.query.JpqlFragment#getEntityType() entity}.
   * @return the default alias for the given <code>entityType</code>.
   */
  protected String getDefaultAlias(Class<?> entityType) {

    return entityType.getSimpleName() + "Alias";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> JpqlFromClause<E> from(Class<E> entityType) {

    return from(entityType, getDefaultAlias(entityType));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <E> JpqlFromClause<E> from(Class<E> entityType, String alias) {

    JpqlQueryContext<E> context = new JpqlQueryContext<E>(this.entityManager, entityType, alias);
    return new JpqlFromClauseImpl<E>(context);
  }

}
