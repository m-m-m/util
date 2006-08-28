/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISplitPanel extends UIComposite implements UISplitPanelIF {

    /** the swing split pane */
    private JSplitPane splitPanel;

    /** the component top or left */
    private UIComponent componentTopOrLeft;

    /** the component bottom or right */
    private UIComponent componentBottomOrRight;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param orientation
     *        is the orientation of the two child-components in this
     *        split-panel.
     */
    public UISplitPanel(UIFactory uiFactory, UIComponent parentObject, Orientation orientation) {

        super(uiFactory, parentObject);
        this.splitPanel = new JSplitPane();
        this.componentTopOrLeft = null;
        this.componentBottomOrRight = null;
        setOrientation(orientation);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public JComponent getSwingComponent() {

        return this.splitPanel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setOrientation(Orientation)
     * {@inheritDoc}
     */
    public void setOrientation(Orientation orientation) {

        if (orientation == Orientation.HORIZONTAL) {
            this.splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        } else {
            this.splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getOrientation()
     * {@inheritDoc}
     */
    public Orientation getOrientation() {

        if (this.splitPanel.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
            return Orientation.HORIZONTAL;
        } else {
            return Orientation.VERTICAL;            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setTopOrLeftComponent(UIComponentIF)
     * {@inheritDoc}
     */
    public void setTopOrLeftComponent(UIComponentIF component) {

        if (component.getParent() != null) {
            throw new IllegalArgumentException("Currently unsupported!");
        }
        if (this.componentTopOrLeft != null) {
            setParent(this.componentTopOrLeft, null);
        }
        this.componentTopOrLeft = (UIComponent) component;
        JComponent jComponent = this.componentTopOrLeft.getSwingComponent();
        this.splitPanel.setTopComponent(jComponent);
        setParent(this.componentTopOrLeft, this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setBottomOrRightComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void setBottomOrRightComponent(UIComponentIF component) {

        if (component.getParent() != null) {
            // TODO: add UIComponent.removeFromParent()
            //UIComponent c = (UIComponent) component;
            //c.removeFromParent;
            throw new IllegalArgumentException("Currently unsupported!");
        }
        if (this.componentBottomOrRight != null) {
            setParent(this.componentBottomOrRight, null);
        }
        this.componentBottomOrRight = (UIComponent) component;
        JComponent jComponent = this.componentBottomOrRight.getSwingComponent();
        this.splitPanel.setBottomComponent(jComponent);
        setParent(this.componentBottomOrRight, this);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#setDividerPosition(double)
     * {@inheritDoc}
     */
    public void setDividerPosition(double proportion) {

        this.splitPanel.setDividerLocation(proportion);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getTopOrLeftComponent()
     * {@inheritDoc}
     */
    public UIComponentIF getTopOrLeftComponent() {

        return this.componentTopOrLeft;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanelIF#getBottomOrRightComponent()
     * {@inheritDoc}
     */
    public UIComponentIF getBottomOrRightComponent() {

        return this.componentBottomOrRight;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * {@inheritDoc}
     */
    public UIComponentIF getComponent(int index) {

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
     * {@inheritDoc}
     */
    public int getComponentCount() {

        return 2;
    }

}