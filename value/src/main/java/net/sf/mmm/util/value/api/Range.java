/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ValueOutOfRangeException;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMinimumValue;
import net.sf.mmm.util.lang.base.ComparableComparator;

/**
 * This class represents a range of two values, {@link #getMin() minimum} and {@link #getMax() maximum}. Validation is
 * performed at {@link #Range(Object, Object) construction} so a given {@link Range} should always be valid (unless
 * created via reflection or de-serialization). <br>
 * <b>ATTENTION:</b><br>
 * Since version 4.0.0 the {@link #getMinimumValue() minimum} and {@link #getMaximumValue() maximum value} may be
 * {@code null} for unbounded ranges. It is still recommended to use fixed bounds such as {@link Long#MAX_VALUE}.
 * However, for types such as {@link java.math.BigDecimal} this is not possible.
 *
 * @param <V> is the generic type of the contained values.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@SuppressWarnings("rawtypes")
public class Range<V> implements Serializable, AttributeReadMinimumValue<V>, AttributeReadMaximumValue<V> {

  private static final long serialVersionUID = 6426261544538415827L;

  private V min;

  private V max;

  /** The unbound minimum. */
  public static final String MIN_UNBOUND = "\u2212\u221E";

  /** The unbound maximum. */
  public static final String MAX_UNBOUND = "+\u221E";

  /**
   * The constructor for serialization.
   */
  protected Range() {

    super();
  }

  /**
   * The constructor.
   *
   * @param min - see {@link #getMin()}. To create an open range use the minimum value.
   * @param max - see {@link #getMax()}. To create an open range use the maximum value.
   */
  public Range(V min, V max) {

    super();
    if ((min != null) && (max != null)) {
      int delta = getComparator().compare(min, max);
      if (delta > 0) {
        throw new ValueOutOfRangeException(min, min, max);
      }
    }
    this.min = min;
    this.max = max;
  }

  /**
   * Shorthand form for {@link #getMinimumValue()}.
   *
   * @return the lower bound of this range. Must NOT be {@code null} and NOT be less than {@link #getMax() max}.
   */
  public V getMin() {

    return this.min;
  }

  /**
   * Shorthand form for {@link #getMaximumValue()}.
   *
   * @return the upper bound of this range. Must NOT be {@code null} and NOT be greater than {@link #getMin() min}.
   */
  public V getMax() {

    return this.max;
  }

  /**
   * {@inheritDoc}
   *
   * @see #getMin()
   *
   * @since 4.0.0
   */
  @Override
  public V getMinimumValue() {

    return this.min;
  }

  /**
   * {@inheritDoc}
   *
   * @see #getMax()
   *
   * @since 4.0.0
   */
  @Override
  public V getMaximumValue() {

    return this.max;
  }

  /**
   * @param minimum the new {@link #getMinimumValue() minimum value}.
   * @return a new {@link Range} where the {@link #getMinimumValue() minimum value} is set to the given {@code min}
   *         value.
   * @since 7.4.0
   */
  public Range<V> withMin(V minimum) {

    if (Objects.equals(this.min, minimum)) {
      return this;
    }
    return new Range<>(minimum, this.max);
  }

  /**
   * @param maximum the new {@link #getMaximumValue() maximum value}.
   * @return a new {@link Range} where the {@link #getMaximumValue() maximum value} is set to the given {@code max}
   *         value.
   * @since 7.4.0
   */
  public Range<V> withMax(V maximum) {

    if (Objects.equals(this.max, maximum)) {
      return this;
    }
    return new Range<>(this.min, maximum);
  }

  /**
   * @return the {@link Comparator} used to {@link Comparator#compare(Object, Object) compare} values of this
   *         {@link Range}. The default implementation assumes that the value type implements {@link Comparable}. If you
   *         want to use other value types you need to create a sub-class of {@link Range} and override this method.
   */
  @SuppressWarnings("unchecked")
  protected Comparator<? super V> getComparator() {

    return (Comparator) ComparableComparator.getInstance();
  }

  /**
   * This method determines if the given {@code value} is within this {@link Range} from {@link #getMin() minimum} to
   * {@link #getMax() maximum}.
   *
   * @param value is the vale to check.
   * @return {@code true} if contained ({@link #getMin() minimum} &lt;= {@code value} &lt;= {@link #getMax() maximum}).
   */
  public boolean isContained(V value) {

    NlsNullPointerException.checkNotNull("value", value);

    Comparator<? super V> comparator = getComparator();
    int delta;
    if (this.min != null) {
      delta = comparator.compare(value, this.min);
      if (delta < 0) {
        // value < min
        return false;
      }
    }
    if (this.max != null) {
      delta = comparator.compare(value, this.max);
      if (delta > 0) {
        // value > max
        return false;
      }
    }
    return true;
  }

  /**
   * This method verifies that the given {@code value} is {@link #isContained(Object) contained in this range} .
   *
   * @param value is the value to check.
   * @throws ValueOutOfRangeException if not {@link #isContained(Object) contained}.
   */
  public void verifyContained(V value) throws ValueOutOfRangeException {

    if (!isContained(value)) {
      throw new ValueOutOfRangeException(null, value, this.min, this.max);
    }
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Range)) {
      return false;
    }
    Range<?> other = (Range<?>) obj;
    if (!Objects.equals(this.min, other.min)) {
      return false;
    }
    if (!Objects.equals(this.max, other.max)) {
      return false;
    }
    return true;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(this.min, this.max);
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    buffer.append('[');
    if (this.min == null) {
      buffer.append(MIN_UNBOUND);
    } else {
      buffer.append(this.min);
    }
    buffer.append(',');
    if (this.max == null) {
      buffer.append(MAX_UNBOUND);
    } else {
      buffer.append(this.max);
    }
    buffer.append(']');
    return buffer.toString();
  }
}
