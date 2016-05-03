/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This class is a generic implementation of {@link Factor} used for color transformations such as
 * {@link GenericColor#lighten(ColorFactor)} or {@link GenericColor#darken(ColorFactor)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ColorFactor extends Factor<ColorFactor> {

  /** UID for serialization. */
  private static final long serialVersionUID = -6917422770605923183L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ColorFactor() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public ColorFactor(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public ColorFactor(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public ColorFactor(String value) {

    super(value);
  }

  @Override
  protected ColorFactor newInstance(double value) {

    return new ColorFactor(value);
  }

}
