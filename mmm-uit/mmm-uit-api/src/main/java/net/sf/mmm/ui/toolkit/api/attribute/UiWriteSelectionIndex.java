/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the
 * {@link #getSelectedIndex() selection-index} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteSelectionIndex extends UiReadSelectionIndex {

  /**
   * This method set the selection to the item at the given index.
   * 
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElement(int)
   * 
   * @param newIndex is the index of the item to select.
   */
  void setSelectedIndex(int newIndex);

}
