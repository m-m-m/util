/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the size of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadSizeIF {

    /**
     * This method gets the width of this object in pixels.
     * 
     * @return the width.
     */
    int getWidth();

    /**
     * This method gets the height of this object in pixels.
     * 
     * @return the height.
     */
    int getHeight();

}
