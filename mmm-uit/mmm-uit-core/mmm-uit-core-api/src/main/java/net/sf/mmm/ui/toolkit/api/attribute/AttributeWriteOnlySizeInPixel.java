/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #setSizeInPixel(int, int) size} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteOnlySizeInPixel {

  /**
   * This method sets the size of this object in pixels.
   * 
   * @see AttributeReadSizeInPixel#getWidthInPixel()
   * @see AttributeReadSizeInPixel#getHeightInPixel()
   * 
   * @param width is the new {@link AttributeReadSizeInPixel#getWidthInPixel() width} of the object.
   * @param height is the new {@link AttributeReadSizeInPixel#getHeightInPixel() height} of the object.
   */
  void setSizeInPixel(int width, int height);

  /**
   * This method sets the {@link AttributeReadSizeInPixel#getWidthInPixel() width} of this object in pixels.
   * 
   * @see AttributeReadSizeInPixel#getWidthInPixel()
   * 
   * @param width is the new {@link AttributeReadSizeInPixel#getWidthInPixel() width} of the object.
   */
  void setWidthInPixel(int width);

  /**
   * This method sets the {@link AttributeReadSizeInPixel#getHeightInPixel() height} of this object in pixels.
   * 
   * @see AttributeReadSizeInPixel#getHeightInPixel()
   * 
   * @param height is the new {@link AttributeReadSizeInPixel#getHeightInPixel() height} of the object.
   */
  void setHeightInPixel(int height);

}
