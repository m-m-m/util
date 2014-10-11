/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.EnumType;

/**
 * This enum contains the possible values for
 * {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaRelevant#getRelevant()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaChangeNotifications implements EnumType<String> {

  /**
   * Indicating that element nodes have been added.
   */
  ADDITIONS("+", "additions"),

  /**
   * Indicating that text or element nodes have been removed.
   */
  REMOVALS("-", "removals"),

  /**
   * Indicating that text has been added.
   */
  TEXT("t", "text"),

  /**
   * Indicating the combination of {@link #ADDITIONS}, {@link #REMOVALS}, and {@link #TEXT}.
   */
  ALL("+-t", "all"),

  /**
   * Indicating the combination of {@link #ADDITIONS} and {@link #TEXT}.
   */
  ADDITIONS_TEXT("+t", "additions text"),

  /**
   * Indicating the combination of {@link #ADDITIONS} and {@link #REMOVALS}.
   */
  ADDITIONS_REMOVAL("+-", "additions removal"),

  /**
   * Indicating the combination of {@link #REMOVALS} and {@link #TEXT}.
   */
  REMOVAL_TEXT("-t", "removal text");

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
  private AriaChangeNotifications(String value, String title) {

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
