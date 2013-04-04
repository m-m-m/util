/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.Length;

/**
 * This interface gives read access to the maximum size of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMaximumSize {

  /**
   * This method gets the maximum width of this object.
   * 
   * @return the maximum width or <code>null</code> if undefined (NOT set).
   */
  Length getMaximumWidth();

  /**
   * This method gets the maximum height of this object.
   * 
   * @return the maximum height or <code>null</code> if undefined (NOT set).
   */
  Length getMaximumHeight();

}
