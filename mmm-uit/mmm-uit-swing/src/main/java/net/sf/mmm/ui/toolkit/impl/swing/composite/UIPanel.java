/* $Id: UIPanel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.mmm.ui.toolkit.api.UIComponentIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanelIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanelIF} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPanel extends UIMultiComposite implements UIPanelIF {

    /** the swing panel */
    private final JPanel panel;

    /** the layout manager */
    private final LayoutManager layout;

    /** the button group used for radio-buttons */
    private ButtonGroup buttonGroup;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param orientation
     *        is the orientation for the layout of the panel.
     */
    public UIPanel(UIFactory uiFactory, UINodeIF parentObject, Orientation orientation) {

        super(uiFactory, parentObject);
        this.layout = new LayoutManager();
        this.layout.setOrientation(orientation);
        this.panel = new JPanel(this.layout);
        this.buttonGroup = null;
    }

    /**
     * This method gets the button group for this panel.
     * 
     * @return the button group.
     */
    protected ButtonGroup getButtonGroup() {

        if (this.buttonGroup == null) {
            this.buttonGroup = new ButtonGroup();
        }
        return this.buttonGroup;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    @Override
    public JComponent getSwingComponent() {

        return this.panel;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#removeComponent(int)
     * {@inheritDoc}
     */
    public void removeComponent(int index) {

        // synchronized (this) {
        this.panel.remove(index);
        UIComponentIF c = this.components.remove(index);
        if ((c != null) && (c instanceof UIComponent)) {
            UIComponent component = (UIComponent) c; 
            setParent(component, null);
            JComponent swingComponent = component.getSwingComponent();
            if (swingComponent instanceof JRadioButton) {
                getButtonGroup().remove((JRadioButton) swingComponent);
            }
        }
        // }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void removeComponent(UIComponentIF component) {

        int index = this.components.indexOf(component);
        if (index >= 0) {
            removeComponent(index);
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component) {

        addComponent(component, LayoutConstraints.DEFAULT);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, LayoutConstraints constraints) {

        UIComponent c = (UIComponent) component;
        // synchronized (this) {
        JComponent swingComponent = c.getSwingComponent();
        this.panel.add(swingComponent, constraints);
        if (swingComponent instanceof JRadioButton) {
            getButtonGroup().add((JRadioButton) swingComponent);
        }
        this.components.add(c);
        setParent(c, this);
        // }
        //this.panel.updateUI();
        if (this.panel.isVisible()) {
            refresh();            
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#addComponent(net.sf.mmm.ui.toolkit.api.UIComponentIF,
     *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints, int)
     * {@inheritDoc}
     */
    public void addComponent(UIComponentIF component, LayoutConstraints constraints, int position) {

        UIComponent c = (UIComponent) component;
        // synchronized (this) {
        JComponent swingComponent = c.getSwingComponent();
        this.panel.add(swingComponent, constraints, position);
        if (swingComponent instanceof JRadioButton) {
            getButtonGroup().add((JRadioButton) swingComponent);
        }
        this.components.add(position, c);
        setParent(c, this);
        // }
        this.panel.updateUI();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UICompositeIF#getComponent(int)
     * {@inheritDoc}
     */
    public UIComponentIF getComponent(int index) {

        return this.components.get(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#setOrientation(net.sf.mmm.ui.toolkit.api.composite.Orientation)
     * {@inheritDoc}
     */
    public void setOrientation(Orientation orientation) {

        this.layout.setOrientation(orientation);
        this.layout.layoutContainer(this.panel);
        this.panel.revalidate();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.composite.UIPanelIF#getOrientation()
     * {@inheritDoc}
     */
    public Orientation getOrientation() {

        return this.layout.getOrientation();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#refresh()
     * {@inheritDoc}
     */
    @Override
    public void refresh() {

        super.refresh();        
        this.layout.refreshCachedData();
        this.panel.updateUI();
    }

}