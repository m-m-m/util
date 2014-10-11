/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.datatype;

import net.sf.mmm.util.lang.api.EnumType;

/**
 * This enum contains the possible values for
 * {@link net.sf.mmm.client.ui.api.aria.attribute.AttributeReadAriaDropEffect#getDropEffect()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum AriaDropEffect implements EnumType<String> {

  /**
   * Indicating that a duplicate of the source object will be dropped into the target.
   */
  COPY("c", "copy"),

  /**
   * Indicating that the source object will be removed from its current location and dropped into the target.
   */
  MOVE("m", "move"),

  /**
   * Indicating that a reference or shortcut to the dragged object will be created in the target object.
   */
  LINK("l", "link"),

  /**
   * Indicating that a function supported by the drop target is executed, using the drag source as an input.
   */
  EXECUTE("x", "execute"),

  /**
   * Indicating that a popup menu or dialog allows the user to choose one of the drag operations (copy, move,
   * link, execute) and any other drag functionality, such as cancel.
   */
  POPUP("p", "popup"),

  /**
   * Indicates the combination of {@link #COPY} and {@link #EXECUTE}.
   */
  COPY_EXECUTE("cx", "copy execute"),

  /**
   * Indicates the combination of {@link #LINK} and {@link #EXECUTE}.
   */
  MOVE_EXECUTE("mx", "move execute"),

  /**
   * Indicates the combination of {@link #LINK} and {@link #EXECUTE}.
   */
  LINK_EXECUTE("lx", "link execute"),

  /**
   * Indicating that no operation can be performed. Effectively cancels the drag operation if an attempt is
   * made to drop on this object.
   */
  NONE("", "none");

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string represenation}.
   */
  private AriaDropEffect(String value, String title) {

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
