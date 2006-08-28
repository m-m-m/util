/* $Id: UIInternalFrame.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.window;

import java.awt.Window;
import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF;
import net.sf.mmm.ui.toolkit.api.window.UIFrameIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.menu.UIMenuBar;

/**
 * This class is the implementation of an internal
 * {@link net.sf.mmm.ui.toolkit.api.window.UIFrameIF frame} using Swing as the
 * UI toolkit.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIWorkbenchIF#createFrame(String,
 *      boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIInternalFrame extends UIWindow implements UIFrameIF, UIComponentIF {

    /** the frame */
    private final JInternalFrame frame;

    /** the workbench */
    private final UIWorkbench workbench;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param parentObject
     *        is the workbench that created this frame.
     * @param title
     *        is the {@link #getTitle() title} of the frame.
     * @param resizeable -
     *        if <code>true</code> the frame will be
     *        {@link #isResizeable() resizeable}.
     */
    public UIInternalFrame(UIFactory uiFactory, UIWorkbench parentObject, String title,
            boolean resizeable) {

        super(uiFactory, parentObject);
        this.frame = new JInternalFrame(title, resizeable, true, resizeable, true);
        this.workbench = parentObject;
    }

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UIObjectIF#getFactory() factory}
     *        instance.
     * @param parentObject
     *        is the
     *        {@link net.sf.mmm.ui.toolkit.api.UINodeIF#getParent() parent} that
     *        created this frame.
     * @param title
     *        is the {@link #getTitle() title} of the frame.
     * @param resizeable -
     *        if <code>true</code> the frame will be
     *        {@link #isResizeable() resizeable}.
     */
    public UIInternalFrame(UIFactory uiFactory, UIInternalFrame parentObject, String title,
            boolean resizeable) {

        super(uiFactory, parentObject);
        this.frame = new JInternalFrame();
        this.workbench = parentObject.workbench;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow#createMenuBar()
     * {@inheritDoc}
     */
    @Override
    protected UIMenuBarIF createMenuBar() {

        JMenuBar menuBar = this.frame.getJMenuBar();
        if (menuBar == null) {
            menuBar = new JMenuBar();
            this.frame.setJMenuBar(menuBar);
        }
        return new UIMenuBar((UIFactory) getFactory(), this, menuBar);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#createFrame(java.lang.String,
     *      boolean)
     * {@inheritDoc}
     */
    public UIFrameIF createFrame(String title, boolean resizeable) {

        UIInternalFrame internalFrame = new UIInternalFrame((UIFactory) getFactory(), this, title,
                resizeable);
        return internalFrame;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadMaximizedIF#isMaximized()
     * {@inheritDoc}
     */
    public boolean isMaximized() {

        return this.frame.isMaximum();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMaximized(boolean)
     * {@inheritDoc}
     */
    public void setMaximized(boolean maximize) {

        if (this.frame.isMaximizable()) {
            try {
                this.frame.setMaximum(maximize);
            } catch (PropertyVetoException e) {
                // ignore this...
            }            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIFrameIF#setMinimized(boolean)
     * {@inheritDoc}
     */
    public void setMinimized(boolean minimize) {

        try {
            this.frame.setIcon(minimize);
        } catch (PropertyVetoException e) {
            // ignore this...
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * {@inheritDoc}
     */
    public boolean isResizeable() {

        return this.frame.isResizable();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * {@inheritDoc}
     */
    public void setSize(int width, int height) {

        this.frame.setSize(width, height);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWritePositionIF#setPosition(int,
     *      int)
     * {@inheritDoc}
     */
    public void setPosition(int x, int y) {

        this.frame.setLocation(x, y);
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
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        return this.frame.getHeight();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        return this.frame.getWidth();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#dispose()
     * {@inheritDoc}
     */
    public void dispose() {

        this.frame.dispose();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#isDisposed()
     * {@inheritDoc}
     */
    public boolean isDisposed() {

        return this.frame.isDisplayable();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPositionIF#getX()
     * {@inheritDoc}
     */
    public int getX() {

        return this.frame.getX();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPositionIF#getY()
     * {@inheritDoc}
     */
    public int getY() {

        return this.frame.getY();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#pack()
     * {@inheritDoc}
     */
    public void pack() {

        this.frame.pack();
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

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisibleIF#isVisible()
     * {@inheritDoc}
     */
    public boolean isVisible() {

        return this.frame.isVisible();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisibleIF#setVisible(boolean)
     * {@inheritDoc}
     */
    public void setVisible(boolean visible) {

        this.frame.setVisible(visible);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.awt.UIWindow#getAwtWindow()
     * {@inheritDoc}
     */
    @Override
    protected Window getAwtWindow() {

        return ((UIWorkbench) getParent()).getAwtWindow();
    }

    /**
     * This method gets the native swing object.
     * 
     * @return the swing internal frame.
     */
    public JInternalFrame getSwingInternalFrame() {

        return this.frame;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow#getDesktopSize()
     * {@inheritDoc}
     */
    @Override
    protected UIReadSizeIF getDesktopSize() {

        return this.workbench.getDesktopPanel();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabledIF#isEnabled()
     * {@inheritDoc}
     */
    public boolean isEnabled() {

        return this.frame.isEnabled();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEnabledIF#setEnabled(boolean)
     * {@inheritDoc}
     */
    public void setEnabled(boolean enabled) {

        this.frame.setEnabled(enabled);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF#getTooltipText()
     * {@inheritDoc}
     */
    public String getTooltipText() {

        return this.frame.getToolTipText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF#setTooltipText(java.lang.String)
     * {@inheritDoc}
     */
    public void setTooltipText(String tooltip) {

        this.frame.setToolTipText(tooltip);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredHeight()
     * {@inheritDoc}
     */
    public int getPreferredHeight() {

        return (int) this.frame.getPreferredSize().getHeight();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredWidth()
     * {@inheritDoc}
     */
    public int getPreferredWidth() {

        return (int) this.frame.getPreferredSize().getWidth();
    }

}
