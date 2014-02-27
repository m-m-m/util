/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the possible values for a <a
 * href="http://www.w3.org/TR/wai-aria/states_and_properties#valuetype_tristate">tristate</a>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaTristate implements SimpleDatatype<String> {

  /** Indicating {@link Boolean#TRUE}. */
  TRUE("true", "true"),

  /** Indicating {@link Boolean#FALSE}. */
  FALSE("false", "false"),

  /** Indicating a mixed mode value. */
  MIXED("mixed", "mixed");

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string representation}.
   */
  private AriaTristate(String value, String title) {

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
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

}
