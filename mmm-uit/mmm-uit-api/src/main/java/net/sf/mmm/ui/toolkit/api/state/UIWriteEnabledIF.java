/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the enabled-flag of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteEnabledIF extends UIReadEnabledIF {

    /**
     * This method sets the enabled status of this object. If it is disabled,
     * the user can not interact with the object.
     * 
     * @param enabled -
     *        if <code>true</code> the object will be enabled, else the object
     *        will be disabled.
     */
    void setEnabled(boolean enabled);

}