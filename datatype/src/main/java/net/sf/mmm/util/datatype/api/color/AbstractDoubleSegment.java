/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract implementation of a {@link Segment} based on {@link Double}.
 * 
 * @param <SELF> is the generic type of the class itself (bound by the actual final subclass).
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDoubleSegment<SELF extends AbstractDoubleSegment<SELF>> extends AbstractSegment<Double> {

  /** UID for serialization. */
  private static final long serialVersionUID = 9206218315093630999L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractDoubleSegment() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public AbstractDoubleSegment(Double value) {

    super(value);
    double d = value.doubleValue();
    if ((d < 0.0) || (d > getMaximumValue().doubleValue())) {
      throw new ValueOutOfRangeException(value, getMinimumValue(), getMaximumValue());
    }
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public AbstractDoubleSegment(double value) {

    super(Double.valueOf(value));
    if ((value < 0.0) || (value > getMaximumValue().doubleValue())) {
      throw new ValueOutOfRangeException(Double.valueOf(value), getMinimumValue(), getMaximumValue());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getValueAsFactor() {

    return getValue().doubleValue() / getMaximumValue().doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getMinimumValue() {

    return Double.valueOf(0);
  }

  /**
   * Creates a new instance with the given value.
   * 
   * @param value is the {@link #getValue() value}.
   * @return the new instance.
   */
  protected abstract SELF newInstance(double value);

  /**
   * @return a new segment of the same type with the <em>inverted</em> value. Inverted means the complement or
   *         technically <code>{@link #getMaximumValue()} - {@link #getValue()}</code>.
   */
  public SELF invert() {

    return newInstance(getMaximumValue().doubleValue() - getValue().doubleValue());
  }

  /**
   * @param factor is the {@link ColorFactor} to increase by. E.g. <code>0.0</code> will cause no change,
   *        <code>1.0</code> will lead to {@link #getMaximumValue() maximum value}.
   * @return a new segment with the value increased by the given factor through linear interpolation.
   */
  public SELF increase(ColorFactor factor) {

    double d = getValue().doubleValue();
    return newInstance(d + (getMaximumValue().doubleValue() - d) * factor.getValueAsFactor());
  }

  /**
   * @param factor is the {@link ColorFactor} to decrease by. E.g. <code>0.0</code> will cause no change,
   *        <code>1.0</code> will lead to {@link #getMinimumValue() minimum value} (0).
   * @return a new segment with the value decreased by the given factor through linear interpolation.
   */
  public SELF decrease(ColorFactor factor) {

    double d = getValue().doubleValue();
    return newInstance(d - d * factor.getValueAsFactor());
  }

}
