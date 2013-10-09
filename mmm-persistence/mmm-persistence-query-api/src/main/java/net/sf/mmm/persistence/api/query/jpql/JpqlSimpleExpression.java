/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api.query.jpql;

import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the interface used to build a simple condition or comparison of a {@link JpqlConditionalExpression}
 * typically for a {@link JpqlConditionalExpression#property(String) property}. You can call {@link #not()}
 * and may only call a single {@link #isCondition(JpqlCondition) condition},
 * {@link #isCompare(JpqlOperator, String, String) comparison} or {@link #isBetween(Object, Object) between
 * expression} method.
 * 
 * @see JpqlConditionalExpression#property(String)
 * @see JpqlConditionalExpression#subQuery(net.sf.mmm.persistence.api.query.SimpleQuery)
 * 
 * @param <T> is the generic type of the property. May be {@link Object} for untyped access.
 * @param <EXPRESSION> is the generic type of the {@link JpqlConditionalExpression} that created this
 *        {@link JpqlSimpleExpression}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface JpqlSimpleExpression<T, EXPRESSION extends JpqlConditionalExpression<?, ?>> extends JpqlCore {

  /**
   * This method negates the following {@link #isCompare(JpqlOperator, Object) comparison},
   * {@link #isCondition(JpqlCondition) condition} or {@link #isBetween(Object, Object) between expression}.
   * It may be called only once per instance of {@link JpqlSimpleExpression}.
   * 
   * @return this instance itself.
   */
  JpqlSimpleExpression<T, EXPRESSION> not();

  /**
   * This method adds the specified comparison expression to the {@link JpqlFragment}. It will compare this
   * property with the specified property using the given <code>operator</code>.
   * 
   * @param operator is the {@link JpqlOperator} used for the comparison.
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isCompare(JpqlOperator operator, String basePath, String property);

  /**
   * This method adds the specified comparison expression to the {@link JpqlFragment}. It will compare this
   * property with the specified property using the given <code>operator</code>.
   * 
   * @param operator is the {@link JpqlOperator} used for the comparison.
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isCompare(JpqlOperator operator, String basePath, TypedProperty<T> property);

  /**
   * This method adds the specified comparison expression to the {@link JpqlFragment}. It will compare this
   * property with a given dynamic <code>value</code> using the given <code>operator</code>.<br/>
   * <b>IMPORTANT:</b><br/>
   * If the given <code>value</code> is <code>null</code> and <code>operator</code> is
   * {@link JpqlOperator#EQUAL} or {@link JpqlOperator#NOT_EQUAL}, then an according {@link #isNull()} or
   * {@link #not()}.{@link #isNull()} is created instead.
   * 
   * @param operator is the {@link JpqlOperator} used for the comparison.
   * @param value is the value to compare to (used as second argument).
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isCompare(JpqlOperator operator, T value);

  /**
   * This method adds the specified comparison expression to the {@link JpqlFragment}. It will compare a given
   * dynamic <code>value</code> with this property with using the given <code>operator</code>.<br/>
   * <b>ATTENTION:</b><br/>
   * In most cases you should use {@link #isCompare(JpqlOperator, Object)} instead. This method is only for
   * special cases such as {@link #isLikeInverse(String, boolean) inverse like expressions}.
   * 
   * @param operator is the {@link JpqlOperator} used for the comparison.
   * @param value is the value to be compared (used as first argument).
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isCompareInverse(JpqlOperator operator, T value);

  /**
   * This method is calling {@link #isCompare(JpqlOperator, Object)} using the operator
   * {@link JpqlOperator#EQUAL}.
   * 
   * @param value is the value to compare to (used as second argument).
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isEqual(T value);

  /**
   * This method is calling {@link #isCompare(JpqlOperator, String, String)} using the operator
   * {@link JpqlOperator#EQUAL}.
   * 
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isEqual(String basePath, String property);

  /**
   * This method is calling {@link #isCompare(JpqlOperator, String, TypedProperty)} using the operator
   * {@link JpqlOperator#EQUAL}.
   * 
   * @param basePath - see {@link JpqlCore#PROPERTY_BASE_PATH}.
   * @param property - see {@link JpqlCore#PROPERTY}.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isEqual(String basePath, TypedProperty<T> property);

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
   * This method is similar to {@link #isCompare(JpqlOperator, Object)} using the operator
   * {@link JpqlOperator#LIKE}.<br/>
   * <b>NOTE:</b><br/>
   * {@link JpqlOperator#LIKE} operations only make sense on {@link String} based datatypes. So this method
   * may only be called if {@literal <T>} is {@link String} or a {@link net.sf.mmm.util.lang.api.Datatype}
   * mapped like {@link String}.
   * 
   * @param pattern is the value with the pattern to match.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isLike(String pattern, boolean convertGlob);

  /**
   * This method is similar to {@link #isCompareInverse(JpqlOperator, Object)} using the operator
   * {@link JpqlOperator#LIKE}.<br/>
   * <b>NOTE:</b><br/>
   * {@link JpqlOperator#LIKE} operations only make sense on {@link String} based datatypes. So this method
   * may only be called if {@literal <T>} is {@link String} or a {@link net.sf.mmm.util.lang.api.Datatype}
   * mapped like {@link String}.
   * 
   * @param pattern is the value with the pattern to match against the value of the given
   *        <code>property</code>.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isLikeInverse(String pattern, boolean convertGlob);

  /**
   * This method adds a given conditional expression to the query (property + " " + condition).
   * 
   * @see #isNull()
   * @see #isEmpty()
   * 
   * @param condition is the {@link JpqlCondition} to add.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isCondition(JpqlCondition condition);

  /**
   * This method calls {@link #isCondition(JpqlCondition)} using {@link JpqlCondition#IS_NULL}.
   * 
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isNull();

  /**
   * This method calls {@link #isCondition(JpqlCondition)} using {@link JpqlCondition#IS_EMPTY}.
   * 
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isEmpty();

  /**
   * This method adds a between expression to the query (property + " BETWEEN :min AND :max").
   * 
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isBetween(T min, T max);

  /**
   * This method adds a (not) between expression to the query (property + " [NOT ]BETWEEN :min AND :max").
   * 
   * @param range is the {@link Range}.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isBetween(Range<? extends T> range);

  /**
   * This method adds a between expression to the query (property +
   * " [NOT ]BETWEEN [minBasePath.]minProperty AND [maxBasePath.]maxProperty").
   * 
   * @param minBasePath is the {@link JpqlCore#PROPERTY_BASE_PATH base path} for <code>minProperty</code>.
   * @param minProperty is the {@link JpqlCore#PROPERTY property} with the minimum value.
   * @param maxBasePath is the {@link JpqlCore#PROPERTY_BASE_PATH base path} for <code>maxProperty</code>.
   * @param maxProperty is the {@link JpqlCore#PROPERTY property} with the maximum value.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isBetween(String minBasePath, TypedProperty<T> minProperty, String maxBasePath,
      TypedProperty<T> maxProperty);

  /**
   * This method adds a between expression to the query (property +
   * " [NOT ]BETWEEN [minBasePath.]minProperty AND [maxBasePath.]maxProperty").
   * 
   * @param minBasePath is the {@link JpqlCore#PROPERTY_BASE_PATH base path} for <code>minProperty</code>.
   * @param minProperty is the {@link JpqlCore#PROPERTY property} with the minimum value.
   * @param maxBasePath is the {@link JpqlCore#PROPERTY_BASE_PATH base path} for <code>maxProperty</code>.
   * @param maxProperty is the {@link JpqlCore#PROPERTY property} with the maximum value.
   * @return the {@link JpqlConditionalExpression} that created this {@link JpqlSimpleExpression}.
   */
  EXPRESSION isBetween(String minBasePath, String minProperty, String maxBasePath, String maxProperty);
}
