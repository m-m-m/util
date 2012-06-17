/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataSelectionView selection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataSelectionView.CLASS_ID, title = DataSelectionView.CLASS_TITLE)
public interface DataSelection extends DataSelectionView, DataObject {

  /**
   * This method sets the {@link #isSelectable() selectable} flag.
   * 
   * @param selectable - <code>true</code> if this instance should be
   *        {@link #isSelectable() selectable}, <code>false</code> otherwise.
   */
  void setSelectable(boolean selectable);

}
