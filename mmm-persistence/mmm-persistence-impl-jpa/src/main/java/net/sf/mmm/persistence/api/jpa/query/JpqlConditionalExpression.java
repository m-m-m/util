/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.jpa.query;

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
   * This method adds the specified comparison expression to the query. It will compare a property with a
   * given dynamic value.
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param operator is the operator used for comparison (e.g. "=", "<>", " LIKE ", etc.)
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isOperator(String property, String operator, Object value);

  /**
   * This method adds the specified comparison expression to the query. It will compare a given dynamic value
   * with a property.<br/>
   * <b>ATTENTION:</b><br/>
   * In most cases you should use {@link #isOperator(String, String, Object)} instead. This method is only for
   * special cases like inverse LIKE expressions.
   * 
   * @param value is the value to use as first argument.
   * @param operator is the operator used for comparison (e.g. "=", "<>", " LIKE ", etc.)
   * @param property is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isOperatorInvers(Object value, String operator, String property);

  /**
   * This method adds the specified comparison expression to the query. It will compare to properties with
   * each other.
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param operator is the operator used for comparison (e.g. "=", "<>", " LIKE ", etc.)
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isOperatorProperty(String property, String operator, String otherProperty);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the equals operator ("=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isEqual(String property, Object value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the equals operator ("=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isEqualProperty(String property, String otherProperty);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the not-equals operator ("<>").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isNotEqual(String property, Object value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the not-equals operator ("<>").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isNotEqualProperty(String property, String otherProperty);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the greater-or-equals operator
   * (">=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isGreaterEqual(String property, Object value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the greater-or-equals operator
   * (">=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isGreaterEqualProperty(String property, String otherProperty);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the greater operator (">").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isGreater(String property, Object value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the greater operator (">").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isGreaterProperty(String property, String otherProperty);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the less-or-equals operator ("<=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isLessEqual(String property, Object value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the less-or-equals operator ("<=").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isLessEqualProperty(String property, String otherProperty);

/**
   * This method calls {@link #isOperator(String, String, Object)} using the less operator ("<").
   *
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @return this instance itself.
   */
  SELF isLess(String property, Object value);

/**
   * This method calls {@link #isOperator(String, String, Object)} using the less operator ("<").
   *
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param otherProperty is the property to use as second argument. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isLessProperty(String property, String otherProperty);

  /**
   * This method calls {@link #isLike(String, String, boolean)} using <code>true</code> for
   * <code>convertGlob</code>.
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument. Should be in {@link #convertGlobPattern(String)
   *        GLOB-syntax}.
   * @return this instance itself.
   */
  SELF isLike(String property, String value);

  /**
   * This method calls {@link #isNotLike(String, String, boolean)} using <code>true</code> for
   * <code>convertGlob</code>.
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument. Should be in {@link #convertGlobPattern(String)
   *        GLOB-syntax}.
   * @return this instance itself.
   */
  SELF isNotLike(String property, String value);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the LIKE operator (" LIKE ").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @return this instance itself.
   */
  SELF isLike(String property, String value, boolean convertGlob);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the (NOT) LIKE operator
   * (" [NOT ]LIKE ").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isLike(String property, String value, boolean convertGlob, boolean not);

  /**
   * This method calls {@link #isOperator(String, String, Object)} using the LIKE operator (" LIKE ").
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @return this instance itself.
   */
  SELF isNotLike(String property, String value, boolean convertGlob);

  /**
   * This method calls {@link #isOperatorInvers(Object, String, String)} using the LIKE operator (" LIKE ").
   * 
   * @param value is the value to use as first argument.
   * @param property is the property to use as string argument. See also {@link #getPropertyPrefix()}.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @return this instance itself.
   */
  SELF isLikeInverse(String value, String property, boolean convertGlob);

  /**
   * This method calls {@link #isOperatorInvers(Object, String, String)} using the (NOT) LIKE operator
   * (" [NOT ]LIKE ").
   * 
   * @param value is the value to use as first argument.
   * @param property is the property to use as string argument. See also {@link #getPropertyPrefix()}.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isLikeInverse(String value, String property, boolean convertGlob, boolean not);

  /**
   * This method adds a null check expression to the query (property + " IS NULL ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isNull(String property);

  /**
   * This method adds a (not) null check expression to the query (property + " IS [NOT] NULL ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isNull(String property, boolean not);

  /**
   * This method adds a not null check expression to the query (property + " IS NOT NULL ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isNotNull(String property);

  /**
   * This method adds an empty check expression to the query (property + " IS EMPTY ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isEmpty(String property);

  /**
   * This method adds a (not) empty check expression to the query (property + " IS [NOT] EMPTY ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isEmpty(String property, boolean not);

  /**
   * This method adds a not empty check expression to the query (property + " IS NOT EMPTY ").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isNotEmpty(String property);

  /**
   * This method adds an empty check expression to the query (property + " IS MEMBER " + collectionProperty).
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param collectionProperty is the property of the collection to check.
   * @return this instance itself.
   */
  SELF isMemberOf(String property, String collectionProperty);

  /**
   * This method adds an (not) empty check expression to the query (property + " IS [NOT] MEMBER " +
   * collectionProperty).
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param collectionProperty is the property of the collection to check.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isMemberOf(String property, String collectionProperty, boolean not);

  /**
   * This method adds a not empty check expression to the query (property + " IS NOT MEMBER " +
   * collectionProperty).
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param collectionProperty is the property of the collection to check.
   * @return this instance itself.
   */
  SELF isNotMemberOf(String property, String collectionProperty);

  /**
   * This method adds a between expression to the query (property + " BETWEEN :min AND :max").
   * 
   * @param <T> is the generic type of <code>min</code> and <code>max</code>.
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
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
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  <T> SELF isBetween(String property, T min, T max, boolean not);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param range is the {@link Range}.
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @return this instance itself.
   */
  SELF isBetween(String property, Range<?> range, boolean not);

  /**
   * This method adds a between expression to the query (property + " BETWEEN :min AND :max").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param minProperty is the property with the minimum value. See also {@link #getPropertyPrefix()}.
   * @param maxProperty is the property with the maximum value. See also {@link #getPropertyPrefix()}.
   * @return this instance itself.
   */
  SELF isBetweenProperty(String property, String minProperty, String maxProperty);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param property is the property to check. See also {@link #getPropertyPrefix()}.
   * @param minProperty is the property with the minimum value. See also {@link #getPropertyPrefix()}.
   * @param maxProperty is the property with the maximum value. See also {@link #getPropertyPrefix()}.
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
   * This method gets the current property prefix that is automatically appended before a given property.
   * 
   * @return the empty string if {@link #getPropertyBasePath()} is empty. Otherwise
   *         {@link #getPropertyBasePath()} + ".".
   */
  String getPropertyPrefix();

  /**
   * This method gets the current property path used as {@link #getPropertyPrefix() prefix} for properties.
   * The default is {@link #getEntityAlias()}.
   * 
   * @see #getPropertyPrefix()
   * 
   * @return the current property base path.
   */
  String getPropertyBasePath();

  /**
   * This method sets the value of {@link #getPropertyBasePath()}.
   * 
   * @param path is the new value of {@link #getPropertyBasePath()}. May be the empty {@link String}, an alias
   *        or path expression.
   * @return this instance itself.
   */
  SELF setPropertyBasePath(String path);

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

}
