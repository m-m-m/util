/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the brightness of a {@link Color}. The brightness is a {@link Factor
 * factor} indicating the amount of light emitted by a {@link Color}.
 * 
 * @see GenericColor#getBrightness()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Brightness extends Factor<Brightness> {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization.
   */
  protected Brightness() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Brightness(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Brightness(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Brightness(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Brightness newInstance(double value) {

    return new Brightness(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return toStringAsPercent();
  }

}
