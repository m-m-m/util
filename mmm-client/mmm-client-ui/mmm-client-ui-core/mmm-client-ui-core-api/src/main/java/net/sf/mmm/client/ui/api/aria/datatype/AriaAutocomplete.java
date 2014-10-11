/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.EnumType;

/**
 * This enum contains the possible values for
 * {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaAutocomplete#getAutocomplete()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaAutocomplete implements EnumType<String> {

  /** Indicates that the system provides text after the caret as a suggestion for how to complete the field. */
  INLINE("i", "inline"),

  /** Indicates that a list of choices appears from which the user can choose, but the edit box retains focus. */
  LIST("l", "list"),

  /**
   * Indicates the combination of {@link #INLINE} and {@link #LIST}: A list of choices appears and the
   * currently selected suggestion also appears inline.
   */
  BOTH("b", "both"),

  /**
   * Indicates that no input completion suggestions are provided.
   */
  NONE("n", "none");

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
  private AriaAutocomplete(String value, String title) {

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
