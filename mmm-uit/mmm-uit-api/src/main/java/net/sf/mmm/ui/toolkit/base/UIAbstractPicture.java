/* $Id$ */
package net.sf.mmm.ui.toolkit.base;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIPictureIF} interface.<br>
 * Set initial {@link #setSize(int, int) site} in constructor of the
 * implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractPicture extends UIAbstractObject implements UIPictureIF {

    /** the current width (may scale) */
    private int width;

    /** the current height (may scale) */
    private int height;

    /**
     * The constructor.
     * 
     * @param uiFactory
     */
    public UIAbstractPicture(UIFactoryIF uiFactory) {

        super(uiFactory);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * 
     */
    public void setSize(int newWidth, int newHeight) {

        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * 
     */
    public boolean isResizeable() {

        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * 
     */
    public int getWidth() {

        return this.width;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * 
     */
    public int getHeight() {

        return this.height;
    }

}
