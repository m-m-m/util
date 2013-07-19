/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.io.Serializable;

import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This class represents a range of two values, {@link #getMin() minimum} and {@link #getMax() maximum}.
 * Validation is performed at {@link #Range(Comparable, Comparable) construction} so a given {@link Range}
 * should always be valid (unless created via reflection or de-serialization).
 * 
 * @param <V> is the generic type of the contained values.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class Range<V extends Comparable<V>> implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 6426261544538415827L;

  /** @see #getMin() */
  private V min;

  /** @see #getMax() */
  private V max;

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
    NlsNullPointerException.checkNotNull("from", min);
    NlsNullPointerException.checkNotNull("to", max);
    int delta = min.compareTo(max);
    if (delta > 0) {
      throw new ValueOutOfRangeException(null, min, min, max);
    }
    this.min = min;
    this.max = max;
  }

  /**
   * @return the lower bound of this range. Must NOT be <code>null</code> and NOT be less than
   *         {@link #getMax() max}.
   */
  public V getMin() {

    return this.min;
  }

  /**
   * @return the upper bound of this range. Must NOT be <code>null</code> and NOT be greater than
   *         {@link #getMin() min}.
   */
  public V getMax() {

    return this.max;
  }

  /**
   * This method determines if the given <code>value</code> is within this {@link Range} from
   * {@link #getMin() minimum} to {@link #getMax() maximum}.
   * 
   * @param value is the vale to check.
   * @return <code>true</code> if contained ({@link #getMin() minimum} &lt;= <code>value</code> &lt;=
   *         {@link #getMax() maximum}).
   */
  public boolean isContained(V value) {

    NlsNullPointerException.checkNotNull("value", value);
    int delta = value.compareTo(this.min);
    if (delta < 0) {
      // value < min
      return false;
    }
    delta = value.compareTo(this.max);
    if (delta > 0) {
      // value > max
      return false;
    }
    return true;
  }

  /**
   * This method verifies that the given <code>value</code> is {@link #isContained(Comparable) contained in
   * this range}.
   * 
   * @param value is the value to check.
   * @throws ValueOutOfRangeException if not {@link #isContained(Comparable) contained}.
   */
  public void verifyContained(V value) throws ValueOutOfRangeException {

    if (!isContained(value)) {
      throw new ValueOutOfRangeException(null, value, this.min, this.max);
    }
  }

}
