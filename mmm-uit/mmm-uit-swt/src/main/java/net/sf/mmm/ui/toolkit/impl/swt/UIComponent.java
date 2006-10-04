/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIWindow;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIComponentIF} interface using SWT as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIComponent extends UISwtNode implements UIComponentIF {

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIComponent(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
    }

    /**
     * This method gets synchron access on the SWT control.
     * 
     * @return the synchron access.
     */
    public abstract AbstractSyncControlAccess getSyncAccess();

    /**
     * This method gets synchron access on the control that represents the
     * active part of this component. This method is used by methods such as
     * {@link #setEnabled(boolean)} and {@link #setTooltipText(String)}. It can
     * be overriden if the implemented component is build out of multiple SWT
     * controls and the top ancestor is not the active control (e.g. composite
     * may have an SWT group as top ancestor that represents a titled border).
     * 
     * @return the active unwrapped SWT control.
     */
    public AbstractSyncControlAccess getActiveSyncAccess() {

        return getSyncAccess();
    }

    /**
     * This method gets the parent SWT composite of the components parent node.
     * This method can be called from the constructor of a subclass to determine
     * the SWT parent of the widget to create.
     * 
     * @return the SWT composite of the parent node or the dummy parent of the
     *         factory if the parent is <code>null</code>.
     */
    /*
     * protected Composite getSwtParent() {
     * 
     * if (getParent() == null) { return getFactory().getDummyParent(); } else
     * if (getParent().isWindow()) { return ((UIWindow)
     * getParent()).getSwtWindow(); } else { return (Composite) ((UIComposite)
     * getParent()).getSwtControl(); } }
     */

    /**
     * This method gets the parent SWT shell of this component.
     * 
     * @return the parent shell this component is hooked to or the
     *         {@link UIFactory#getDummyParent() dummy-shell} if this component
     *         is not yet attached to a shell.
     */
    /*
    protected Shell getSwtParentShell() {

        if (getParent() == null) {
            return getFactory().getDummyParent();
        } else if (getParent().isWindow()) {
            return ((UIWindow) getParent()).getSwtWindow();
        } else {
            return ((UIComposite) getParent()).getSwtParentShell();
        }
    }
    */

    /**
     * This method gets the unwrapped control that represents the active part of
     * this component. This method is used by methods such as
     * {@link #setEnabled(boolean)} and {@link #setTooltipText(String)}. It can
     * be overriden if the implemented component is build out of multiple SWT
     * controls and the top ancestor is not the active control (e.g. composite
     * may have an SWT group as top ancestor that represents a titled border).
     * 
     * @return the active unwrapped SWT control.
     */
    // protected Control getActiveSwtControl() {
    //
    // return getSwtControl();
    // }
    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF#getTooltipText()
     * 
     */
    public String getTooltipText() {

        return getActiveSyncAccess().getTooltip();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTooltipIF#setTooltipText(java.lang.String)
     * 
     */
    public void setTooltipText(String tooltip) {

        getActiveSyncAccess().setTooltip(tooltip);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#setParent(net.sf.mmm.ui.toolkit.api.UINodeIF)
     * 
     */
    @Override
    public void setParent(UINodeIF newParent) {

        if (newParent == null) {
            System.out.println("UIComponent: This should be kicked out!");
            // getSwtControl().setParent(getFactory().getDummyParent());
        } else {
            AbstractSyncCompositeAccess parentAccess;
            boolean autoAttach = true;
            if (newParent instanceof UIComposite) {
                UIComposite parentComposite = (UIComposite) newParent;
                parentAccess = parentComposite.getActiveSyncAccess();
                autoAttach = parentComposite.isAttachToActiveAccess();
            } else {
                parentAccess = ((UIWindow) newParent).getSyncAccess();
            }
            if (autoAttach) {
                getSyncAccess().setParentAccess(parentAccess);                
            }
            if ((getSyncAccess().getSwtObject() == null) && (parentAccess.getSwtObject() != null)) {
                create();
            }            
        }
        super.setParent(newParent);
        update();
    }

    /**
     * This method creates the SWT control of this component.
     */
    public void create() {

        /*
        if (getSyncAccess().getSwtObject() == null) {
            getSyncAccess().create();
            if (getActiveSyncAccess().getSwtObject() == null) {
                getActiveSyncAccess().create();
            }
        }
        */
        getActiveSyncAccess().create();
    }

    /**
     *
     */
    public void update() {
        if (getParent() instanceof UIComponent) {
            UIComponent p = (UIComponent) getParent();
            p.update();
        }
        //nothing to do so far
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEnabledIF#setEnabled(boolean)
     * 
     */
    public void setEnabled(boolean enabled) {

        getActiveSyncAccess().setEnabled(enabled);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabledIF#isEnabled()
     * 
     */
    public boolean isEnabled() {

        return getActiveSyncAccess().isEnabled();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#isResizeable()
     * 
     */
    public boolean isResizeable() {

        // TODO
        return false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF#setSize(int, int)
     * 
     */
    public void setSize(int width, int height) {

        if (isResizeable()) {

            getSyncAccess().setSize(width, height);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getHeight()
     * 
     */
    public int getHeight() {

        return getSyncAccess().getBounds().height;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF#getWidth()
     * 
     */
    public int getWidth() {

        return getSyncAccess().getBounds().width;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredHeight()
     * 
     */
    public int getPreferredHeight() {

        return getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredWidth()
     * 
     */
    public int getPreferredWidth() {

        return getSyncAccess().computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#dispose()
     * 
     */
    public void dispose() {

        getSyncAccess().dispose();
        if (!getActiveSyncAccess().isDisposed()) {
            // should never happen but just to get sure...
            getActiveSyncAccess().dispose();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteDisposedIF#isDisposed()
     * 
     */
    public boolean isDisposed() {

        return getSyncAccess().isDisposed();
    }

}