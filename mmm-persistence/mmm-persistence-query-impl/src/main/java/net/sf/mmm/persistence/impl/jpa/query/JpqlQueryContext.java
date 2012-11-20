/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import net.sf.mmm.persistence.api.jpa.query.JpqlBase;

/**
 * This class is the container for the context and state required to build a JPQL query.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlQueryContext<E> implements JpqlBase<E> {

  /** @see #getEntityManager() */
  private final EntityManager entityManager;

  /** @see #getEntityType() */
  private final Class<E> entityType;

  /** @see #getEntityAlias() */
  private final String entityAlias;

  /** @see #getQueryBuffer() */
  private final StringBuilder queryBuffer;

  /** The {@link List} of parameters. */
  private List<Object> parameterList;

  /**
   * The constructor.
   * 
   * @param entityManager - see {@link #getEntityManager()}.
   * @param entityType - see {@link #getEntityType()}.
   * @param entityAlias - see {@link #getEntityAlias()}.
   */
  public JpqlQueryContext(EntityManager entityManager, Class<E> entityType, String entityAlias) {

    super();
    this.entityManager = entityManager;
    this.entityType = entityType;
    this.entityAlias = entityAlias;
    this.queryBuffer = new StringBuilder(48);
    this.parameterList = new LinkedList<Object>();
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
   * @return the instance of {@link EntityManager} used to build the query.
   */
  public EntityManager getEntityManager() {

    return this.entityManager;
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
    this.parameterList.add(parameter);
  }

  /**
   * @see net.sf.mmm.persistence.api.jpa.query.JpqlFragment#select(String)
   * 
   * @param <R> is the generic type of the result.
   * 
   * @param selection is the actual selection to aggregate.
   * @param resultType is the type of the result.
   * @return the {@link TypedQuery} representing the build query.
   */
  public <R> TypedQuery<R> createSelectQuery(String selection, Class<R> resultType) {

    StringBuilder query = new StringBuilder(this.queryBuffer.length() + 16);
    query.append(JPQL_SELECT);
    query.append(selection);
    query.append(this.queryBuffer);
    TypedQuery<R> typedQuery = this.entityManager.createQuery(query.toString(), resultType);
    int i = 0;
    for (Object parameter : this.parameterList) {
      typedQuery.setParameter(i++, parameter);
    }
    return typedQuery;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.queryBuffer.toString();
  }

}
