/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This enum contains the available values for the alignment of an object.
 *
 * @see HorizontalAlignment
 * @see VerticalAlignment
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public enum Alignment {

  /**
   * the component will be horizontally and vertically centered. <br>
   * <code>
   * ___<br>
   * _<b>*</b>_<br>
   * ___<br>
   * </code>
   */
  CENTER("-~", "center") {

    @Override
    public Alignment getMirrored() {

      return CENTER;
    }
  },

  /**
   * the component will be located at the top and horizontally centered. <br>
   * <code>
   * _<b>*</b>_<br>
   * ___<br>
   * ___<br>
   * </code>
   */
  TOP("^~", "top") {

    @Override
    public Alignment getMirrored() {

      return BOTTOM;
    }
  },

  /**
   * the component will be located at the bottom and horizontally centered. <br>
   * <code>
   * ___<br>
   * ___<br>
   * _<b>*</b>_<br>
   * </code>
   */
  BOTTOM("_~", "bottom") {

    @Override
    public Alignment getMirrored() {

      return TOP;
    }

  },

  /**
   * the component will be located at the left and vertically centered. <br>
   * <code>
   * ___<br>
   * <b>*</b>__<br>
   * ___<br>
   * </code>
   */
  LEFT("--", "left") {

    @Override
    public Alignment getMirrored() {

      return RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered. <br>
   * <code>
   * ___<br>
   * __<b>*</b><br>
   * ___<br>
   * </code>
   */
  RIGHT("-+", "right") {

    @Override
    public Alignment getMirrored() {

      return LEFT;
    }

  },

  /**
   * the component will be located at the right and vertically centered. <br>
   * <code>
   * <b>*</b>__<br>
   * ___<br>
   * ___<br>
   * </code>
   */
  TOP_LEFT("^-", "top left") {

    @Override
    public Alignment getMirrored() {

      return BOTTOM_RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered. <br>
   * <code>
   * __<b>*</b><br>
   * ___<br>
   * ___<br>
   * </code>
   */
  TOP_RIGHT("^+", "top right") {

    @Override
    public Alignment getMirrored() {

      return BOTTOM_LEFT;
    }

  },

  /**
   * the component will be located at the right and vertically centered. <br>
   * <code>
   * ___<br>
   * ___<br>
   * <b>*</b>__<br>
   * </code>
   */
  BOTTOM_LEFT("_-", "bottom left") {

    @Override
    public Alignment getMirrored() {

      return TOP_RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered. <br>
   * <code>
   * ___<br>
   * ___<br>
   * __<b>*</b><br>
   * </code>
   */
  BOTTOM_RIGHT("_+", "bottom right") {

    @Override
    public Alignment getMirrored() {

      return TOP_LEFT;
    }

  };

  private final String value;

  private final String title;

  /**
   * The constructor.
   *
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #toString() string representation}.
   */
  private Alignment(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * @return the ascii symbol.
   */
  public String getValue() {

    return this.value;
  }

  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link Alignment} with the given {@link #getValue() value}.
   *
   * @param value is the {@link #getValue() value} of the requested {@link Alignment}.
   * @return the requested {@link Alignment}.
   */
  public static Alignment fromValue(String value) {

    for (Alignment alignment : values()) {
      if (alignment.value.equals(value)) {
        return alignment;
      }
    }
    return null;
  }

  /**
   * This method gets the horizontal part of the alignment.
   *
   * @return the {@link HorizontalAlignment}.
   */
  public HorizontalAlignment getHorizontalAlignment() {

    if ((this == TOP_LEFT) || (this == LEFT) || (this == BOTTOM_LEFT)) {
      return HorizontalAlignment.LEFT;
    } else if ((this == TOP_RIGHT) || (this == RIGHT) || (this == BOTTOM_RIGHT)) {
      return HorizontalAlignment.RIGHT;
    } else {
      return HorizontalAlignment.CENTER;
    }
  }

  /**
   * This method gets the vertical part of the alignment.
   *
   * @return the {@link VerticalAlignment}.
   */
  public VerticalAlignment getVerticalAlignment() {

    if ((this == TOP_LEFT) || (this == TOP) || (this == TOP_RIGHT)) {
      return VerticalAlignment.TOP;
    } else if ((this == BOTTOM_LEFT) || (this == BOTTOM) || (this == BOTTOM_RIGHT)) {
      return VerticalAlignment.BOTTOM;
    } else {
      return VerticalAlignment.CENTER;
    }
  }

  /**
   * This method extracts the {@link Orientation#HORIZONTAL horizontal} or {@link Orientation#VERTICAL
   * vertical} part of this alignment.
   *
   * @param orientation is the {@link Orientation} of the requested part.
   * @return {@link #getHorizontalAlignment()} if orientation is {@link Orientation#HORIZONTAL},
   *         {@link #getVerticalAlignment()} otherwise.
   */
  public Alignment getPart(Orientation orientation) {

    if (orientation == Orientation.HORIZONTAL) {
      return getHorizontalAlignment().getAlignment();
    } else {
      return getVerticalAlignment().getAlignment();
    }
  }

  /**
   * This method gets the inverse alignment.
   *
   * @return the inverse alignment. E.g. {@link #BOTTOM_RIGHT} for {@link #TOP_LEFT}. Returns itself if
   *         {@link #CENTER}.
   */
  public abstract Alignment getMirrored();

  /**
   * @return the corresponding {@link Direction} or {@code null} for {@link #CENTER}.
   * @since 4.0.0
   */
  public Direction toDirection() {

    switch (this) {
      case BOTTOM:
        return Direction.SOUTH;
      case BOTTOM_LEFT:
        return Direction.SOUTH_WEST;
      case BOTTOM_RIGHT:
        return Direction.SOUTH_EAST;
      case CENTER:
        return null;
      case LEFT:
        return Direction.WEST;
      case RIGHT:
        return Direction.EAST;
      case TOP:
        return Direction.NORTH;
      case TOP_LEFT:
        return Direction.NORTH_WEST;
      case TOP_RIGHT:
        return Direction.NORTH_EAST;
      default :
        throw new IllegalStateException(name());
    }
  }

  /**
   * This is the inverse operation for {@link #toDirection()}.
   *
   * @param direction is the {@link Direction}. May be {@code null} for {@link #CENTER}.
   * @return the corresponding {@link Alignment}.
   * @since 4.0.0
   */
  public static Alignment fromDirection(Direction direction) {

    if (direction == null) {
      return CENTER;
    }
    switch (direction) {
      case EAST:
        return RIGHT;
      case WEST:
        return LEFT;
      case NORTH:
        return BOTTOM;
      case SOUTH:
        return TOP;
      case SOUTH_EAST:
        return BOTTOM_RIGHT;
      case SOUTH_WEST:
        return BOTTOM_LEFT;
      case NORTH_EAST:
        return TOP_RIGHT;
      case NORTH_WEST:
        return TOP_LEFT;
      default :
        throw new IllegalStateException(direction.name());
    }
  }

}
