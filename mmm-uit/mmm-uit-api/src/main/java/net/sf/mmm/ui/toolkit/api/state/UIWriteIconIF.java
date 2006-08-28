/* $Id: UIWriteIconIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;

/**
 * This interface gives read and write access to the
 * {@link net.sf.mmm.ui.toolkit.api.UIPictureIF icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteIconIF extends UIReadIconIF {

    /**
     * This method sets the icon of this object.
     * 
     * @param icon
     *        is the new icon for this object.
     */
    void setIcon(UIPictureIF icon);

}
