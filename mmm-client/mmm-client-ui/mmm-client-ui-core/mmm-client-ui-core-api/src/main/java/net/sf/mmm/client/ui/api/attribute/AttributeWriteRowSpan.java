/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getRowSpan() rowSpan} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteRowSpan extends AttributeReadRowSpan {

  /**
   * This method sets the {@link #getRowSpan() rowSpan} attribute.<br/>
   * <b>ATTENTION:</b><br/>
   * Please note that this is the number of row spanned by this object (and not the number of rows to join).
   * This means the default value is <code>1</code> and not <code>0</code>. To join with the next row, you
   * need to provide the value <code>2</code>.
   * 
   * @see #getRowSpan()
   * 
   * @param rowSpan is the new value of {@link #getRowSpan()}. Should be at least <code>1</code>. As
   *        <code>1</code> is the default, a typical value will be greater than <code>1</code>.
   */
  void setRowSpan(int rowSpan);

}
