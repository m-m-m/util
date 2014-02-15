/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.LengthUnit;

/**
 * This interface gives read and write access to the
 * {@link #setSize(net.sf.mmm.client.ui.api.common.Length, net.sf.mmm.client.ui.api.common.Length) size} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSizeAdvanced extends AttributeWriteSize {

  /**
   * This method sets the {@link #getHeight() height} of this object in {@link LengthUnit#PERCENT percent}.
   * 
   * @param heightInPercent is the new {@link #getHeight() height} in {@link LengthUnit#PERCENT percent}.
   */
  void setHeightInPercent(double heightInPercent);

  /**
   * This method sets the {@link #getHeight() height} of this object in {@link LengthUnit#PIXEL pixel}.
   * 
   * @param heightInPixel is the new {@link #getHeight() height} in {@link LengthUnit#PIXEL pixel}.
   */
  void setHeightInPixel(double heightInPixel);

  /**
   * This method sets the {@link #getWidth() width} of this object in {@link LengthUnit#PERCENT percent}.
   * 
   * @param widthInPercent is the new {@link #getWidth() width} in {@link LengthUnit#PERCENT percent}.
   */
  void setWidthInPercent(double widthInPercent);

  /**
   * This method sets the {@link #getWidth() width} of this object in {@link LengthUnit#PIXEL pixel}.
   * 
   * @param widthInPixel is the new {@link #getWidth() width} in {@link LengthUnit#PIXEL pixel}.
   */
  void setWidthInPixel(double widthInPixel);

  /**
   * This method sets the size of this object.
   * 
   * @see #getWidth()
   * @see #getHeight()
   * 
   * @param width is the new {@link #getWidth() width} of the object.
   * @param height is the new {@link #getHeight() height} of the object.
   * @param unit is the {@link net.sf.mmm.client.ui.api.common.Length#getUnit() unit} of <code>width</code>
   *        and <code>height</code>.
   */
  void setSize(double width, double height, LengthUnit unit);

}
