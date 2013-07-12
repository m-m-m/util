/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This is a simple utility for operations on {@link SizeUnit}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class SizeUnitHelper {

  /**
   * The constructor.
   */
  private SizeUnitHelper() {

    super();
  }

  /**
   * This method gets the {@link Length#getAmount() amount} of the given {@link Length} in
   * {@link SizeUnit#PIXEL pixels}.<br/>
   * <b>ATTENTION:</b><br/>
   * This implementation is NOT precise and should only used when you know what you are doing.
   * 
   * @param length is the {@link Length}.
   * @param basePixelLength is the base length in pixel used to convert from {@link SizeUnit#PERCENT percent}.
   *        Use <code>0</code> if unknown (what will cause an exception in case of {@link SizeUnit#PERCENT
   *        percent}).
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
          throw new IllegalCaseException(SizeUnit.class, length.getUnit());
        }
        return length.getAmount() * basePixelLength / 100D;
      default :
        throw new IllegalCaseException(SizeUnit.class, length.getUnit());
    }
  }
}
