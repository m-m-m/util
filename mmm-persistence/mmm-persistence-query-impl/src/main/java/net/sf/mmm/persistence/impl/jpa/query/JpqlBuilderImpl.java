/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.mmm.persistence.api.query.JpqlBuilder;
import net.sf.mmm.persistence.api.query.jpql.JpqlFromClause;
import net.sf.mmm.persistence.impl.jpa.query.jpql.JpqlContext;
import net.sf.mmm.persistence.impl.jpa.query.jpql.JpqlFromClauseImpl;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the implementation of {@link JpqlBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
@Named
public class JpqlBuilderImpl extends AbstractLoggableComponent implements JpqlBuilder {

  /** @see #getEntityManager() */
  private EntityManager entityManager;

  /**
   * The constructor.
   */
  public JpqlBuilderImpl() {

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
   *        {@link net.sf.mmm.persistence.api.query.jpql.JpqlFragment#getEntityType() entity}.
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

    JpqlContext<E> context = new JpqlContext<E>(this.entityManager, entityType, alias);
    return new JpqlFromClauseImpl<E>(context);
  }

}
