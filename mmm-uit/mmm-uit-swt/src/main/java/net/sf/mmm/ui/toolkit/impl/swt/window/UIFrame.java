/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.window;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the UIFrameIF interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFrame extends UIWindow implements UIFrameIF {

    /** the default style for the native SWT shell */
    private static final int DEFAULT_STYLE = SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param parent
     *        is the {@link UINodeIF#getParent() parent} of this object (may be
     *        <code>null</code>).
     * @param resizeable -
     *        if <code>true</code> the frame will be
     *        {@link #isResizeable() resizeable}.
     */
    public UIFrame(UIFactory uiFactory, UIFrame parent, boolean resizeable) {

        super(uiFactory, parent, DEFAULT_STYLE, false, resizeable);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximizedIF#isMaximized()
     * {@inheritDoc}
     */
    public boolean isMaximized() {
    
        return getSwtWindow().getMaximized();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMaximized(boolean)
     * {@inheritDoc}
     */
    public void setMaximized(boolean maximize) {

        getSwtWindow().setMaximized(maximize);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMinimized(boolean)
     * {@inheritDoc}
     */
    public void setMinimized(boolean minimize) {

        getSwtWindow().setMinimized(minimize);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#createFrame(java.lang.String,
     *      boolean)
     * {@inheritDoc}
     */
    public UIFrame createFrame(String title, boolean resizeable) {

        UIFrame frame = new UIFrame(getFactory(), this, resizeable);
        frame.setTitle(title);
        return frame;
    }

}
