/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncGroupAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncSashFormAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISplitPanel extends UIComposite implements UISplitPanelIF {
    
    /** the synchron access to the sash-form */
    private final SyncSashFormAccess syncAccess;

    /** the synchron access to the top or left composite */
    private final SyncGroupAccess syncTopLeft;

    /** the synchron access to the bottom or right composite */
    private final SyncGroupAccess syncBottomRight;

    /** the componenet top or left */
    private UIComponent componentTopOrLeft;

    /** the componenet bottom or right */
    private UIComponent componentBottomOrRight;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param borderTitle
     *        is the title of the border or <code>null</code> for NO border.
     * @param orientation
     *        is the orientation of the two child-components in this split-pane.
     */
    public UISplitPanel(UIFactory uiFactory, UIComponent parentObject, String borderTitle, Orientation orientation) {

        super(uiFactory, parentObject, borderTitle);
        int style;
        if (orientation == Orientation.HORIZONTAL) {
            style = SWT.HORIZONTAL;
        } else {
            style = SWT.VERTICAL;
        }
        style |= SWT.BORDER;
        // style |= SWT.LEFT_TO_RIGHT;
        this.syncAccess = new SyncSashFormAccess(uiFactory, style);
        this.syncTopLeft = new SyncGroupAccess(uiFactory, SWT.NONE);
        this.syncTopLeft.setLayout(new FillLayout());
        this.syncTopLeft.setParentAccess(this.syncAccess);
        this.syncBottomRight = new SyncGroupAccess(uiFactory, SWT.NONE);
        this.syncBottomRight.setLayout(new FillLayout());
        this.syncBottomRight.setParentAccess(this.syncAccess);
        /*
        final int sashStyle = style;
        getFactory().invokeSynchron(new Runnable() {

            public void run() {

                UISplitPanel.this.splitPanel = new SashForm(parent, sashStyle);
                UISplitPanel.this.compositeTopLeft = new Composite(UISplitPanel.this.splitPanel,
                        SWT.NONE);
                UISplitPanel.this.compositeTopLeft.setLayout(new FillLayout());
                UISplitPanel.this.compositeBottomRight = new Composite(
                        UISplitPanel.this.splitPanel, SWT.NONE);
                UISplitPanel.this.compositeBottomRight.setLayout(new FillLayout());
            }
        });
        */
        this.componentTopOrLeft = null;
        this.componentBottomOrRight = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite#createChildren()
     * 
     */
    @Override
    protected void createChildren() {
    
        this.syncTopLeft.create();
        this.syncBottomRight.create();
        super.createChildren();
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getActiveSyncAccess()
     * 
     */
    @Override
    public SyncSashFormAccess getActiveSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setOrientation(Orientation)
     * 
     */
    public void setOrientation(Orientation orientation) {

        if (orientation == Orientation.HORIZONTAL) {
            this.syncAccess.setOrientation(SWT.HORIZONTAL);
        } else {
            this.syncAccess.setOrientation(SWT.VERTICAL);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getOrientation()
     * 
     */
    public Orientation getOrientation() {

        if (this.syncAccess.hasStyle(SWT.HORIZONTAL)) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setTopOrLeftComponent(UIComponentIF)
     * 
     */
    public void setTopOrLeftComponent(UIComponentIF component) {

        if (this.componentTopOrLeft != null) {
            //TODO
        }
        this.componentTopOrLeft = (UIComponent) component;
        this.componentTopOrLeft.getSyncAccess().setParentAccess(this.syncTopLeft);
        this.componentTopOrLeft.setParent(this);
        
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setBottomOrRightComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * 
     */
    public void setBottomOrRightComponent(UIComponentIF component) {

        if (this.componentBottomOrRight != null) {
            //TODO
        }
        this.componentBottomOrRight = (UIComponent) component;
        this.componentBottomOrRight.getSyncAccess().setParentAccess(this.syncBottomRight);
        this.componentBottomOrRight.setParent(this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setDividerPosition(double)
     * 
     */
    public void setDividerPosition(double proportion) {

        final int percent = (int) (proportion * 100);
        this.syncAccess.setWeights(percent);            
        /*
        if ((this.componentBottomOrRight != null) && (this.componentTopOrLeft != null)) {
        }
        getFactory().invokeSynchron(new Runnable() {

            public void run() {
                UISplitPanel.this.splitPanel.setWeights(new int[] {percent, 100 - percent});
            }
        });
        */
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getTopOrLeftComponent()
     * 
     */
    public UIComponent getTopOrLeftComponent() {

        return this.componentTopOrLeft;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getBottomOrRightComponent()
     * 
     */
    public UIComponent getBottomOrRightComponent() {

        return this.componentBottomOrRight;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIComponentIF#setEnabled(boolean)
     * 
     */
    public void setEnabled(boolean enabled) {

        this.syncAccess.setEnabled(enabled);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadEnabledIF#isEnabled()
     * 
     */
    public boolean isEnabled() {

        return this.syncAccess.isEnabled();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * 
     */
    public UIComponent getComponent(int index) {

        if (index == 0) {
            return getTopOrLeftComponent();
        } else if (index == 1) {
            return getBottomOrRightComponent();
        } else {
            throw new IndexOutOfBoundsException("Illegal index (" + index + ") must be 0 or 1!");
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponentCount()
     * 
     */
    public int getComponentCount() {

        return 2;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.composite.UIComposite#isAttachToActiveAccess()
     * 
     */
    @Override
    public boolean isAttachToActiveAccess() {
    
        return false;
    }
    
}