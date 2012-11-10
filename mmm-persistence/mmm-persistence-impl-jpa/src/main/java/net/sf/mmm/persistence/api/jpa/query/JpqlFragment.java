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
public abstract interface JpqlFragment<E> {

  /** JPQL-keyword SELECT. */
  String JPQL_SELECT = "SELECT ";

  /** JPQL-keyword FROM. */
  String JPQL_FROM = " FROM ";

  /** JPQL-keyword DISTINCT. */
  String JPQL_DISTINCT = "DISTINCT ";

  /** JPQL-keyword JOIN. */
  String JPQL_JOIN = " JOIN ";

  /** JPQL-keyword LEFT JOIN. */
  String JPQL_LEFT_JOIN = " LEFT JOIN ";

  /** JPQL-keyword LEFT OUTER JOIN. */
  String JPQL_LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";

  /** JPQL-keyword INNER JOIN. */
  String JPQL_INNER_JOIN = " INNER JOIN ";

  /** JPQL-keyword FETCH. */
  String JPQL_FETCH = " FETCH ";

  /** JPQL-keyword IN. */
  String JPQL_IN = " IN ";

  /** JPQL-keyword ORDER BY. */
  String JPQL_ORDER_BY = " ORDER BY ";

  /** JPQL-keyword GROUP BY. */
  String JPQL_GROUP_BY = " GROUP BY ";

  /** JPQL-keyword new. */
  String JPQL_NEW = "new ";

  /** JPQL-keyword WHERE. */
  String JPQL_WHERE = " WHERE ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_AND = " AND ";

  /** JPQL-conjunction for AND. */
  String JPQL_CONJUNCTION_OR = " OR ";

  /** JPQL-keyword for indexed parameter. */
  String JPQL_PARAMETER = "?";

  /**
   * @return the {@link Class} reflecting the main {@link net.sf.mmm.util.entity.api.GenericEntity entity}.
   *         This is typically the result type of this query, e.g. in case of a regular {@link #select()
   *         select}.
   */
  Class<E> getEntityType();

  /**
   * This method gets the alias of the {@link #getEntityType() (main) entity}. For example in the query
   * "SELECT foo FROM MyEntity foo ..." this method will return "foo".
   * 
   * @return the alias of the {@link #getEntityType() (main) entity}.
   */
  String getEntityAlias();

  /**
   * This method creates a {@link String} to be used as property-argument in methods like
   * {@link JpqlConditionalExpression#isOperator(String, String, Object)}. According to JPQL specification
   * this is called <code>single_valued_association_path_expression</code>.<br/>
   * This is a shortcut for
   * <code>{@link #createProperty(boolean, String...) createProperty}(true, properties)</code>.
   * 
   * @param properties is the array of properties to concat with dot (".").
   * @return the resulting property {@link String}.
   */
  String createProperty(String... properties);

  /**
   * This method creates a {@link String} to be used as property-argument in methods like
   * {@link JpqlConditionalExpression#isOperator(String, String, Object)}. According to JPQL specification
   * this is called <code>single_valued_association_path_expression</code>.
   * 
   * @param appendAlias if <code>true</code> the {@link #getEntityAlias() entity alias} is appended
   *        automatically as if it was added as first argument to <code>properties</code>.
   * @param properties is the array of properties to concat with dot (".").
   * @return the resulting property {@link String}.
   */
  String createProperty(boolean appendAlias, String... properties);

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
   * @param arguments are the constructor-arguments.
   * @return the {@link TypedQuery} representing the build query.
   */
  TypedQuery<E> selectNew(String... arguments);

  /**
   * Builds the actual query as <code>select_statement</code> using the aggregate function MIN on the given
   * <code>selection</code>. The query is build as "SELECT MIN(&lt;selection>) FROM ...".
   * 
   * @param selection is the actual selection to aggregate.
   * @return the {@link TypedQuery} representing the build query.
   */
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

}
