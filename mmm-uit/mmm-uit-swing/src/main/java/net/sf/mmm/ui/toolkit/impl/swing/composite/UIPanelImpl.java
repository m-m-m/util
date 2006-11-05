/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanel} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPanelImpl extends UIMultiComposite implements UIPanel {

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
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param orientation
   *        is the orientation for the layout of the panel.
   */
  public UIPanelImpl(UIFactorySwing uiFactory, UINode parentObject, Orientation orientation) {

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
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.panel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#removeComponent(int)
   */
  public void removeComponent(int index) {

    // synchronized (this) {
    this.panel.remove(index);
    UIComponent c = this.components.remove(index);
    if ((c != null) && (c instanceof AbstractUIComponent)) {
      AbstractUIComponent component = (AbstractUIComponent) c;
      setParent(component, null);
      JComponent swingComponent = component.getSwingComponent();
      if (swingComponent instanceof JRadioButton) {
        getButtonGroup().remove((JRadioButton) swingComponent);
      }
    }
    // }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void removeComponent(UIComponent component) {

    int index = this.components.indexOf(component);
    if (index >= 0) {
      removeComponent(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void addComponent(UIComponent component) {

    addComponent(component, LayoutConstraints.DEFAULT);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints)
   */
  public void addComponent(UIComponent component, LayoutConstraints constraints) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    // synchronized (this) {
    JComponent swingComponent = c.getSwingComponent();
    this.panel.add(swingComponent, constraints);
    if (swingComponent instanceof JRadioButton) {
      getButtonGroup().add((JRadioButton) swingComponent);
    }
    this.components.add(c);
    setParent(c, this);
    // }
    // this.panel.updateUI();
    if (this.panel.isVisible()) {
      refresh();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints, int)
   */
  public void addComponent(UIComponent component, LayoutConstraints constraints, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
   */
  public UIComponent getComponent(int index) {

    return this.components.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#setOrientation(net.sf.mmm.ui.toolkit.api.composite.Orientation)
   */
  public void setOrientation(Orientation orientation) {

    this.layout.setOrientation(orientation);
    this.layout.layoutContainer(this.panel);
    this.panel.revalidate();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIPanel#getOrientation()
   */
  public Orientation getOrientation() {

    return this.layout.getOrientation();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#refresh()
   */
  @Override
  public void refresh() {

    super.refresh();
    this.layout.refreshCachedData();
    this.panel.updateUI();
  }

}
