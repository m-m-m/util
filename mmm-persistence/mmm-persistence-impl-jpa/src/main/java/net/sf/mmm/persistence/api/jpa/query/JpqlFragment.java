/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

import javax.persistence.TypedQuery;

/**
 * This is the abstract interface for a fragment of a JPQL-query.
 * 
 * @see JpqlQueryBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlFragment<E> extends JpqlBase<E>, JpqlPropertySupport {

  /**
   * @return the current JPQL query fragment as string. This is NOT a complete JQPL query. Use
   *         {@link #select()} and according methods to build a complete and parsed query with bound
   *         parameters.
   */
  String getCurrentQuery();

  /**
   * Builds the actual query as <code>select_statement</code> using defaults (selecting by alias name).
   * 
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<E> select();

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function COUNT. The query is
   * build as "SELECT COUNT(alias) FROM ...".
   * 
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<Long> selectCount();

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function AVG on the given
   * <code>selection</code>. The query is build as "SELECT AVG(&lt;selection>) FROM ...".
   * 
   * @param selection is the actual selection to aggregate.
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<Double> selectAverage(String selection);

  /**
   * Builds the actual query as <code>select_statement</code> using a JPQL constructor expression. The query
   * is build as "SELECT NEW &lt;resultType.getName()>(&lt;arguments>) FROM ...".
   * 
   * @param <R> is the generic type of the <code>resultType</code>.
   * 
   * @param resultType is the type of the result object (typically a transfer object, but may also be a
   *        {@link javax.persistence.Entity}).
   * @param arguments are the constructor-arguments.
   * @return the {@link TypedQuery} representing the build query.
   */
  <R> TypedQuery<R> selectNew(Class<R> resultType, String... arguments);

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function MIN on the given
   * <code>selection</code>. The query is build as "SELECT MIN(&lt;selection>) FROM ...".
   * 
   * @param selection is the actual selection to aggregate.
   * @return the {@link TypedQuery} representing the build query.
   */
  // // TODO hohwille: could also be a Date instead of Number
  TypedQuery<Number> selectMinimum(String selection);

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function MAX on the given
   * <code>selection</code>. The query is build as "SELECT MAX(&lt;selection>) FROM ...".
   * 
   * @param selection is the actual selection to aggregate.
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<Number> selectMaximum(String selection);

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function AVG on the given
   * <code>selection</code>. The query is build as "SELECT AVG(&lt;selection>) FROM ...".
   * 
   * @param selection is the actual selection to aggregate.
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<E> select(String selection);

  /**
   * {@inheritDoc}
   */
  @Override
  JpqlFragment<E> setPropertyBasePath(String path);

}
