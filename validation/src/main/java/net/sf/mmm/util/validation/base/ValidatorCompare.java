/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Objects;

import net.sf.mmm.util.lang.api.CompareOperator;
import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that {@link #validate(Object, Object) validates that
 * a value} {@link CompareOperator#eval(double, double) satisfies} a given {@link CompareOperator} -operation for given
 * value to compare to.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorCompare<V extends Comparable<V>> extends AbstractValueValidator<V> {

  /** The comparison operator. */
  private final CompareOperator comparator;

  /** @see #validateNotNull(Comparable) */
  private final AttributeReadValue<V> valueSource;

  /** @see #validateNotNull(Comparable) */
  private final String source;

  /** @see #isDynamic() */
  private final boolean dynamic;

  /**
   * The constructor.
   *
   * @param comparator is the {@link CompareOperator comparison operator} used to compare the {@link #validate(Object)
   *        value to validate} (first argument) with the value of the given <code>valueSource</code>.
   * @param valueSource is a reference to something that {@link AttributeReadValue#getValue() provides a value} and will
   *        be evaluated {@link #isDynamic() dynamically} on every {@link #validate(Object) validation}.
   * @param source is a brief description of the <code>valueSource</code> for potential failure messages. E.g. in case
   *        of a user interface the label of the field providing the value. May be <code>null</code>.
   */
  public ValidatorCompare(CompareOperator comparator, AttributeReadValue<V> valueSource, String source) {

    super();
    this.comparator = comparator;
    this.valueSource = valueSource;
    this.dynamic = true;
    this.source = source;
  }

  /**
   * The constructor.
   *
   * @param comparator is the {@link CompareOperator comparison operator} used to compare the {@link #validate(Object)
   *        value to validate} (first argument) with the value of the given <code>value</code>.
   * @param value is the fixed value to compare to.
   */
  public ValidatorCompare(CompareOperator comparator, V value) {

    super();
    this.comparator = comparator;
    this.valueSource = new GenericBean<>(value);
    this.dynamic = false;
    this.source = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDynamic() {

    return this.dynamic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NlsMessage validateNotNull(V value) {

    V value2 = this.valueSource.getValue();
    if (this.comparator.eval(value, value2)) {
      return null;
    }
    if (this.source == null) {
      return createBundle(NlsBundleUtilValidationRoot.class).errorValueComparison(value, this.comparator, value2);
    } else {
      return createBundle(NlsBundleUtilValidationRoot.class).errorValueComparisonWithSource(value, this.comparator,
          value2, this.source);
    }
  }

  @Override
  public int hashCode() {

    return Objects.hash(this.comparator, this.valueSource);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ValidatorCompare<?> other = (ValidatorCompare<?>) obj;
    if (!Objects.equals(this.comparator, other.comparator)) {
      return false;
    }
    if (!Objects.equals(this.valueSource, other.valueSource)) {
      return false;
    }
    return true;
  }

}
