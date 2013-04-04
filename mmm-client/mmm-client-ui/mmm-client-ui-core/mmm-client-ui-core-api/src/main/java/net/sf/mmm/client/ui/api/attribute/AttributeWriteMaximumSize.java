/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;

/**
 * This interface gives read and write access to the {@link #setMaximumSize(Length, Length) maximum size} of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMaximumSize extends AttributeReadMaximumSize {

  /**
   * This method sets the {@link #getMaximumWidth() maximum width} of this object.
   * 
   * @param maxWidth is the new {@link #getMaximumWidth() maximum width}.
   */
  void setMaximumWidth(Length maxWidth);

  /**
   * This method sets the {@link #getMaximumHeight() maximum height} of this object.
   * 
   * @param maxHeight is the new {@link #getMaximumHeight() maximum height}.
   */
  void setMaximumHeight(Length maxHeight);

  /**
   * This method sets the size of this object.
   * 
   * @see #getMaximumWidth()
   * @see #getMaximumHeight()
   * 
   * @param maxWidth is the new {@link #getMaximumWidth() width} of the object.
   * @param maxHeight is the new {@link #getMaximumHeight() height} of the object.
   */
  void setMaximumSize(Length maxWidth, Length maxHeight);

}
