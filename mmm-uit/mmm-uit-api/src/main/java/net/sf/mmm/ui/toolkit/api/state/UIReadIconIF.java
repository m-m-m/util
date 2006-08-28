/* $Id: UIReadIconIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;

/**
 * This interface gives read access to the
 * {@link net.sf.mmm.ui.toolkit.api.UIPictureIF icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadIconIF {

    /**
     * This method gets the icon of this object.
     * 
     * @return the icon of this object or <code>null</code>, if no icon is
     *         set.
     */
    UIPictureIF getIcon();

}
