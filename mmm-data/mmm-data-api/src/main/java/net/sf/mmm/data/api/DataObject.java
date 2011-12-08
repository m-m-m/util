/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

/**
 * This is the interface for a mutable {@link DataObjectView data object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataObject extends DataObjectView {

  /**
   * This method sets the {@link #getDeletedFlag() deleted flag}.
   * 
   * @param deleted is the deleted flag to set.
   */
  void setDeletedFlag(boolean deleted);

  /**
   * This method sets the {@link #getTitle() title} or in other words it renames
   * the object.<br/>
   * <b>ATTENTION:</b><br/>
   * The title of some {@link #getDataClassId() object types} has to be unique.
   * 
   * @param title is the new title.
   */
  void setTitle(String title);

}
