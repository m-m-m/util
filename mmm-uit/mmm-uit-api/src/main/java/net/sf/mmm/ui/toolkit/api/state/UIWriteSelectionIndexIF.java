/* $Id: UIWriteSelectionIndexIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the
 * {@link #getSelectedIndex() selection-index} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteSelectionIndexIF extends UIReadSelectionIndexIF {

    /**
     * This method set the selection to the item at the given index.
     * 
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElement(int)
     * 
     * @param newIndex
     *        is the index of the item to select.
     */
    void setSelectedIndex(int newIndex);

}
