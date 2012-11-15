/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the abstract interface for a conditional expression of a JPQL-query.
 * 
 * @see JpqlQueryBuilder
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
   * This constant is used to document the concept of a property. A property can either be provided in a
   * type-safe way as a {@link TypedProperty} (recommended) or as a regular {@link String}. Properties are
   * addressed in JPQL by a path of java bean properties separated by a dot. E.g. for the
   * {@link #getEntityAlias alias} <code>myAlias</code> a final property may be
   * <code>myAlias.address.city</code> will refer to <code>getAddress().getCity()</code> invoked on
   * <code>myAlias</code>. If properties are provided as {@link String} you can still use
   * {@link TypedProperty#createPath(String...)} to build that {@link String} (e.g. {@link TypedProperty}.
   * {@link TypedProperty#createPath(String...) createPath}("address", "city") for "address.city").<br/>
   * Whenever a property is given in this API and will be appended to the query, the
   * {@link #getPropertyPrefix() property prefix} will automatically be added before the actual property.
   */
  String PROPERTY = "property";

  /**
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>IMPORTANT:</b><br/>
   * If the given <code>value</code> is <code>null</code> and <code>operator</code> is
   * {@link JpqlOperator#EQUAL} or {@link JpqlOperator#NOT_EQUAL}, then an according
   * {@link #JPQL_CONDITION_IS_NULL} or {@link #JPQL_CONDITION_IS_NOT_NULL} is created instead.
   * 
   * @param <T> is the generic type of the {@link TypedProperty property}.
   * 
   * @param property is the {@link #PROPERTY property} to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  <T> SELF isCompare(TypedProperty<T> property, JpqlOperator operator, T value);

  /**
   * This method adds the specified comparison expression to the query. It will compare two properties using
   * the given <code>operator</code>.
   * 
   * @param <T> is the generic type of the {@link TypedProperty property}.
   * 
   * @param property is the {@link #PROPERTY property} to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param otherProperty is the {@link #PROPERTY property} to use as second argument.
   * @return this instance itself.
   */
  <T> SELF isCompare(TypedProperty<T> property, JpqlOperator operator, TypedProperty<T> otherProperty);

  /**
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * In most cases you should use {@link #isCompare(TypedProperty, JpqlOperator, Object)} instead. This method
   * is only for special cases like inverse {@link JpqlOperator#LIKE like} expressions.
   * 
   * @param <T> is the generic type of the {@link TypedProperty property}.
   * 
   * @param value is the value to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param property is the {@link #PROPERTY property} to use as second argument.
   * @return this instance itself.
   */
  <T> SELF isCompare(T value, JpqlOperator operator, TypedProperty<T> property);

  /**
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * For type-safe access consider using {@link #isCompare(TypedProperty, JpqlOperator, Object)} instead.
   * 
   * @param property is the {@link #PROPERTY property} to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isCompareValue(String property, JpqlOperator operator, Object value);

  /**
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * For type-safe access consider using {@link #isCompare(TypedProperty, JpqlOperator, Object)} instead.
   * 
   * @param property is the {@link #PROPERTY property} to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param otherProperty is the {@link #PROPERTY property} to use as second argument.
   * @return this instance itself.
   */
  SELF isCompareProperties(String property, JpqlOperator operator, String otherProperty);

  /**
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * For type-safe access consider using {@link #isCompare(Object, JpqlOperator, TypedProperty)} instead.
   * Otherwise also consider using {@link #isCompareValue(String, JpqlOperator, Object)} instead. This method
   * is only for special cases like inverse {@link JpqlOperator#LIKE like} expressions.
   * 
   * @param value is the value to use as first argument.
   * @param operator is the operator used for comparison (e.g. {@link JpqlOperator#EQUAL},
   *        {@link JpqlOperator#NOT_EQUAL}, {@link JpqlOperator#LIKE}, etc.)
   * @param property is the {@link #PROPERTY property} to use as second argument.
   * @return this instance itself.
   */
  SELF isCompareInvers(Object value, JpqlOperator operator, String property);

  /**
   * This method is similar to {@link #isCompare(TypedProperty, JpqlOperator, Object)} using the operator
   * {@link JpqlOperator#LIKE} or {@link JpqlOperator#NOT_LIKE}.
   * 
   * @param property is the {@link #PROPERTY property} to match against the given <code>pattern</code>.
   * @param pattern is the value with the pattern to match.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> for {@link JpqlOperator#NOT_LIKE}, <code>false</code> for
   *        {@link JpqlOperator#LIKE}.
   * @return this instance itself.
   */
  SELF isLike(TypedProperty<String> property, String pattern, boolean convertGlob, boolean not);

  /**
   * This method is similar to {@link #isCompare(Object, JpqlOperator, TypedProperty)} using the operator
   * {@link JpqlOperator#LIKE} or {@link JpqlOperator#NOT_LIKE}.
   * 
   * @param pattern is the value with the pattern to match against the value of the given
   *        <code>property</code>.
   * @param property is the {@link #PROPERTY property} to be matched.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> for {@link JpqlOperator#NOT_LIKE}, <code>false</code> for
   *        {@link JpqlOperator#LIKE}.
   * @return this instance itself.
   */
  SELF isLike(String pattern, TypedProperty<String> property, boolean convertGlob, boolean not);

  /**
   * This method is similar to {@link #isCompareValue(String, JpqlOperator, Object)} using the operator
   * {@link JpqlOperator#LIKE} or {@link JpqlOperator#NOT_LIKE}.
   * 
   * @param property is the {@link #PROPERTY property} to match against the given <code>pattern</code>.
   * @param pattern is the value with the pattern to match.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> for {@link JpqlOperator#NOT_LIKE}, <code>false</code> for
   *        {@link JpqlOperator#LIKE}.
   * @return this instance itself.
   */
  SELF isLikeValue(String property, String pattern, boolean convertGlob, boolean not);

  /**
   * This method is similar to {@link #isCompareInvers(Object, JpqlOperator, String)} using the operator
   * {@link JpqlOperator#LIKE} or {@link JpqlOperator#NOT_LIKE}.
   * 
   * @param pattern is the value with the pattern to match against the value of the given
   *        <code>property</code>.
   * @param property is the {@link #PROPERTY property} to be matched.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> for {@link JpqlOperator#NOT_LIKE}, <code>false</code> for
   *        {@link JpqlOperator#LIKE}.
   * @return this instance itself.
   */
  SELF isLikeInverse(String pattern, String property, boolean convertGlob, boolean not);

  /**
   * This method adds a given conditional expression to the query (property + " " + condition).
   * 
   * @see #isNull(String)
   * @see #isNotNull(String)
   * @see #isEmpty(String)
   * @see #isNotEmpty(String)
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param condition is the condition to add.
   * @return this instance itself.
   */
  SELF isCondition(String property, String condition);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS NULL".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @return this instance itself.
   */
  SELF isNull(String property);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS [NOT ]NULL".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isNull(String property, boolean not);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS NOT NULL".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @return this instance itself.
   */
  SELF isNotNull(String property);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS EMPTY".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @return this instance itself.
   */
  SELF isEmpty(String property);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS [NOT ]EMPTY".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isEmpty(String property, boolean not);

  /**
   * This method calls {@link #isCondition(String, String)} using the condition "IS NOT EMPTY".
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @return this instance itself.
   */
  SELF isNotEmpty(String property);

  /**
   * This method adds a between expression to the query (property + " BETWEEN :min AND :max").
   * 
   * @param <T> is the generic type of <code>min</code> and <code>max</code>.
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @return this instance itself.
   */
  <T> SELF isBetween(String property, T min, T max);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param <T> is the generic type of <code>min</code> and <code>max</code>.
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  <T> SELF isBetween(String property, T min, T max, boolean not);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param range is the {@link Range}.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isBetween(String property, Range<?> range, boolean not);

  /**
   * This method adds a between expression to the query (property + " BETWEEN :min AND :max").
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param minProperty is the {@link #PROPERTY property} with the minimum value.
   * @param maxProperty is the {@link #PROPERTY property} with the maximum value.
   * @return this instance itself.
   */
  SELF isBetweenProperty(String property, String minProperty, String maxProperty);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param property is the {@link #PROPERTY property} to check.
   * @param minProperty is the {@link #PROPERTY property} with the minimum value.
   * @param maxProperty is the {@link #PROPERTY property} with the maximum value.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isBetweenProperty(String property, String minProperty, String maxProperty, boolean not);

  /**
   * This method converts a glob-pattern to an SQL-pattern to be used in a LIKE-expression. Glob-patterns are
   * typically used and expected by end-users but SQL has its own syntax.
   * <table border="1">
   * <tr>
   * <th>Wildcard</th>
   * <th>Glob</th>
   * <th>SQL</th>
   * </tr>
   * <tr>
   * <td>Any substring (zero or more characters).</td>
   * <td>*</td>
   * <td>%</td>
   * </tr>
   * <tr>
   * <td>A single character.</td>
   * <td>?</td>
   * <td>_</td>
   * </tr>
   * <tr>
   * <td>Any single character out of the given list.</td>
   * <td>&lt;not supported&gt;</td>
   * <td>[&lt;characters&gt;]</td>
   * </tr>
   * <tr>
   * <td>Any single character NOT in the given list.</td>
   * <td>&lt;not supported&gt;</td>
   * <td>[^&lt;characters&gt;]<br/>
   * or [!&lt;characters&gt;]</td>
   * </tr>
   * </table>
   * 
   * @param pattern is the pattern in GLOB-syntax.
   * @return the pattern converted to SQL-syntax. Character lists will remain untouched.
   */
  String convertGlobPattern(String pattern);

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
   * {@inheritDoc}
   */
  @Override
  SELF setPropertyBasePath(String path);

}
