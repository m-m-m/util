/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the available units for size measures.
 * 
 * @see Length
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// absolute units 1in = 2.54cm = 25.4mm = 72pt = 12pc
// for font-size ~ 1em = 12pt = 16px = 100%
public enum LengthUnit implements SimpleDatatype<String> {

  /** Unit for pixels. */
  PIXEL("px", "pixel"),

  /** Unit for percent, relative to the parent container. At topmost the entire window. */
  PERCENT("%", "percent"),

  /** Unit for <em>em</em> meaning a factor relative to the current font-size. */
  EM("em", "font-size");

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #toString()}.
   */
  private LengthUnit(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * Creates a new {@link Length} with this {@link LengthUnit} as {@link Length#getUnit() unit}.
   * 
   * @param amount is the {@link Length#getAmount() amount}.
   * @return the new {@link Length}.
   */
  public Length newLength(double amount) {

    return new Length(amount, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
