/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.IllegalCaseException;

/**
 * This enum represents a vertical alignment of some object.<br>
 * 
 * @see HorizontalAlignment
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public enum VerticalAlignment implements SimpleDatatype<String> {

  /** Align content to the top. */
  TOP("^", NlsBundleUtilCoreRoot.INF_TOP),

  /** Align content to the bottom. */
  BOTTOM("_", NlsBundleUtilCoreRoot.INF_BOTTOM),

  /**
   * Align content centered to the middle (same space at top and bottom).
   */
  CENTER("-", NlsBundleUtilCoreRoot.INF_CENTER);

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
  private VerticalAlignment(String value, String title) {

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
   * This method gets the corresponding {@link Alignment}.
   * 
   * @return the corresponding {@link Alignment}.
   */
  public Alignment getAlignment() {

    switch (this) {
      case TOP:
        return Alignment.TOP;
      case CENTER:
        return Alignment.CENTER;
      case BOTTOM:
        return Alignment.BOTTOM;
      default :
        throw new IllegalCaseException(VerticalAlignment.class, this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link VerticalAlignment} with the given <code>{@link #getValue() value}</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link VerticalAlignment}.
   * @return the requested {@link VerticalAlignment}.
   */
  public static VerticalAlignment fromValue(String value) {

    for (VerticalAlignment alignment : values()) {
      if (alignment.value.equals(value)) {
        return alignment;
      }
    }
    return null;
  }

}
