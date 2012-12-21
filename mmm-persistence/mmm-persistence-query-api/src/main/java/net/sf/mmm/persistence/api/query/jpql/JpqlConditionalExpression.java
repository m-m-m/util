/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

import net.sf.mmm.persistence.api.query.SimpleQuery;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the abstract interface for a conditional expression of a JPQL-query.
 * 
 * @see net.sf.mmm.persistence.api.query.JpqlBuilder
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * @param <SELF> is the generic type reflecting the type of this instance itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface JpqlConditionalExpression<E, SELF extends JpqlConditionalExpression<E, ?>> extends
    JpqlFragment<E> {

  /**
   * This method creates a new sub-query to be used as argument in this conditional expression. The actual
   * sub-query can be created as {@link net.sf.mmm.persistence.api.query.SimpleQuery query} starting from the
   * returned {@link JpqlFromClause} just like a top-level query. Then it can be passed as {#PROPERTY
   * property} argument to according methods of this {@link JpqlConditionalExpression}.
   * 
   * @param basePath is the {@link #PROPERTY_BASE_PATH base path}.
   * @param property is the {@link #PROPERTY property} used as primary selection in the FROM clause.
   * @param alias is the {@link #ALIAS alias} to use for the property.
   * 
   * @return the new {@link JpqlFromClause} to build the sub-query.
   */
  JpqlFromClause<?> newSubQuery(String basePath, String property, String alias);

  /**
   * This method creates a new sub-query to be used as argument in this conditional expression. The actual
   * sub-query can be created as {@link net.sf.mmm.persistence.api.query.SimpleQuery query} starting from the
   * returned {@link JpqlFromClause} just like a top-level query. Then it can be passed as {#PROPERTY
   * property} argument to according methods of this {@link JpqlConditionalExpression}.
   * 
   * @param <T> is the generic type of the {@link TypedProperty} selected by the sub-query.
   * 
   * @param basePath is the {@link #PROPERTY_BASE_PATH base path}.
   * @param property is the {@link #PROPERTY property} used as primary selection in the FROM clause.
   * @param alias is the {@link #ALIAS alias} to use for the property.
   * 
   * @return the new {@link JpqlFromClause} to build the sub-query.
   */
  <T> JpqlFromClause<T> newSubQuery(String basePath, TypedProperty<T> property, String alias);

  /**
   * This method creates a new sub-query to be used as argument in this conditional expression. The actual
   * sub-query can be created as {@link net.sf.mmm.persistence.api.query.SimpleQuery query} starting from the
   * returned {@link JpqlFromClause} just like a top-level query. Then it can be passed as as {#PROPERTY
   * property} argument to according methods of this {@link JpqlConditionalExpression}.
   * 
   * @param <T> is the generic type of the {@link JpqlFromClause#getEntityType() main entity} of the
   *        sub-query.
   * 
   * @param entityType is the {@link Class} reflecting the (main) {@link JpqlFromClause#getEntityType()
   *        entity} to select.
   * @param alias is the {@link JpqlCore#ALIAS alias} to use for the property.
   * @return the new {@link JpqlFromClause} to build the sub-query.
   */
  <T> JpqlFromClause<T> newSubQuery(Class<T> entityType, String alias);

  /**
   * This method create a new {@link JpqlPropertyExpression} for this conditional expression.
   * 
   * @param <T> is the generic type of the property.
   * 
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the new {@link JpqlPropertyExpression}.
   */
  <T> JpqlPropertyExpression<T, SELF> property(String basePath, TypedProperty<T> property);

  /**
   * This method create a new {@link JpqlPropertyExpression} for this conditional expression.
   * 
   * @param <T> is the generic type of the property.
   * 
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @param propertyType is the {@link Class} reflecting the type of the property.
   * @return the new {@link JpqlPropertyExpression}.
   */
  <T> JpqlPropertyExpression<T, SELF> property(String basePath, String property, Class<T> propertyType);

  /**
   * This method create a new {@link JpqlPropertyExpression} for this conditional expression.
   * 
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the new {@link JpqlPropertyExpression}.
   */
  JpqlPropertyExpression<Object, SELF> property(String basePath, String property);

  /**
   * This method create a new {@link JpqlPropertyExpression} for this conditional expression.
   * 
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the new {@link JpqlPropertyExpression}.
   */
  JpqlPropertyExpression<Object, SELF> property(String property);

  /**
   * This method create a new {@link JpqlPropertyExpression} for this conditional expression.
   * 
   * @param <T> is the generic type of the property.
   * 
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the new {@link JpqlPropertyExpression}.
   */
  <T> JpqlPropertyExpression<T, SELF> property(TypedProperty<T> property);

  /**
   * This method create a new {@link JpqlPropertyExpression} for the given <code>subQuery</code>.
   * 
   * @see #newSubQuery(Class, String)
   * @see #newSubQuery(String, String, String)
   * @see #newSubQuery(String, TypedProperty, String)
   * 
   * @param <T> is the generic type of result of the sub-query.
   * 
   * @param subQuery is the {@link SimpleQuery} representing a sub-query.
   * @return the new {@link JpqlPropertyExpression}.
   */
  <T> JpqlPropertyExpression<T, SELF> subQuery(SimpleQuery<T> subQuery);

  /**
   * Adds an AND conjunction to this query. Will fail if called without previously adding a comparison.
   * 
   * @return this instance.
   */
  SELF and();

  /**
   * Adds an OR conjunction to this query. Will fail if called without previously adding a comparison.
   * 
   * @return this instance.
   */
  SELF or();

  /**
   * @see #setConjunctionDefaultToAnd()
   * 
   * @return this instance.
   */
  SELF setConjunctionDefaultToAnd();

  /**
   * Instead of explicitly calling {@link #and()} (or {@link #or()}) after each added condition, you can also
   * omit this so the query builder will add this automatically. The default is to use " AND " by default. You
   * can change this by calling this method and reset it with {@link #setConjunctionDefaultToAnd()}.
   * 
   * @return this instance.
   */
  SELF setConjunctionDefaultToOr();

  /**
   * {@inheritDoc}
   */
  @Override
  SELF setPropertyBasePath(String path);

}
