/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.SizeUnit;

/**
 * This interface gives advanced read and write access to the {@link #setSize(String, String) size} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeWriteSizeAdvanced extends AttributeReadSizeAdvanced, AttributeWriteSize,
    AttributeWriteOnlySizeInPixel {

  /**
   * This method sets the {@link #getWidth() width}.
   * 
   * @param value - see {@link #getWidthValue()}.
   * @param unit - see {@link #getWidthUnit()}.
   */
  void setWidth(double value, SizeUnit unit);

  /**
   * This method sets the {@link #getHeight() height}.
   * 
   * @param value - see {@link #getHeightValue()}.
   * @param unit - see {@link #getHeightUnit()}.
   */
  void setHeight(double value, SizeUnit unit);

  /**
   * This method sets the {@link #getWidth() width} {@link #getHeight() height}.
   * 
   * @param widthValue - see {@link #getWidthValue()}.
   * @param heightValue - see {@link #getHeightValue()}.
   * @param unit is the {@link SizeUnit unit} of the given size values.
   */
  void setSize(double widthValue, double heightValue, SizeUnit unit);

  /**
   * This method sets the {@link #getWidth() width} {@link #getHeight() height}.
   * 
   * @param widthValue - see {@link #getWidthValue()}.
   * @param widthUnit - see {@link #getWidthUnit()}.
   * @param heightValue - see {@link #getHeightValue()}.
   * @param heightUnit - see {@link #getHeightUnit()}.
   */
  void setSize(double widthValue, SizeUnit widthUnit, double heightValue, SizeUnit heightUnit);

}
