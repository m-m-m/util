/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.widgets;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Fruit {

  APPLE("Apple"),

  BANANA("Banana"),

  ORANGE("Orange"),

  PINEAPPLE("Pineapple");

  /** @see #toString() */
  private final String string;

  /**
   * The constructor.
   *
   * @param string - see {@link #toString()}.
   */
  private Fruit(String string) {

    this.string = string;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.string;
  }

}
