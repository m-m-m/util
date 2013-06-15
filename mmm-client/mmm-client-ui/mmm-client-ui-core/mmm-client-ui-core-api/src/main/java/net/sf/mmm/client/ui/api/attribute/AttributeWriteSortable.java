/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isSortable() sortable} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSortable extends AttributeReadSortable {

  /**
   * This method sets the {@link #isSortable() sortable} attribute.
   * 
   * @param sortable is the new value of {@link #isSortable()}.
   */
  void setSortable(boolean sortable);

}
