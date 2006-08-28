/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that can be sized.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteSizeIF extends UIReadSizeIF {

    /**
     * This method sets the size of this object.
     * 
     * @param width
     *        is the new width of the object.
     * @param height
     *        is the new height of the object.
     */
    void setSize(int width, int height);

    /**
     * This method determines if this object is resizeable.
     * 
     * @return <code>true</code> if the object can be resized by the user.
     *         <code>false</code> if the {@link #setSize(int, int)} method has
     *         no effect.
     */
    boolean isResizeable();

}
