/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import javax.persistence.EntityManager;

import net.sf.mmm.persistence.api.query.jpql.JpqlBase;
import net.sf.mmm.persistence.impl.jpa.query.AbstractJpqlContext;

/**
 * This class is the container for the context and state required to build a JPQL query.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlContext<E> extends AbstractJpqlContext implements JpqlBase<E> {

  /** @see #getEntityType() */
  private final Class<E> entityType;

  /** @see #getEntityAlias() */
  private final String entityAlias;

  /** @see #getQueryBuffer() */
  private final StringBuilder queryBuffer;

  /** @see #isSubQuery() */
  private boolean subQuery;

  /**
   * The constructor.
   * 
   * @param entityManager - see {@link #getEntityManager()}.
   * @param entityType - see {@link #getEntityType()}.
   * @param entityAlias - see {@link #getEntityAlias()}.
   */
  public JpqlContext(EntityManager entityManager, Class<E> entityType, String entityAlias) {

    this(entityManager, entityType, entityAlias, false);
  }

  /**
   * The constructor.
   * 
   * @param entityManager - see {@link #getEntityManager()}.
   * @param entityType - see {@link #getEntityType()}.
   * @param entityAlias - see {@link #getEntityAlias()}.
   * @param subQuery - see {@link #isSubQuery()}.
   */
  public JpqlContext(EntityManager entityManager, Class<E> entityType, String entityAlias, boolean subQuery) {

    super(entityManager);
    this.entityType = entityType;
    this.entityAlias = entityAlias;
    this.queryBuffer = new StringBuilder(48);
    this.subQuery = subQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<E> getEntityType() {

    return this.entityType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getEntityAlias() {

    return this.entityAlias;
  }

  /**
   * @return the {@link StringBuilder} with the current query string.
   */
  public StringBuilder getQueryBuffer() {

    return this.queryBuffer;
  }

  /**
   * This method adds a parameter to the query.
   * 
   * @param parameter is the {@link javax.persistence.TypedQuery#setParameter(int, Object) parameter} to add.
   * @param property is the property that may be used to create nice named parameters instead of indexed
   *        parameters.
   */
  public void addParameter(Object parameter, String property) {

    this.queryBuffer.append(JPQL_PARAMETER);
    getParameters().add(parameter);
  }

  /**
   * @return <code>true</code> if this context is created for a sub-query, <code>false</code> otherwise.
   */
  public boolean isSubQuery() {

    return this.subQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.queryBuffer.toString();
  }

}
