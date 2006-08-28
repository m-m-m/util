/* $Id: UIProgressBar.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JProgressBar;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.custom.MyProgressBar;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF} interface using
 * Swing as the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIProgressBar extends UIWidget implements UIProgressBarIF {

    /** the native swing widget */
    private final JProgressBar progressBar;
    
    /**
     * The constructor.
     *
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param orientation
     */
    public UIProgressBar(UIFactory uiFactory, UINodeIF parentObject, Orientation orientation) {

        super(uiFactory, parentObject);
        this.progressBar = new MyProgressBar(orientation);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public @Override JComponent getSwingComponent() {

        return this.progressBar;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#getProgress()
     * {@inheritDoc}
     */
    public int getProgress() {

        return this.progressBar.getValue();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#setProgress(int)
     * {@inheritDoc}
     */
    public void setProgress(int newProgress) {

        this.progressBar.setValue(newProgress);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#getOrientation()
     * {@inheritDoc}
     */
    public Orientation getOrientation() {

        if (this.progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#isIndeterminate()
     * {@inheritDoc}
     */
    public boolean isIndeterminate() {

        return this.progressBar.isIndeterminate();
    }

}
