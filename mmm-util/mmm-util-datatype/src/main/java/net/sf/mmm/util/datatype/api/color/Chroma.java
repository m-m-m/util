/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the <em>chroma</em> of a {@link Color}. The chroma is a {@link Factor
 * factor} indicating the colorfulness relative to the brightness of a similarly illuminated white.
 * 
 * @see GenericColor#getChroma()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Chroma extends Factor<Chroma> {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization.
   */
  protected Chroma() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Chroma(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Chroma(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param byteValue is the {@link #getValueAsByte() value given as byte}.
   */
  public Chroma(int byteValue) {

    super(byteValue);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Chroma(String value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Chroma newInstance(double value) {

    return new Chroma(value);
  }

}
