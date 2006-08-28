/* $Id: UIWindow.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.awt;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.window.UIDialogIF;
import net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow;

/**
 * This class is the implementation of the UIWindowIF interface using AWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIWindow extends UIAbstractWindow {

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
     *        created this object. It may be <code>null</code>.
     */
    public UIWindow(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * This method gets the unwrapped window.
     * 
     * @return the unwrapped window object.
     */
    protected abstract Window getAwtWindow();

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    protected boolean doInitializeListener() {

        WindowListener listener = new WindowListener() {

            /**
             * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowActivated(WindowEvent e) {

                invoke(ActionType.ACTIVATE);
            }

            /**
             * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowClosed(WindowEvent e) {

                invoke(ActionType.HIDE);
            }

            /**
             * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowClosing(WindowEvent e) {

            // currently ignored...
            }

            /**
             * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowDeactivated(WindowEvent e) {

                invoke(ActionType.DEACTIVATE);
            }

            /**
             * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowDeiconified(WindowEvent e) {

                invoke(ActionType.DEICONIFY);
            }

            /**
             * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowIconified(WindowEvent e) {

                invoke(ActionType.DEACTIVATE);
            }

            /**
             * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
             * {@inheritDoc}
     */
            public void windowOpened(WindowEvent e) {

                invoke(ActionType.SHOW);
            }

        };
        getAwtWindow().addWindowListener(listener);
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisibleIF#isVisible()
     * {@inheritDoc}
     */
    public boolean isVisible() {

        return getAwtWindow().isVisible();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteVisibleIF#setVisible(boolean)
     * {@inheritDoc}
     */
    public void setVisible(boolean visible) {

        getAwtWindow().setVisible(visible);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#pack()
     * {@inheritDoc}
     */
    public void pack() {

        getAwtWindow().pack();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWritePositionIF#setPosition(int,
     *      int)
     * {@inheritDoc}
     */
    public void setPosition(int x, int y) {

        getAwtWindow().setLocation(x, y);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * {@inheritDoc}
     */
    public void setSize(int width, int height) {

        getAwtWindow().setSize(width, height);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPositionIF#getX()
     * {@inheritDoc}
     */
    public int getX() {

        return getAwtWindow().getX();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPositionIF#getY()
     * {@inheritDoc}
     */
    public int getY() {

        return getAwtWindow().getY();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * {@inheritDoc}
     */
    public int getWidth() {

        return getAwtWindow().getWidth();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * {@inheritDoc}
     */
    public int getHeight() {

        return getAwtWindow().getHeight();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#dispose()
     * {@inheritDoc}
     */
    public void dispose() {

        getAwtWindow().dispose();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#isDisposed()
     * {@inheritDoc}
     */
    public boolean isDisposed() {

        //TODO this is not corret in all situations, is it?
        return getAwtWindow().isDisplayable();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * {@inheritDoc}
     */
    public boolean isResizeable() {

        if (getType() == UIDialogIF.TYPE) {
            return ((Dialog) getAwtWindow()).isResizable();
        } else {
            return ((Frame) getAwtWindow()).isResizable();
        }
    }
    
}
