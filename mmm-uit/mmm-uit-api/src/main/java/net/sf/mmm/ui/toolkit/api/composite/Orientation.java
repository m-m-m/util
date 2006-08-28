/* $Id: Orientation.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.composite;

/**
 * This enum contains the available values for the orientation of a
 * {@link net.sf.mmm.ui.toolkit.api.UIComponentIF component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum Orientation {

    /**
     * A horizontal orientation means that objects are ordered from the left to
     * the right.
     */
    HORIZONTAL,

    /**
     * A vertical orientation means that objects are ordered from the top to the
     * bottom.
     */
    VERTICAL;

    /**
     * This method gets the inverse orientation.
     * 
     * @return {@link #VERTICAL} if this orientation is {@link #HORIZONTAL} and
     *         vice versa.
     */
    public Orientation getMirrored() {

        if (this == HORIZONTAL) {
            return VERTICAL;
        } else {
            return HORIZONTAL;
        }
    }

}
