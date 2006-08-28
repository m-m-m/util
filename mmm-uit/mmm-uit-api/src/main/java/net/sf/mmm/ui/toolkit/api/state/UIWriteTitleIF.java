/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that has a title.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteTitleIF {

    /**
     * This method gets the title of this object.
     * 
     * @return the title.
     */
    String getTitle();

    /**
     * This method sets the title of this object.
     * 
     * @param newTitle
     *            is the new title to set.
     */
    void setTitle(String newTitle);

}
