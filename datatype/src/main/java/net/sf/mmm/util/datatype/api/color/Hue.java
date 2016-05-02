/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the main characteristic of a a color by the degree of the angle around that
 * axis in the color cube starting with red at 0°.
 * 
 * @see GenericColor#getHue()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Hue extends AbstractDoubleSegment<Hue> {

  /** The factor for percent values (360 / 100). */
  private static final double PERCENT_FACTOR = 3.6;

  /** UID for serialization. */
  private static final long serialVersionUID = 3822877998535396427L;

  /**
   * If {@link Chroma} is {@code 0} then {@link Hue} is actually undefined. This value represent this
   * undefined value. For convenience a {@link #getValue() value} of 0° is used as representation.
   */
  public static final Hue UNDEFINED = new Hue(0);

  /** The maximum value allowed. */
  public static final double MAX_VALUE = 360;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected Hue() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the #getValue()
   */
  public Hue(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the #getValue()
   */
  public Hue(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link String} in {@link #toString() default} or
   *        {@link #toStringAsPercent() percent} representation.
   */
  public Hue(String value) {

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
      return Double.valueOf(percent * PERCENT_FACTOR);
    } else {
      return Double.valueOf(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double getMaximumValue() {

    return Double.valueOf(MAX_VALUE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Hue newInstance(double value) {

    return new Hue(value);
  }

}
