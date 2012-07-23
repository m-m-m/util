/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #setSize(String, String) size} of an object.
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
  void setWidth(String width);

  /**
   * This method sets the {@link #getHeight() height} of this object.
   * 
   * @param height is the new {@link #getHeight() height}.
   */
  void setHeight(String height);

  /**
   * This method sets the size of this object.
   * 
   * @see #getWidth()
   * @see #getHeight()
   * 
   * @param width is the new {@link #getWidth() width} of the object.
   * @param height is the new {@link #getHeight() height} of the object.
   */
  void setSize(String width, String height);

}
