/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

/**
 * This {@link Segment} represents the <em>blue</em> part of a {@link GenericColor color}.
 * 
 * @see Color#getBlue()
 * @see GenericColor#getBlue()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Blue extends Factor<Blue> {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected Blue() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Blue(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Blue(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param byteValue is the {@link #getValueAsByte() value given as byte}.
   */
  public Blue(int byteValue) {

    super(byteValue);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Blue(String value) {

    super(value);
  }

  @Override
  protected Blue newInstance(double value) {

    return new Blue(value);
  }

}
