/* $Id: UIReadSelectionIndexIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the
 * {@link #getSelectedIndex() selection-index} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSelectionIndexIF {

    /**
     * This method gets the index of the selected item.
     * 
     * @return the index of the selected item or <code>-1</code> if no item is
     *         selected.
     */
    int getSelectedIndex();

}
