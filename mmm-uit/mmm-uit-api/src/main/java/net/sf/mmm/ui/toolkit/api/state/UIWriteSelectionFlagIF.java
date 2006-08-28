/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that is selectable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteSelectionFlagIF extends UIReadSelectionFlagIF {

    /**
     * This method sets the selection status of this object.<br>
     * 
     * NOTE: This method does NOT
     * {@link net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF#invoke(net.sf.mmm.ui.toolkit.api.UINodeIF, net.sf.mmm.ui.toolkit.api.event.ActionType) invoke}
     * an action event.
     * 
     * @param selected -
     *        if <code>true</code> the object will be selected, else it will
     *        be deselected.
     */
    void setSelected(boolean selected);

}
