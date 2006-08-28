/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.TabItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTabItemAccess extends AbstractSyncItemAccess {

    /**
     * operation to set the
     * {@link org.eclipse.swt.widgets.TabItem#setControl(org.eclipse.swt.widgets.Control) control}
     * of the item.
     */
    private static final String OPERATION_SET_CONTROL = "setControl";

    /** the access to the parent tab-folder */
    private SyncTabFolderAccess parentAccess;

    /** the tab-item to access */
    private TabItem tabItem;

    /** the tab-index of the item */
    private int tabIndex;

    /** the control of this tab */
    private Control control;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the item.
     */
    public SyncTabItemAccess(UIFactory uiFactory, int swtStyle) {

        this(uiFactory, swtStyle, -1);
    }

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is used to do the synchonization.
     * @param swtStyle
     *        is the {@link Widget#getStyle() style} of the item.
     * @param index
     *        is the index position of the item to add.
     */
    public SyncTabItemAccess(UIFactory uiFactory, int swtStyle, int index) {

        super(uiFactory, swtStyle);
        this.tabItem = null;
        this.tabIndex = index;
        this.parentAccess = null;
        this.control = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#performSynchron(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void performSynchron(String operation) {

        if (operation == OPERATION_SET_CONTROL) {
            this.tabItem.setControl(this.control);
        } else {
            super.performSynchron(operation);
        }
    }

    /**
     * This method gets the parent tab-folder of this tab-item.
     * 
     * @return the parnet.
     */
    public TabFolder getParent() {

        if (this.parentAccess == null) {
            return null;
        }
        return this.parentAccess.getSwtObject();
    }

    /**
     * This method sets the parent sync-access of this tab-item. If the parent
     * {@link SyncTabFolderAccess#getSwtObject() exists}, it will be set as
     * parent of this control. Else if the control does NOT yet exist, the
     * parent will be set on {@link #create() creation}.
     * 
     * @param newParentAccess
     *        is the synchron access to the new parent
     */
    public void setParentAccess(SyncTabFolderAccess newParentAccess) {

        this.parentAccess = newParentAccess;
        if (this.parentAccess.getSwtObject() == null) {
            this.tabIndex = -1;
        } else {
            if (this.tabItem == null) {
                create();
            } else {
                //this is currently not supported
                throw new IllegalArgumentException("can not move tab-item!");
            }
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#createSynchron()
     * {@inheritDoc}
     */
    @Override
    protected void createSynchron() {

        if (this.tabIndex == -1) {
            this.tabItem = new TabItem(getParent(), getStyle());
        } else {
            this.tabItem = new TabItem(getParent(), getStyle(), this.tabIndex);
        }
        if (this.control != null) {
            this.tabItem.setControl(this.control);
        }
        super.createSynchron();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#create()
     * {@inheritDoc}
     */
    @Override
    public void create() {
    
        //change visiblilty
        super.create();
    }
    
    /**
     * This method sets the
     * {@link org.eclipse.swt.widgets.TabItem#setControl(org.eclipse.swt.widgets.Control) control}
     * of the tab-item.
     * 
     * @param swtControl
     *        is the tabs control.
     */
    public void setControl(Control swtControl) {

        this.control = swtControl;
        assert (checkReady());
        invoke(OPERATION_SET_CONTROL);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncItemAccess#getSwtObject()
     * {@inheritDoc}
     */
    @Override
    public TabItem getSwtObject() {

        return this.tabItem;
    }

}
