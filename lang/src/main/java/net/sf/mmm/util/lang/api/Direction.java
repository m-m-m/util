/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This enum contains all possible directions.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public enum Direction implements AttributeReadValue<String> {

  /** Direction to the south (down/bottom). */
  SOUTH("S", "south"),

  /** Direction to the east (right). */
  EAST("E", "east"),

  /** Direction the the west (left). */
  WEST("W", "west"),

  /** Direction the the north (up/top). */
  NORTH("N", "north"),

  /** Direction to the south-east (down/bottom and right). */
  SOUTH_EAST("SE", "south-east"),

  /** Direction to the south-west (down/bottom and left). */
  SOUTH_WEST("SW", "south-west"),

  /** Direction to the north-east (up/top and right). */
  NORTH_EAST("NE", "north-east"),

  /** Direction to the north-west (up/top and left). */
  NORTH_WEST("NW", "north-west");

  private final String value;

  private final String title;

  /**
   * The constructor.
   *
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #toString()}.
   */
  private Direction(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * @return the shortcut representation.
   */
  @Override
  public String getValue() {

    return this.value;
  }

  @Override
  public String toString() {

    return this.title;
  }

  /**
   * @return {@code true} if pointing to the east ({@link #EAST}, {@link #SOUTH_EAST}, or
   *         {@link #NORTH_EAST}), {@code false} otherwise.
   */
  public boolean isToEast() {

    return (this == EAST) || (this == SOUTH_EAST) || (this == NORTH_EAST);
  }

  /**
   * @return {@code true} if pointing to the west ({@link #WEST}, {@link #SOUTH_WEST}, or
   *         {@link #NORTH_WEST}), {@code false} otherwise.
   */
  public boolean isToWest() {

    return (this == WEST) || (this == SOUTH_WEST) || (this == NORTH_WEST);
  }

  /**
   * @return {@code true} if pointing to the south ({@link #SOUTH}, {@link #SOUTH_EAST}, or
   *         {@link #SOUTH_WEST}), {@code false} otherwise.
   */
  public boolean isToSouth() {

    return (this == SOUTH) || (this == SOUTH_EAST) || (this == SOUTH_WEST);
  }

  /**
   * @return {@code true} if pointing to the north ({@link #NORTH}, {@link #NORTH_EAST}, or
   *         {@link #NORTH_WEST}), {@code false} otherwise.
   */
  public boolean isToNorth() {

    return (this == NORTH) || (this == NORTH_EAST) || (this == NORTH_WEST);
  }

}
