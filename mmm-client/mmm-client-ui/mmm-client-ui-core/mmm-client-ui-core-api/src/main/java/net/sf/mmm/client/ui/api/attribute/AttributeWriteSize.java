/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;

/**
 * This interface gives read and write access to the {@link #setSize(Length, Length) size} (consisting of
 * {@link #getWidth() width} and {@link #getHeight() height}) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSize extends AttributeReadSize {

  /**
   * This method sets the {@link #getWidth() width} of this object.
   * 
   * @param width is the new {@link #getWidth() width}.
   */
  void setWidth(Length width);

  /**
   * This method sets the {@link #getHeight() height} of this object.
   * 
   * @param height is the new {@link #getHeight() height}.
   */
  void setHeight(Length height);

  /**
   * This method sets the size of this object.
   * 
   * @see #getWidth()
   * @see #getHeight()
   * 
   * @param width is the new {@link #getWidth() width} of the object.
   * @param height is the new {@link #getHeight() height} of the object.
   */
  void setSize(Length width, Length height);

}
