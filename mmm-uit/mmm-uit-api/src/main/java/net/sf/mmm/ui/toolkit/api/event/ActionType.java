/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This enum contains the available action event types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum ActionType {

    /** the action if something is selected */
    SELECT,

    /** the action if something is deselected */
    DESELECT,

    /** the action if something is hidden */
    HIDE,

    /** the action if something is shown */
    SHOW,

    /** the action if something is activated */
    ACTIVATE,

    /** the action if something is deactivated */
    DEACTIVATE,

    /** the action if a window is iconified */
    ICONIFY,

    /** the action if a window is deiconified */
    DEICONIFY,

    /** the action if a window is disposed */
    DISPOSE;

}
