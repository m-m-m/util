/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;

/**
 * This interface gives read access to the size (consisting of {@link #getWidth() width} and
 * {@link #getHeight() height}) of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSize {

  /**
   * This method gets the width of this object.
   * 
   * @return the width. Will be {@link Length#ZERO} if undefined (NOT set).
   */
  Length getWidth();

  /**
   * This method gets the height of this object.
   * 
   * @return the height. Will be {@link Length#ZERO} if undefined (NOT set).
   */
  Length getHeight();

}
