/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getColumnSpan() columnSpan} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteColumnSpan extends AttributeReadColumnSpan {

  /**
   * This method sets the {@link #getColumnSpan() columnSpan} attribute. <br>
   * <b>ATTENTION:</b><br>
   * Please note that this is the number of columns spanned by this object (and not the number of columns to
   * join). This means the default value is <code>1</code> and not <code>0</code>. To join with the next
   * column, you need to provide the value <code>2</code>.
   * 
   * @see #getColumnSpan()
   * 
   * @param columnSpan is the new value of {@link #getColumnSpan()}. Should be at least <code>1</code>. As
   *        <code>1</code> is the default, a typical value will be greater than <code>1</code>.
   */
  void setColumnSpan(int columnSpan);

}
