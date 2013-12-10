/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the lightness of a {@link Color}. Just like {@link Brightness} it is a
 * {@link Factor factor} indicating the amount of light emitted by a {@link Color}, but relative to the
 * {@link Brightness} of a similarly illuminated white.
 * 
 * @see GenericColor#getLightness()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Lightness extends Factor<Lightness> {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization.
   */
  protected Lightness() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Lightness(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Lightness(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Lightness(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Lightness newInstance(double value) {

    return new Lightness(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return toStringAsPercent();
  }

}
