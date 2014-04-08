/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.color;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * The abstract base implementation of {@link Segment}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSegment<V extends Number> extends AbstractSimpleDatatype<V> implements Segment<V> {

  /** UID for serialization. */
  private static final long serialVersionUID = -2574365836749680058L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractSegment() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value}.
   */
  public AbstractSegment(V value) {

    super(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getValueAsPercent() {

    return (int) Math.round(getValueAsFactor() * 100);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toStringAsFactor() {

    return formatDouble(getValueAsFactor());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toStringAsPercent() {

    return formatDouble(getValueAsFactor() * 100) + "%";
  }

  /**
   * @param d is the {@link Double} value.
   * @return the string representation formatted in a human friendly way (no scientific notation, limited
   *         number of decimal digits, etc.) with potential precision loss.
   */
  private static String formatDouble(double d) {

    // TODO prevent scientific notation, limit digits, etc. without using DecimalFormat or the like...
    return Double.toString(d);
  }
}
