/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * This enum contains the available values for the alignment of an object.
 * 
 * @see HorizontalAlignment
 * @see VerticalAlignment
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public enum Alignment implements Datatype<String> {

  /**
   * the component will be horizontally and vertically centered.<br>
   * 000<br>
   * 0x0<br>
   * 000<br>
   */
  CENTER("-~", NlsBundleUtilCoreRoot.INF_CENTER) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return CENTER;
    }
  },

  /**
   * the component will be located at the top and horizontally centered.<br>
   * 0x0<br>
   * 000<br>
   * 000<br>
   */
  TOP("^~", NlsBundleUtilCoreRoot.INF_TOP) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return BOTTOM;
    }
  },

  /**
   * the component will be located at the bottom and horizontally centered.<br>
   * 000<br>
   * 000<br>
   * 0x0<br>
   */
  BOTTOM("_~", NlsBundleUtilCoreRoot.INF_BOTTOM) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return TOP;
    }

  },

  /**
   * the component will be located at the left and vertically centered.<br>
   * 000<br>
   * x00<br>
   * 000<br>
   */
  LEFT("--", NlsBundleUtilCoreRoot.INF_LEFT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 00x<br>
   * 000<br>
   */
  RIGHT("-+", NlsBundleUtilCoreRoot.INF_RIGHT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return LEFT;
    }

  },

  /**
   * the component will be located at the right and vertically centered.<br>
   * x00<br>
   * 000<br>
   * 000<br>
   */
  TOP_LEFT("^-", NlsBundleUtilCoreRoot.INF_TOP_LEFT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return BOTTOM_RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered.<br>
   * 00x<br>
   * 000<br>
   * 000<br>
   */
  TOP_RIGHT("^+", NlsBundleUtilCoreRoot.INF_TOP_RIGHT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return BOTTOM_LEFT;
    }

  },

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 000<br>
   * x00<br>
   */
  BOTTOM_LEFT("_-", NlsBundleUtilCoreRoot.INF_BOTTOM_LEFT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return TOP_RIGHT;
    }

  },

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 000<br>
   * 00x<br>
   */
  BOTTOM_RIGHT("_+", NlsBundleUtilCoreRoot.INF_BOTTOM_RIGHT) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Alignment getMirrored() {

      return TOP_LEFT;
    }

  };

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
  private Alignment(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * This method gets the {@link Alignment} with the given <code>{@link #getValue() value}</code>.
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

}
