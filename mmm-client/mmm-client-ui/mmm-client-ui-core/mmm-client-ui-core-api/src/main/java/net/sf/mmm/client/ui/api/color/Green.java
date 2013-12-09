/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.color;

/**
 * This {@link Segment} represents the <em>green</em> part of a {@link GenericColor color}.
 * 
 * @see Color#getGreen()
 * @see GenericColor#getGreen()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Green extends Factor {

  /** UID for serialization. */
  private static final long serialVersionUID = 323656018661251252L;

  /**
   * The constructor for de-serialization.
   */
  protected Green() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Green(Double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public Green(double value) {

    super(value);
  }

  /**
   * The constructor.
   * 
   * @param byteValue is the {@link #getValueAsByte() value given as byte}.
   */
  public Green(int byteValue) {

    super(byteValue);
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} given as {@link #toStringAsFactor() factor} or
   *        {@link #toStringAsPercent() percent} {@link String}.
   */
  public Green(String value) {

    super(value);
  }

}
