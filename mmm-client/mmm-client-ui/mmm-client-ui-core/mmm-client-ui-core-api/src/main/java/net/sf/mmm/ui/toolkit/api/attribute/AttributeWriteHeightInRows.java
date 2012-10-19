/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getHeightInRows() height in rows} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteHeightInRows extends AttributeReadHeightInRows {

  /**
   * This method sets the {@link #getHeightInRows() height in rows} of this object.
   * 
   * @param rows is the new value for {@link #getHeightInRows() height in text lines} to set.
   */
  void setHeightInRows(int rows);

}
