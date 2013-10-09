/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query.jpql;

import net.sf.mmm.persistence.api.query.jpql.JpqlCondition;
import net.sf.mmm.persistence.api.query.jpql.JpqlConditionalExpression;
import net.sf.mmm.persistence.api.query.jpql.JpqlCore;
import net.sf.mmm.persistence.api.query.jpql.JpqlOperator;
import net.sf.mmm.persistence.api.query.jpql.JpqlSimpleExpression;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the implementation of {@link JpqlSimpleExpression}.
 * 
 * @param <T> is the generic type of the property. May be {@link Object} for untyped access.
 * @param <EXPRESSION> is the generic type of the {@link AbstractJpqlConditionalExpression} that created this
 *        {@link JpqlSimpleExpression}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 0.9.0
 */
public class JpqlPropertyExpressionImpl<T, EXPRESSION extends JpqlConditionalExpression<?, ?>> extends
    AbstractJpqlPropertySupport implements JpqlSimpleExpression<T, EXPRESSION> {

  /** The {@link AbstractJpqlConditionalExpression} that created this {@link JpqlSimpleExpression}. */
  private final EXPRESSION expression;

  /** The actual property represented by this object. */
  private final String mainProperty;

  /** @see #not() */
  private boolean not;

  /**
   * The constructor.
   * 
   * @param expression is the {@link AbstractJpqlConditionalExpression} that created this
   *        {@link JpqlSimpleExpression}.
   * @param path is the {@link JpqlCore#PROPERTY_BASE_PATH base path} for the <code>property</code>.
   * @param property is the actual {@link JpqlCore#PROPERTY property} for this expression.
   */
  public JpqlPropertyExpressionImpl(EXPRESSION expression, String path, String property) {

    super();
    this.expression = expression;
    if (path == null) {
      setPropertyBasePath(this.expression.getPropertyBasePath());
    } else {
      setPropertyBasePath(path);
    }
    this.mainProperty = property;
  }

  /**
   * @see AbstractJpqlConditionalExpression#appendConjunctionIfRequired()
   */
  protected void appendConjunctionIfRequired() {

    ensureNotDisposed();
    ((AbstractJpqlConditionalExpression<?, ?>) this.expression).appendConjunctionIfRequired();
  }

  /**
   * This method completes and {@link #dispose() disposes} this expression.
   */
  protected void complete() {

    ((AbstractJpqlConditionalExpression<?, ?>) this.expression).setExpressionAppended();
    dispose();
  }

  /**
   * @return the {@link JpqlContext}.
   */
  protected JpqlContext<?> getContext() {

    return ((AbstractJpqlConditionalExpression<?, ?>) this.expression).getContext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlSimpleExpression<T, EXPRESSION> not() {

    this.not = !this.not;
    return this;
  }

  /**
   * @see #isCompare(JpqlOperator, Object)
   * 
   * @param operatorArgument - see #isCompareValue(String, JpqlOperator, Object)
   * @param value - see #isCompareValue(String, JpqlOperator, Object)
   * @param inverse - <code>true</code> if <code>property</code> is second argument and <code>value</code> is
   *        first (see {@link #isCompareInverse(JpqlOperator, Object)}), <code>false</code> otherwise.
   * @param convertGlob - <code>true</code> if {@link #convertGlobPattern(String)} shall be invoked for LIKEs,
   *        <code>false</code> otherwise.
   * @return the {@link AbstractJpqlConditionalExpression}.
   */
  private EXPRESSION isCompare(JpqlOperator operatorArgument, Object value, boolean inverse, boolean convertGlob) {

    appendConjunctionIfRequired();
    JpqlOperator operator = operatorArgument;
    if (this.not) {
      operator = operator.negate();
    }
    Object parameter = value;
    JpqlContext<?> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    if (parameter == null) {
      appendProperty(this.mainProperty, context);
      if (operator == JpqlOperator.EQUAL) {
        queryBuffer.append(JPQL_CONDITION_IS_NULL);
      } else if (operator == JpqlOperator.NOT_EQUAL) {
        queryBuffer.append(JPQL_CONDITION_IS_NOT_NULL);
      } else {
        throw new NlsNullPointerException("value");
      }
    } else {
      if ((operator == JpqlOperator.LIKE) || (operator == JpqlOperator.NOT_LIKE)) {
        if (!(parameter instanceof CharSequence)) {
          throw new NlsClassCastException(parameter, CharSequence.class);
        }
        if (convertGlob) {
          parameter = convertGlobPattern(parameter.toString());
        }
      }
      if (inverse) {
        context.addParameter(parameter, this.mainProperty);
        queryBuffer.append(operator);
        appendProperty(this.mainProperty, context);
      } else {
        appendProperty(this.mainProperty, context);
        queryBuffer.append(operator);
        context.addParameter(parameter, this.mainProperty);
      }
    }
    complete();
    return this.expression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isCompare(JpqlOperator operatorArgument, String basePath, String property) {

    appendConjunctionIfRequired();
    JpqlOperator operator = operatorArgument;
    if (this.not) {
      operator = operator.negate();
    }
    JpqlContext<?> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    appendProperty(this.mainProperty, context);
    queryBuffer.append(operator);
    appendProperty(basePath, property, context);
    complete();
    return this.expression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isCompare(JpqlOperator operator, String basePath, TypedProperty<T> property) {

    return isCompare(operator, basePath, property.getPojoPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isCompare(JpqlOperator operator, T value) {

    return isCompare(operator, value, false, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isCompareInverse(JpqlOperator operator, T value) {

    return isCompare(operator, value, true, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isEqual(T value) {

    return isCompare(JpqlOperator.EQUAL, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isEqual(String basePath, String property) {

    return isCompare(JpqlOperator.EQUAL, basePath, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isEqual(String basePath, TypedProperty<T> property) {

    return isCompare(JpqlOperator.EQUAL, basePath, property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String convertGlobPattern(String pattern) {

    // TODO hohwille Do we need to escape % and _ before?
    return pattern.replace('*', '%').replace('?', '_');
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isLike(String pattern, boolean convertGlob) {

    return isCompare(JpqlOperator.LIKE, pattern, false, convertGlob);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isLikeInverse(String pattern, boolean convertGlob) {

    return isCompare(JpqlOperator.LIKE, pattern, true, convertGlob);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isCondition(JpqlCondition conditionArgument) {

    appendConjunctionIfRequired();
    JpqlCondition condition = conditionArgument;
    if (this.not) {
      condition = condition.negate();
    }
    JpqlContext<?> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    appendProperty(this.mainProperty, context);
    queryBuffer.append(' ');
    queryBuffer.append(condition);
    complete();
    return this.expression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isNull() {

    return isCondition(JpqlCondition.IS_NULL);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isEmpty() {

    return isCondition(JpqlCondition.IS_EMPTY);
  }

  /**
   * This method starts an {@link #isBetween(Object, Object) is between} expression.
   */
  private void startIsBetween() {

    String operator;
    if (this.not) {
      operator = JpqlCore.JPQL_OPERATOR_NOT_BETWEEN;
    } else {
      operator = JpqlCore.JPQL_OPERATOR_BETWEEN;
    }

    appendConjunctionIfRequired();
    JpqlContext<?> context = getContext();
    appendProperty(this.mainProperty, context);
    context.getQueryBuffer().append(operator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isBetween(T min, T max) {

    // property BETWEEN :min AND :max <=> property >= :min AND property <= :max
    if (min == null) {
      if (max == null) {
        throw new NlsNullPointerException("min & max");
      }
      return isCompare(JpqlOperator.LESS_EQUAL, max);
    } else if (max == null) {
      return isCompare(JpqlOperator.GREATER_EQUAL, min);
    }
    startIsBetween();
    JpqlContext<?> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    context.addParameter(min, this.mainProperty + "Min");
    queryBuffer.append(JpqlCore.JPQL_CONJUNCTION_AND);
    context.addParameter(max, this.mainProperty + "Max");
    complete();
    return this.expression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isBetween(Range<? extends T> range) {

    return isBetween(range.getMin(), range.getMax());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isBetween(String minBasePath, TypedProperty<T> minProperty, String maxBasePath,
      TypedProperty<T> maxProperty) {

    return isBetween(minBasePath, minProperty.getPojoPath(), maxBasePath, maxProperty.getPojoPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EXPRESSION isBetween(String minBasePath, String minProperty, String maxBasePath, String maxProperty) {

    startIsBetween();
    JpqlContext<?> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    appendProperty(minBasePath, minProperty, context);
    queryBuffer.append(JpqlCore.JPQL_CONJUNCTION_AND);
    appendProperty(maxBasePath, maxProperty, context);
    complete();
    return this.expression;
  }

}
