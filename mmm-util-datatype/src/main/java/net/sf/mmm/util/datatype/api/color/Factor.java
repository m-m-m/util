/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * The implementation of {@link Segment} for a factor in the range from <code>0</code> to <code>1</code> such
 * as {@link Saturation}, {@link Lightness}, or {@link Brightness}. <br>
 * <b>ATTENTION:</b><br>
 * Beside {@link #toStringAsPercent() percent} notation the {@link String} constructor both accepts
 * {@link #getValueAsByte() byte values} as well as {@link #toStringAsFactor() factor} notation.
 * 
 * @param <SELF> is the generic type of the class itself (bound by the actual final subclass).
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class Factor<SELF extends Factor<SELF>> extends AbstractDoubleSegment<SELF> {

  /** UID for serialization. */
  private static final long serialVersionUID = 2276148174440077961L;

  /** @see #getMaximumValue() */
  public static final double MAX_VALUE = 1;

  /** @see #Factor(int) */
  protected static final double BYTE_MAX = 255;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected Factor() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Factor(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Factor(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param byteValue is the {@link #getValueAsByte() value given as byte}.
   */
  public Factor(int byteValue) {

    super(byteValue / BYTE_MAX);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Factor(String value) {

    super(parseValue(value));
  }

  /**
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   * @return the parsed value.
   */
  private static Double parseValue(String value) {

    int length = value.length();
    if (value.charAt(length - 1) == '%') {
      double percent = Double.parseDouble(value.substring(0, length - 1));
      return Double.valueOf(percent / 100.0);
    } else if (value.contains(".")) {
      return Double.valueOf(value);
    } else {
      return Double.valueOf(Integer.parseInt(value) / BYTE_MAX);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getValueAsFactor() {

    return getValue().doubleValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getMaximumValue() {

    return Double.valueOf(MAX_VALUE);
  }

  /**
   * @return the {@link #getValue()} as <code>int</code> in the range from <code>0</code> to <code>255</code>.
   */
  public int getValueAsByte() {

    return (int) (getValueAsFactor() * BYTE_MAX);
  }

  /**
   * @return the {@link #getValueAsByte() byte value} as {@link String}. E.g. "255".
   */
  public String toStringAsByte() {

    return Integer.toString(getValueAsByte());
  }

}
