/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.window.UIDialogIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the UIDialogIF interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDialog extends UIWindow implements UIDialogIF {

    /** the default style for the native SWT shell */
    private static final int DEFAULT_STYLE = SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.TITLE;

    /** the modal flag */
    private boolean modalFlag;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param modal -
     *        if <code>true</code> all windows of the application are blocked
     *        until the dialog is closed.
     * @param resizeable -
     *        if <code>true</code> the window will be
     *        {@link #isResizeable() resizeable}.
     */
    public UIDialog(UIFactory uiFactory, UIWindow parentObject, boolean modal, boolean resizeable) {

        super(uiFactory, parentObject, DEFAULT_STYLE, modal, resizeable);
        this.modalFlag = modal;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIDialogIF#isModal()
     * {@inheritDoc}
     */
    public boolean isModal() {

        return this.modalFlag;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

}
