/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;

/**
 * This interface gives {@link #getOrientation() read} access to the
 * {@link net.sf.mmm.ui.toolkit.api.composite.Orientation} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadOrientationIF {

    /**
     * This method gets the orientation of this object. The object is layed out
     * along the axis defined by the orientation.
     * 
     * @return the objects orientation.
     */
    Orientation getOrientation();

}
