/* $Id: UIWriteTextIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the text of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteTextIF extends UIReadTextIF {

    /**
     * This method sets the text of this object.
     * 
     * @param text
     *        is the new text for this object.
     */
    void setText(String text);

}