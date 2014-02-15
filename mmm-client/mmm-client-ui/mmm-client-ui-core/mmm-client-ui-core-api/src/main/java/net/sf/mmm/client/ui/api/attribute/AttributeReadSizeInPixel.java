/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;


/**
 * This interface gives read access to the size of an object in
 * {@link net.sf.mmm.client.ui.api.common.LengthUnit#PIXEL}.
 * 
 * @see AttributeReadSize
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSizeInPixel {

  /**
   * This method gets the width of this object in pixels.<br/>
   * <b>NOTE:</b><br/>
   * The result will typically be an <code>int</code> value. However, to be compatible with
   * {@link net.sf.mmm.client.ui.api.common.Length#getAmount()} and with some underlying native toolkits we
   * use <code>double</code> to prevent potential information loss.
   * 
   * @return the width.
   */
  double getWidthInPixel();

  /**
   * This method gets the height of this object in pixels.<br/>
   * <b>NOTE:</b><br/>
   * The result will typically be an <code>int</code> value. However, to be compatible with
   * {@link net.sf.mmm.client.ui.api.common.Length#getAmount()} and with some underlying native toolkits we
   * use <code>double</code> to prevent potential information loss.
   * 
   * @return the height.
   */
  double getHeightInPixel();

}
