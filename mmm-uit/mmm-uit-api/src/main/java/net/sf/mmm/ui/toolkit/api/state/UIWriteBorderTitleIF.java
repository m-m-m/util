/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read and write access to the
 * {@link #getBorderTitle() border-title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteBorderTitleIF extends UIReadBorderTitleIF {

    /**
     * This method set the border-title of this object.
     * 
     * @param borderTitle -
     *        the title of this object's border.
     */
    void setBorderTitle(String borderTitle);

}