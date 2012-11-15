/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import net.sf.mmm.persistence.api.jpa.query.JpqlConditionalExpression;
import net.sf.mmm.persistence.api.jpa.query.JpqlOperator;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.Range;

/**
 * This is the implementation of {@link JpqlConditionalExpression}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * @param <SELF> is the generic type reflecting the type of this instance itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public abstract class AbstractJpqlConditionalExpression<E, SELF extends JpqlConditionalExpression<E, SELF>> extends
    AbstractJpqlFragment<E> implements JpqlConditionalExpression<E, SELF> {

  /** @see #setConjunctionDefaultToOr() */
  private boolean conjunctionDefaultOr;

  /**
   * <code>true</code> if a expression (e.g. {@link #isCompareValue(String, JpqlOperator, Object)} has
   * previously been added), <code>false</code> otherwise (initially or after conjunction like {@link #and()}
   * or {@link #or()} has been added.
   */
  private boolean hasPreviousExpression;

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlQueryContext}.
   * @param propertyBasePath - see {@link #getPropertyBasePath()}.
   */
  public AbstractJpqlConditionalExpression(JpqlQueryContext<E> context, String propertyBasePath) {

    super(context);
    setPropertyBasePath(propertyBasePath);
    this.conjunctionDefaultOr = false;
    this.hasPreviousExpression = false;
  }

  /**
   * This method automatically appends the {@link #setConjunctionDefaultToAnd() default conjunction} as
   * needed.
   */
  private void appendConjunctionIfRequired() {

    if (this.hasPreviousExpression) {
      appendConjunction(this.conjunctionDefaultOr);
    }
  }

  /**
   * This method appends a conjunction.
   * 
   * @param or - <code>true</code> for {@link #JPQL_CONJUNCTION_OR}, <code>false</code> for
   *        {@link #JPQL_CONJUNCTION_AND}.
   */
  private void appendConjunction(boolean or) {

    if (!this.hasPreviousExpression) {
      // TODO hohwille create explicit exception
      throw new NlsIllegalStateException();
    }
    String conjunction;
    if (or) {
      conjunction = JPQL_CONJUNCTION_OR;
    } else {
      conjunction = JPQL_CONJUNCTION_AND;
    }
    getContext().getQueryBuffer().append(conjunction);
    this.hasPreviousExpression = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> SELF isCompare(TypedProperty<T> property, JpqlOperator operator, T value) {

    return isCompareValue(property.getPojoPath(), operator, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> SELF isCompare(TypedProperty<T> property, JpqlOperator operator, TypedProperty<T> otherProperty) {

    return isCompareProperties(property.getPojoPath(), operator, otherProperty.getPojoPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> SELF isCompare(T value, JpqlOperator operator, TypedProperty<T> property) {

    return isCompareInvers(value, operator, property.getPojoPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isCompareValue(String property, JpqlOperator operator, Object value) {

    return isCompare(property, operator.getTitle(), value, false, true);
  }

  /**
   * @see #isCompareValue(String, JpqlOperator, Object)
   * 
   * @param property - see #isCompareValue(String, JpqlOperator, Object)
   * @param operator - see #isCompareValue(String, JpqlOperator, Object)
   * @param value - see #isCompareValue(String, JpqlOperator, Object)
   * @param inverse - <code>true</code> if <code>property</code> is second argument and <code>value</code> is
   *        first (see {@link #isCompareInvers(Object, JpqlOperator, String)}), <code>false</code> otherwise.
   * @param convertGlob - <code>true</code> if {@link #convertGlobPattern(String)} shall be invoked for LIKEs,
   *        <code>false</code> otherwise.
   * @return this instance.
   */
  private SELF isCompare(String property, String operator, Object value, boolean inverse, boolean convertGlob) {

    appendConjunctionIfRequired();
    Object parameter = value;
    JpqlQueryContext<E> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    if (parameter == null) {
      appendProperty(property);
      if (JpqlOperator.EQUAL.getTitle().equals(operator)) {
        queryBuffer.append(JPQL_CONDITION_IS_NULL);
      } else if (JpqlOperator.NOT_EQUAL.getTitle().equals(operator)) {
        queryBuffer.append(JPQL_CONDITION_IS_NOT_NULL);
      } else {
        throw new NlsNullPointerException("value");
      }
    } else {
      if (JpqlOperator.LIKE.equals(operator) || JpqlOperator.NOT_LIKE.equals(operator)) {
        if (!(parameter instanceof CharSequence)) {
          throw new NlsClassCastException(parameter, CharSequence.class);
        }
        if (convertGlob) {
          parameter = convertGlobPattern(parameter.toString());
        }
      }
      if (inverse) {
        context.addParameter(parameter, property);
        queryBuffer.append(operator);
        appendProperty(property);
      } else {
        appendProperty(property);
        queryBuffer.append(operator);
        context.addParameter(parameter, property);
      }
    }
    this.hasPreviousExpression = true;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isCompareProperties(String property, JpqlOperator operator, String otherProperty) {

    return isCompareProperties(otherProperty, operator.getTitle(), otherProperty);
  }

  /**
   * @see #isCompareProperties(String, JpqlOperator, String)
   * 
   * @param property - see {@link #isCompareProperties(String, JpqlOperator, String)}.
   * @param operator - see {@link #isCompareProperties(String, JpqlOperator, String)}.
   * @param otherProperty - see {@link #isCompareProperties(String, JpqlOperator, String)}.
   * @return this instance.
   */
  private SELF isCompareProperties(String property, String operator, String otherProperty) {

    appendConjunctionIfRequired();
    JpqlQueryContext<E> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    appendProperty(property);
    queryBuffer.append(operator);
    appendProperty(otherProperty);
    this.hasPreviousExpression = true;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isCompareInvers(Object value, JpqlOperator operator, String property) {

    return isCompare(property, operator.getTitle(), value, true, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isLikeValue(String property, String pattern, boolean convertGlob, boolean not) {

    return isLike(property, pattern, convertGlob, not, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isLikeInverse(String pattern, String property, boolean convertGlob, boolean not) {

    return isLike(property, pattern, convertGlob, not, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isLike(TypedProperty<String> property, String pattern, boolean convertGlob, boolean not) {

    return isLike(property.getPojoPath(), pattern, convertGlob, not, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isLike(String pattern, TypedProperty<String> property, boolean convertGlob, boolean not) {

    return isLikeInverse(pattern, property.getPojoPath(), convertGlob, not);
  }

  /**
   * @see #isLikeValue(String, String, boolean, boolean)
   * @see #isLikeInverse(String, String, boolean, boolean)
   * 
   * @param property is the property to use as first argument. See also {@link #getPropertyPrefix()}.
   * @param value is the value to use as second argument.
   * @param convertGlob - <code>true</code> if the given <code>value</code> should be
   *        {@link #convertGlobPattern(String) converted from GLOB to SQL syntax}, <code>false</code>
   *        otherwise (if the value should be used as is).
   * @param not - <code>true</code> if the expression shall be negated ("NOT "), <code>false</code> otherwise.
   * @param inverse - <code>true</code> if called from
   *        {@link #isLikeInverse(String, String, boolean, boolean)} (value is first argument, property is
   *        second argument), <code>false</code> otherwise.
   * @return this instance itself.
   */
  private SELF isLike(String property, String value, boolean convertGlob, boolean not, boolean inverse) {

    JpqlOperator operator;
    if (not) {
      operator = JpqlOperator.NOT_LIKE;
    } else {
      operator = JpqlOperator.LIKE;
    }
    return isCompare(property, operator.getTitle(), value, inverse, convertGlob);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isCondition(String property, String condition) {

    appendConjunctionIfRequired();
    JpqlQueryContext<E> context = getContext();
    StringBuilder queryBuffer = context.getQueryBuffer();
    appendProperty(property);
    queryBuffer.append(' ');
    queryBuffer.append(condition);
    this.hasPreviousExpression = true;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isNull(String property) {

    return isCondition(property, JPQL_CONDITION_IS_NULL);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isNull(String property, boolean not) {

    String condition;
    if (not) {
      condition = JPQL_CONDITION_IS_NOT_NULL;
    } else {
      condition = JPQL_CONDITION_IS_NULL;
    }
    return isCondition(property, condition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isNotNull(String property) {

    return isNull(property, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isEmpty(String property) {

    return isEmpty(property, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isEmpty(String property, boolean not) {

    String condition;
    if (not) {
      condition = JPQL_CONDITION_IS_NOT_EMPTY;
    } else {
      condition = JPQL_CONDITION_IS_EMPTY;
    }
    return isCondition(property, condition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isNotEmpty(String property) {

    return isEmpty(property, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> SELF isBetween(String property, T min, T max) {

    return isBetween(property, min, max, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> SELF isBetween(String property, T min, T max, boolean not) {

    // property BETWEEN :min AND :max <=> property >= :min AND property <= :max
    if (min == null) {
      if (max == null) {
        throw new NlsNullPointerException("min & max");
      }
      if (not) {
        return isCompareValue(property, JpqlOperator.GREATER_THAN, max);
      } else {
        return isCompareValue(property, JpqlOperator.LESS_EQUAL, max);
      }
    } else if (max == null) {
      if (not) {
        return isCompareValue(property, JpqlOperator.LESS_THAN, min);
      } else {
        return isCompareValue(property, JpqlOperator.GREATER_EQUAL, min);
      }
    }
    String operator;
    if (not) {
      operator = JPQL_OPERATOR_NOT_BETWEEN;
    } else {
      operator = JPQL_OPERATOR_BETWEEN;
    }

    StringBuilder queryBuffer = getContext().getQueryBuffer();
    isCompare(property, operator, min, false, false);
    queryBuffer.append(JPQL_CONJUNCTION_AND);
    getContext().addParameter(max, property + "Max");
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isBetween(String property, Range<?> range, boolean not) {

    NlsNullPointerException.checkNotNull(Range.class, range);
    return isBetween(property, range.getMin(), range.getMax(), not);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isBetweenProperty(String property, String minProperty, String maxProperty) {

    return isBetweenProperty(property, minProperty, maxProperty, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF isBetweenProperty(String property, String minProperty, String maxProperty, boolean not) {

    String operator;
    if (not) {
      operator = JPQL_OPERATOR_NOT_BETWEEN;
    } else {
      operator = JPQL_OPERATOR_BETWEEN;
    }
    StringBuilder queryBuffer = getContext().getQueryBuffer();
    isCompareProperties(property, operator, minProperty);
    queryBuffer.append(JPQL_CONJUNCTION_AND);
    appendProperty(property);
    return (SELF) this;
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
  public SELF setConjunctionDefaultToAnd() {

    this.conjunctionDefaultOr = false;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF setConjunctionDefaultToOr() {

    this.conjunctionDefaultOr = true;
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF and() {

    appendConjunction(false);
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF or() {

    appendConjunction(true);
    return (SELF) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SELF setPropertyBasePath(String path) {

    return (SELF) super.setPropertyBasePath(path);
  }

}
