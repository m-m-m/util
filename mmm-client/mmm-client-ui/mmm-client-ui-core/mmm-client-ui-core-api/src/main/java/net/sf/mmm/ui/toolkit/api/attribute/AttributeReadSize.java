/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the size of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSize {

  /**
   * This method gets the width of this object (e.g. "0.9em", "10px", or "90%").
   * 
   * @return the width or <code>null</code> if undefined (NOT set).
   */
  String getWidth();

  /**
   * This method gets the height of this object (e.g. "0.9em", "10px", or "90%").
   * 
   * @return the height or <code>null</code> if undefined (NOT set).
   */
  String getHeight();

}
