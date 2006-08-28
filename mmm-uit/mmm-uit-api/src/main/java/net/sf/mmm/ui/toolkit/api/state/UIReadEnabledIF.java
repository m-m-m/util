/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the enabled-flag of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadEnabledIF {

    /**
     * This method determines if this object is enabled. If it is disabled (NOT
     * enabled), the user can NOT interact with the object.
     * 
     * @return <code>true</code> if this object is enabled, <code>false</code>
     *         if this object is disabled.
     */
    boolean isEnabled();

}
