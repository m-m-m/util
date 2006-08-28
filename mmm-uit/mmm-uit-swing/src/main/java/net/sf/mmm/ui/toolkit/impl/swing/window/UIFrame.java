/* $Id: UIFrame.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBar;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.window.UIFrameIF} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIFrame extends UIWindow implements UIFrameIF {

    /** the swing frame */
    private final JFrame frame;

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
     * @param title
     *        is the {@link #getTitle() title} of the frame.
     * @param resizeable -
     *        if <code>true</code> the frame will be
     *        {@link #isResizeable() resizeable}.
     */
    public UIFrame(UIFactory uiFactory, UIFrame parent, String title, boolean resizeable) {

        super(uiFactory, parent);
        this.frame = new JFrame(uiFactory.getAwtGc());
        this.frame.setResizable(resizeable);
        if (title != null) {
            this.frame.setTitle(title);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.awt.UIWindow#getAwtWindow()
     * {@inheritDoc}
     */
    protected Window getAwtWindow() {

        return this.frame;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitleIF#getTitle()
     * {@inheritDoc}
     */
    public String getTitle() {

        return this.frame.getTitle();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTitleIF#setTitle(java.lang.String)
     * {@inheritDoc}
     */
    public void setTitle(String newTitle) {

        this.frame.setTitle(newTitle);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow#createMenuBar()
     * {@inheritDoc}
     */
    protected UIMenuBarIF createMenuBar() {

        JMenuBar menuBar = this.frame.getJMenuBar();
        if (menuBar == null) {
            menuBar = new JMenuBar();
            this.frame.setJMenuBar(menuBar);
        }
        return new UIMenuBar((UIFactory) getFactory(), this, menuBar);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximizedIF#isMaximized()
     * {@inheritDoc}
     */
    public boolean isMaximized() {

        return (this.frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMaximized(boolean)
     * {@inheritDoc}
     */
    public void setMaximized(boolean maximize) {

        int state = this.frame.getExtendedState();
        if (maximize) {
            state |= JFrame.MAXIMIZED_BOTH;
        } else {
            state &= ~JFrame.MAXIMIZED_BOTH;
        }
        this.frame.setExtendedState(state);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMinimized(boolean)
     * {@inheritDoc}
     */
    public void setMinimized(boolean minimize) {

    // TODO Auto-generated method stub

    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#createFrame(java.lang.String,
     *      boolean)
     * {@inheritDoc}
     */
    public UIFrameIF createFrame(String title, boolean resizeable) {

        return new UIFrame((UIFactory) getFactory(), this, title, resizeable);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#setComposite(net.sf.mmm.ui.toolkit.api.composite.UICompositeIF)
     * {@inheritDoc}
     */
    public void setComposite(UICompositeIF newComposite) {

        JComponent jComponent = ((UIComponent) newComposite).getSwingComponent();
        this.frame.setContentPane(jComponent);
        registerComposite(newComposite);
    }

}