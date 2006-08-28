/* $Id: UIAbstractPicture.java 191 2006-07-24 21:00:49Z hohwille $ */
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
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * {@inheritDoc}
     */
    public void setSize(int newWidth, int newHeight) {

        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * {@inheritDoc}
     */
    public boolean isResizeable() {

        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        return this.width;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        return this.height;
    }

}
