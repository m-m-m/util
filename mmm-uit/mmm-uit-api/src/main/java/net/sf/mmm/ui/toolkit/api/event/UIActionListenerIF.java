/* $Id: UIActionListenerIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

import net.sf.mmm.ui.toolkit.api.UINodeIF;

/**
 * This class represents an action that is invoked on an interaction of the user
 * in the UIFactory (e.g. hitting a button).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIActionListenerIF extends EventListener {

    /**
     * This method is called if the user invoked an interaction in a
     * UIComponent.
     * 
     * @param source
     *        is the component that invoked the action.
     * @param action
     *        is the action that has been invoked.
     */
    void invoke(UINodeIF source, ActionType action);

}
