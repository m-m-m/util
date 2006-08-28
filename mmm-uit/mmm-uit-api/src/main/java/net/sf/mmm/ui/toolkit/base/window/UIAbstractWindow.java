/* $Id: UIAbstractWindow.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.window;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF;
import net.sf.mmm.ui.toolkit.api.window.MessageType;
import net.sf.mmm.ui.toolkit.api.window.UIWindowIF;
import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

/**
 * This is the base implementation of the UIWindowIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractWindow extends UIAbstractNode implements UIWindowIF {

    /** the composite content of this window */
    private UICompositeIF composite;

    /** the menu bar of this window */
    private UIMenuBarIF menuBar;

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
    public UIAbstractWindow(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.menuBar = null;
    }

    /**
     * This method gets access to read the size of the desktop.<br>
     * It is used by {@link #centerWindow()} and may be overriden in specific
     * scenarios.
     * 
     * @return read-access to the size of the desktop.
     */
    protected UIReadSizeIF getDesktopSize() {

        return getFactory().getDisplay();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#centerWindow()
     * {@inheritDoc}
     */
    public void centerWindow() {

        UIReadSizeIF desktop = getDesktopSize();
        int xDiff = desktop.getWidth() - getWidth();
        int yDiff = desktop.getHeight() - getHeight();
        if (xDiff < 0) {
            xDiff = 0;
        }
        if (yDiff < 0) {
            yDiff = 0;
        }
        setPosition(xDiff / 2, yDiff / 2);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#isWindow()
     * {@inheritDoc}
     */
    public boolean isWindow() {

        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#getComposite()
     * {@inheritDoc}
     */
    public UICompositeIF getComposite() {

        return this.composite;
    }

    /**
     * This method registeres the new composite and changes the parent of the
     * old and the new composite. The method should be called from the
     * setComposite method implementation.
     * 
     * @see UIWindowIF#setComposite(UICompositeIF)
     * 
     * @param newComposite
     *        is the composite to register.
     */
    public void registerComposite(UICompositeIF newComposite) {

        if (this.composite != null) {
            // The current composite is replaced by a new one. Set the parent of
            // the old (current) one to null.
            setParent((UIAbstractNode) this.composite, null);
        }
        this.composite = newComposite;
        ((UIAbstractNode) newComposite).setParent(this);
        //setParent((UIAbstractNode) newComposite, this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#getMenuBar()
     * {@inheritDoc}
     */
    public synchronized UIMenuBarIF getMenuBar() {

        if (this.menuBar == null) {
            synchronized (this) {
                if (this.menuBar == null) {
                    this.menuBar = createMenuBar();
                }
            }
        }
        return this.menuBar;
    }

    /**
     * This method is called from the getMenuBar() method if the menu bar is
     * <code>null</code>. The method must create an empty menu bar.
     * 
     * @see UIWindowIF#getMenuBar()
     * 
     * @return the created menu bar.
     */
    protected abstract UIMenuBarIF createMenuBar();

    /**
     * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF#showMessage(java.lang.String,
     *      java.lang.String, net.sf.mmm.ui.toolkit.api.window.MessageType,
     *      java.lang.Throwable)
     */
    public void showMessage(String message, String title, MessageType messageType,
            Throwable throwable) {

        // TODO Hack for the moment...
        throwable.printStackTrace();
        showMessage(message + "\n" + throwable.getMessage(), title, messageType);
    }

}