/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.composite.UICompositeIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncGroupAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UICompositeIF} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIComposite extends UIComponent implements UICompositeIF {

    /** gives access to the {@link org.eclipse.swt.widgets.Group} */
    private final SyncGroupAccess syncGroupAccess;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param borderTitle
     *        is the title of the border or <code>null</code> for NO border.
     */
    public UIComposite(UIFactory uiFactory, UISwtNode parentObject, String borderTitle) {

        super(uiFactory, parentObject);
        if (borderTitle == null) {
            this.syncGroupAccess = null;
        } else {
            this.syncGroupAccess = new SyncGroupAccess(uiFactory, SWT.NONE);
            this.syncGroupAccess.setLayout(new FillLayout());
            this.syncGroupAccess.setText(borderTitle);            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteBorderTitleIF#getBorderTitle()
     * {@inheritDoc}
     */
    public String getBorderTitle() {

        if (this.syncGroupAccess == null) {
            return null;
        } else {
            return this.syncGroupAccess.getText();            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public AbstractSyncCompositeAccess getSyncAccess() {

        if (this.syncGroupAccess == null) {
            return getActiveSyncAccess();
        } else {
            return this.syncGroupAccess;
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getActiveSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public abstract AbstractSyncCompositeAccess getActiveSyncAccess();

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * {@inheritDoc}
     */
    public abstract UIComponent getComponent(int index);

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#create()
     * {@inheritDoc}
     */
    @Override
    public void create() {

        if (this.syncGroupAccess != null) {
            getActiveSyncAccess().setParentAccess(this.syncGroupAccess);
        }
        super.create();
        createChildren();
    }
    
    /**
     * This method is called to create the children of this composite.
     */
    protected void createChildren() {
        
        int count = getComponentCount();
        for (int i = 0; i < count; i++) {
            UIComponent child = getComponent(i);
            if (child != null) {
                child.create();
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#dispose()
     * {@inheritDoc}
     */
    @Override
    public void dispose() {

        // TODO
        super.dispose();
    }

    /**
     * This method determines if an SWT child will automatically be attached to
     * this the {@link #getActiveSyncAccess() composite}. Override this method
     * and return <code>false</code> if you need special behaviour to build
     * the SWT tree.
     * 
     * @return <code>true</code> if children should be attached automatically,
     *         <code>false</code> if your implementation needs special
     *         behaviour.
     */
    public boolean isAttachToActiveAccess() {

        return true;
    }

}