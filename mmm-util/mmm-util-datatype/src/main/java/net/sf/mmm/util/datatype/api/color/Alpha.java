/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the alpha value of a color what is its opacity. A value of <code>0</code>
 * stands for fully transparent (color not visible), while <code>1.0</code> stands for a regular color (color
 * fully visible). An alpha value of <code>0.5</code> will be 50% transparent so you can see the color but the
 * background shines through.
 * 
 * @see Color#getAlpha()
 * @see GenericColor#getAlpha()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Alpha extends Factor {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /** The maximum {@link Alpha} value for full opaque (no transparency). */
  public static final Alpha OPAQUE = new Alpha(1.0);

  /**
   * The constructor for de-serialization.
   */
  protected Alpha() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Alpha(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Alpha(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Alpha(String value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param byteValue is the {@link #getValueAsByte() value given as byte}.
   */
  public Alpha(int byteValue) {

    super(byteValue);
  }

}
