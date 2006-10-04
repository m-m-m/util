/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncProgressBarAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIProgressBar extends UIWidget implements UIProgressBarIF {

    /** the native SWT widget */
    private final SyncProgressBarAccess syncAccess;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param orientation
     *        is the orientation of the progress-bar.
     * @param indeterminate
     *        if <code>true</code> the progress-bar will be
     *        {@link #isIndeterminate() indeterminate}.
     */
    public UIProgressBar(UIFactory uiFactory, UISwtNode parentObject, Orientation orientation,
            boolean indeterminate) {

        super(uiFactory, parentObject);
        int style;
        if (orientation == Orientation.HORIZONTAL) {
            style = SWT.HORIZONTAL;
        } else {
            style = SWT.VERTICAL;
        }
        if (indeterminate) {
            style |= SWT.INDETERMINATE;
        }
        this.syncAccess = new SyncProgressBarAccess(uiFactory, style);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * 
     */
    @Override
    public SyncProgressBarAccess getSyncAccess() {

        return this.syncAccess;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#getProgress()
     * 
     */
    public int getProgress() {

        return this.syncAccess.getSelection();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#setProgress(int)
     * 
     */
    public void setProgress(int newProgress) {

        this.syncAccess.setSelection(newProgress);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#isIndeterminate()
     * 
     */
    public boolean isIndeterminate() {

        return this.syncAccess.hasStyle(SWT.INDETERMINATE);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIProgressBarIF#getOrientation()
     * 
     */
    public Orientation getOrientation() {

        if (this.syncAccess.hasStyle(SWT.HORIZONTAL)) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }

}
