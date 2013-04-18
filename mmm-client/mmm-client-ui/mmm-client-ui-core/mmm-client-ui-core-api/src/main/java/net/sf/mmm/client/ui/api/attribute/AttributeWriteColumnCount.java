/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getColumnCount() columnCount} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteColumnCount extends AttributeReadColumnCount {

  /**
   * This method sets the {@link #getColumnCount() columnCount} attribute.
   * 
   * @see #getColumnCount()
   * 
   * @param columnCount is the new value of {@link #getColumnCount()}. Should be at least <code>1</code>.
   */
  void setColumnCount(int columnCount);

}
