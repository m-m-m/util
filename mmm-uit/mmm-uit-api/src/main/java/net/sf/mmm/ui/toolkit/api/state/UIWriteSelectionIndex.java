/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the
 * {@link #getSelectedIndex() selection-index} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteSelectionIndex extends UIReadSelectionIndex {

  /**
   * This method set the selection to the item at the given index.
   * 
   * @see net.sf.mmm.ui.toolkit.api.model.UIListModel#getElement(int)
   * 
   * @param newIndex
   *        is the index of the item to select.
   */
  void setSelectedIndex(int newIndex);

}
