/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * This enum contains the possible values for
 * {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaInvalid#getInvalid()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaInvalid implements Datatype<String> {

  /**
   * Indicating that a grammatical error was detected.
   */
  GRAMMAR("g", "grammar"),

  /**
   * Indicating that there are no detected errors in the value.
   */
  FALSE("f", "false"),

  /**
   * Indicating that a spelling error was detected.
   */
  SPELLING("s", "spelling"),

  /**
   * Indicating that the value entered by the user has failed validation.
   */
  TRUE("t", "true");

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #getTitle() title}.
   */
  private AriaInvalid(String value, String title) {

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
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
