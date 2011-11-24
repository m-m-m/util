/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * A {@link BooleanEnum} represents a {@link Boolean} as enum type. This may
 * seem pointless in the first place but is usable for annotations that can not
 * have <code>null</code> values. However, in this case it is often desirable to
 * express a default that is neither <code>true</code> nor <code>false</code>
 * e.g. if the default can be determined from other values. In such case you can
 * use {@link BooleanEnum} instead and {@link BooleanEnum#NULL} as default.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public enum BooleanEnum implements Datatype<Boolean> {

  /**
   * Represents {@link Boolean#TRUE}.
   */
  TRUE(Boolean.TRUE),

  /**
   * Represents {@link Boolean#FALSE}.
   */
  FALSE(Boolean.FALSE),

  /**
   * Repesents <code>null</code>.
   */
  NULL(null);

  /** @see #getValue() */
  private final Boolean value;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   */
  private BooleanEnum(Boolean value) {

    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  public Boolean getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    if (this.value == null) {
      return "null";
    } else {
      return this.value.toString();
    }
  }

  /**
   * This method gets the {@link BooleanEnum} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link BooleanEnum}.
   * @return the requested {@link BooleanEnum}.
   */
  public static BooleanEnum fromValue(Boolean value) {

    if (value == null) {
      return NULL;
    } else if (value.booleanValue()) {
      return TRUE;
    } else {
      return FALSE;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }
}
