/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;

/**
 * This interface gives read and write access to the {@link #setMinimumSize(Length, Length) minimum size} of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMinimumSize extends AttributeReadMinimumSize {

  /**
   * This method sets the {@link #getMinimumWidth() minimum width} of this object.
   * 
   * @param minWidth is the new {@link #getMinimumWidth() minimum width}.
   */
  void setMinimumWidth(Length minWidth);

  /**
   * This method sets the {@link #getMinimumHeight() minimum height} of this object.
   * 
   * @param minHeight is the new {@link #getMinimumHeight() minimum height}.
   */
  void setMinimumHeight(Length minHeight);

  /**
   * This method sets the size of this object.
   * 
   * @see #getMinimumWidth()
   * @see #getMinimumHeight()
   * 
   * @param minWidth is the new {@link #getMinimumWidth() width} of the object.
   * @param minHeight is the new {@link #getMinimumHeight() height} of the object.
   */
  void setMinimumSize(Length minWidth, Length minHeight);

}
