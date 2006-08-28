/* $Id: UIReadMultiSelectionFlagIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that has a selection
 * mode. The mode can eighter allow selection of a single item or selection of
 * multiple items.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadMultiSelectionFlagIF {

    /**
     * This methods determines the selection mode.
     * 
     * @return <code>true</code> if the user can select multiple items,
     *         <code>false</code> if only a single item can be selected.
     */
    boolean isMultiSelection();

}