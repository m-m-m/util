/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract implementation of a {@link Segment} based on {@link Double}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDoubleSegment extends AbstractSegment<Double> {

  /** UID for serialization. */
  private static final long serialVersionUID = 9206218315093630999L;

  /**
   * The constructor for de-serialization.
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

}
