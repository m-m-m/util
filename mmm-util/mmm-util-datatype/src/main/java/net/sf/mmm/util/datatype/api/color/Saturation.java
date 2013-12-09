/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} is a {@link Factor factor} that represents the colorfulness of a stimulus relative to
 * its own brightness.<br/>
 * <b>ATTENTION:</b><br/>
 * The saturation has different definitions for {@link ColorModel#HSB} and {@link ColorModel#HSL}. Hence
 * transformation from other {@link ColorModel}s like {@link ColorModel#RGB} differs and will return different
 * results for {@link Saturation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Saturation extends Factor {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization.
   */
  protected Saturation() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Saturation(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Saturation(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Saturation(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return toStringAsPercent();
  }

}
