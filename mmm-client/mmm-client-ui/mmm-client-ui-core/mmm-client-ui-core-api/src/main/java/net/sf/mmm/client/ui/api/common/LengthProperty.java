/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.client.ui.api.attribute.AttributeReadLengthProperty;
import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum contains the available properties that are configured with {@link Length} values. Both
 * {@link #getValue() value} and {@link #toString() string representation} are the CSS property name.
 * 
 * @see Length
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum LengthProperty implements SimpleDatatype<String>, CssProperty {

  /** The actual width (horizontal length). */
  WIDTH("width", "width"),

  /** The minimum width (horizontal length). */
  MIN_WIDTH("min-width", "minWidth"),

  /** The maximum width (horizontal length). */
  MAX_WIDTH("max-width", "maxWidth"),

  /** The actual height (vertical length). */
  HEIGHT("height", "height"),

  /** The minimum height (vertical length). */
  MIN_HEIGHT("min-height", "minHeight"),

  /** The maximum height (vertical length). */
  MAX_HEIGHT("max-height", "maxHeight");

  /** @see #getStyleName() */
  private final String styleName;

  /** @see #getMemberName() */
  private final String memberName;

  /**
   * The constructor.
   * 
   * @param styleName - see {@link #getStyleName()}.
   * @param memberName - see {@link #getMemberName()};
   */
  private LengthProperty(String styleName, String memberName) {

    this.styleName = styleName;
    this.memberName = memberName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyleName() {

    return this.styleName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMemberName() {

    return this.memberName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return getStyleName();
  }

  /**
   * @return <code>true</code> if {@link #WIDTH}, {@link #MIN_WIDTH}, or {@link #MAX_WIDTH} -
   *         <code>false</code> otherwise.
   */
  public boolean isWidth() {

    return ((this == WIDTH) || (this == MIN_WIDTH) || (this == MAX_WIDTH));
  }

  /**
   * @see Length#convertToPixel(double, LengthProperty)
   * 
   * @param lengthSource is the object to get the {@link Length} from.
   * @param basePixelLength - see Length#convertToPixel(double, LengthProperty)
   * @return the requested {@link Length} in {@link LengthUnit#PIXEL}.
   */
  public double getLengthInPixel(AttributeReadLengthProperty lengthSource, double basePixelLength) {

    Length length = lengthSource.getLength(this);
    return length.convertToPixel(basePixelLength, this);
  }

  /**
   * @see Length#convertToPixel(double, LengthProperty)
   * 
   * @param lengthSource is the object to get the {@link Length} from.
   * @param basePixelSize gives access to read the size of the base container (e.g. screen, parent panel,
   *        etc.).
   * @return the requested {@link Length} in {@link LengthUnit#PIXEL}.
   */
  public double getLengthInPixel(AttributeReadLengthProperty lengthSource, AttributeReadSizeInPixel basePixelSize) {

    Length length = lengthSource.getLength(this);
    double basePixelLength = 0;
    if ((length.getUnit() == LengthUnit.PERCENT) && (length.getAmount() > 0)) {
      if (isWidth()) {
        basePixelLength = basePixelSize.getWidthInPixel();
      } else {
        basePixelLength = basePixelSize.getHeightInPixel();
      }
    }
    return length.convertToPixel(basePixelLength, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.styleName;
  }

  /**
   * @return the default value for this {@link LengthProperty}. Will be {@link Length#MAX} for max properties
   *         and {@link Length#ZERO} otherwise.
   */
  public Length getDefaultValue() {

    if ((this == MAX_HEIGHT) || (this == MAX_WIDTH)) {
      return Length.MAX;
    } else {
      return Length.ZERO;
    }
  }

}
