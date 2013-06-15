/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isSortable() sortable} attribute of an object.
 * 
 * @see AttributeWriteSortable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadSortable {

  /**
   * This method determines if this object can be sorted.
   * 
   * @return <code>true</code> if the object can be sorted (by the end-user) - typically by clicking (e.g. the
   *         header of a column), <code>false</code> otherwise.
   */
  boolean isSortable();

}
