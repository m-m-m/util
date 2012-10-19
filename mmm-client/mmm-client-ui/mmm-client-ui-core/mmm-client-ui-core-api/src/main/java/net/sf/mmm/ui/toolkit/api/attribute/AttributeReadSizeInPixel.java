/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the size of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSizeInPixel {

  /**
   * This method gets the width of this object in pixels.
   * 
   * @return the width.
   */
  int getWidthInPixel();

  /**
   * This method gets the height of this object in pixels.
   * 
   * @return the height.
   */
  int getHeightInPixel();

}
