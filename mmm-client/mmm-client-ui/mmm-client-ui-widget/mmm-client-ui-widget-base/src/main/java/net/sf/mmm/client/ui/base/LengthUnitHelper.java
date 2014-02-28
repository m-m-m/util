/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is a simple utility for operations on {@link LengthUnit}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class LengthUnitHelper {

  /**
   * The constructor.
   */
  private LengthUnitHelper() {

    super();
  }

  /**
   * This method gets the {@link Length#getAmount() amount} of the given {@link Length} in
   * {@link LengthUnit#PIXEL pixels}.<br/>
   * <b>ATTENTION:</b><br/>
   * This implementation is NOT precise and should only used when you know what you are doing.
   * 
   * @param length is the {@link Length}.
   * @param basePixelLength is the base length in pixel used to convert from {@link LengthUnit#PERCENT
   *        percent}. Use <code>0</code> if unknown (what will result in <code>0</code> in case of
   *        {@link LengthUnit#PERCENT percent} length values).
   * @return the <code>length</code> in pixels.
   */
  public static double convertToPixel(Length length, double basePixelLength) {

    if (length == null) {
      return 0;
    }
    switch (length.getUnit()) {
      case PIXEL:
        return length.getAmount();
      case EM:
        return length.getAmount() * 16.0;
      case PERCENT:
        if (basePixelLength <= 0) {
          return 0;
        }
        return length.getAmount() * basePixelLength / 100D;
      default :
        throw new IllegalCaseException(LengthUnit.class, length.getUnit());
    }
  }

  /**
   * @see UiWidget#getSize()
   * @see #convertToPixel(Length, double)
   * 
   * @param widget is the {@link UiWidget} to get the {@link Length} from.
   * @param property is the {@link LengthProperty} to determine.
   * @param basePixelLength - see {@link #convertToPixel(Length, double)}.
   * @return the requested {@link Length} in {@link LengthUnit#PIXEL}.
   */
  public static double getLengthInPixel(UiWidget widget, LengthProperty property, double basePixelLength) {

    Length length = widget.getSize().getLength(property);
    if ((length.getUnit() == LengthUnit.PERCENT) && (basePixelLength <= 0)) {
      if (length.getAmount() == 0) {
        // 0% of anything is just nothing...
        return 0;
      }
      // 1% or more of unbounded min/max is still unbounded...
      return property.getDefaultValue().getAmount();
    }
    // will actually do work as expected for percent values...
    return convertToPixel(length, basePixelLength);
  }

}
