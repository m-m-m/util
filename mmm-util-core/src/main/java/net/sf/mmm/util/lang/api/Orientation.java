/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * This enum contains the available values for the orientation.
 * 
 * @see Alignment
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public enum Orientation implements SimpleDatatype<String> {

  /**
   * A horizontal orientation means that objects are ordered from the left to the right.
   */
  HORIZONTAL("-", NlsBundleUtilCoreRoot.INF_HORIZONTAL),

  /**
   * A vertical orientation means that objects are ordered from the top to the bottom.
   */
  VERTICAL("|", NlsBundleUtilCoreRoot.INF_VERTICAL);

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
  private Orientation(String value, String title) {

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
  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link Orientation} with the given <code>{@link #getValue() value}</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link Orientation}.
   * @return the requested {@link Orientation}.
   */
  public static Orientation fromValue(String value) {

    for (Orientation alignment : values()) {
      if (alignment.value.equals(value)) {
        return alignment;
      }
    }
    return null;
  }

  /**
   * This method gets the inverse orientation.
   * 
   * @return {@link #VERTICAL} if this orientation is {@link #HORIZONTAL} and vice versa.
   */
  public Orientation getMirrored() {

    if (this == HORIZONTAL) {
      return VERTICAL;
    } else {
      return HORIZONTAL;
    }
  }

}
