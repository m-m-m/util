/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the possible values for
 * {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaLive#getLive()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaLiveLevel implements SimpleDatatype<String> {

  /**
   * Indicating that assistive technologies should notify the user immediately. Because an interruption may
   * disorient users or cause them to not complete their current task, authors should not use the assertive
   * value unless the interruption is imperative.
   */
  ASSERTIVE("a", "assertive"),

  /**
   * Indicating that assistive technologies should announce updates at the next graceful opportunity, such as
   * at the end of speaking the current sentence or when the user pauses typing.
   */
  POLITE("p", "polite"),

  /**
   * Indicating that updates to the region will not be presented to the user unless the assistive technology
   * is currently focused on that region.
   */
  OFF("o", "off");

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
  private AriaLiveLevel(String value, String title) {

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
