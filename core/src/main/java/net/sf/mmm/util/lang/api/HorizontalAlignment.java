/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.IllegalCaseException;

/**
 * This enum represents a horizontal alignment (e.g. for {@link net.sf.mmm.util.text.api.Justification}). <br>
 * The following table gives an example to illustrate the expected behavior of according to an
 * {@link HorizontalAlignment}.
 * <table border="1">
 * <tr>
 * <th>Alignment</th>
 * <th>Text</th>
 * </tr>
 * <tr>
 * <td>{@link #LEFT}</td>
 * <td><code>Hello&nbsp;&nbsp;&nbsp;&nbsp;</code></td>
 * </tr>
 * <tr>
 * <td>{@link #CENTER}</td>
 * <td><code>&nbsp;&nbsp;Hello&nbsp;&nbsp;</code></td>
 * </tr>
 * <tr>
 * <td>{@link #RIGHT}</td>
 * <td><code>&nbsp;&nbsp;&nbsp;&nbsp;Hello</code></td>
 * </tr>
 * </table>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum HorizontalAlignment implements SimpleDatatype<String> {

  /** Align content to the left side. */
  LEFT("-", NlsBundleUtilCoreRoot.INF_LEFT),

  /** Align content to the right side. */
  RIGHT("+", NlsBundleUtilCoreRoot.INF_RIGHT),

  /**
   * Align content centered to the middle (same space to the left and the right).
   */
  CENTER("~", NlsBundleUtilCoreRoot.INF_CENTER);

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
  private HorizontalAlignment(String value, String title) {

    this.value = value;
    this.title = title;
  }

  @Override
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
      case LEFT:
        return Alignment.LEFT;
      case CENTER:
        return Alignment.CENTER;
      case RIGHT:
        return Alignment.RIGHT;
      default:
        throw new IllegalCaseException(HorizontalAlignment.class, this);
    }
  }

  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link HorizontalAlignment} with the given {@link #getValue() value}.
   *
   * @param value is the {@link #getValue() value} of the requested {@link HorizontalAlignment}.
   * @return the requested {@link HorizontalAlignment}.
   */
  public static HorizontalAlignment fromValue(String value) {

    for (HorizontalAlignment alignment : values()) {
      if (alignment.value.equals(value)) {
        return alignment;
      }
    }
    return null;
  }

}
