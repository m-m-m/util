/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.composite;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UISplitPanel;
import net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UISplitPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISplitPanelImpl extends AbstractUIComposite implements UISplitPanel {

  /** the swing split pane */
  private JSplitPane splitPanel;

  /** the component top or left */
  private AbstractUIComponent componentTopOrLeft;

  /** the component bottom or right */
  private AbstractUIComponent componentBottomOrRight;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param orientation
   *        is the orientation of the two child-components in this split-panel.
   */
  public UISplitPanelImpl(UIFactorySwing uiFactory, AbstractUIComponent parentObject, Orientation orientation) {

    super(uiFactory, parentObject);
    this.splitPanel = new JSplitPane();
    this.componentTopOrLeft = null;
    this.componentBottomOrRight = null;
    setOrientation(orientation);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public JComponent getSwingComponent() {

    return this.splitPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#setOrientation(Orientation)
   */
  public void setOrientation(Orientation orientation) {

    if (orientation == Orientation.HORIZONTAL) {
      this.splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    } else {
      this.splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#getOrientation()
   */
  public Orientation getOrientation() {

    if (this.splitPanel.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#setTopOrLeftComponent(UIComponent)
   */
  public void setTopOrLeftComponent(UIComponent component) {

    if (component.getParent() != null) {
      throw new IllegalArgumentException("Currently unsupported!");
    }
    if (this.componentTopOrLeft != null) {
      setParent(this.componentTopOrLeft, null);
    }
    this.componentTopOrLeft = (AbstractUIComponent) component;
    JComponent jComponent = this.componentTopOrLeft.getSwingComponent();
    this.splitPanel.setTopComponent(jComponent);
    setParent(this.componentTopOrLeft, this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#setBottomOrRightComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void setBottomOrRightComponent(UIComponent component) {

    if (component.getParent() != null) {
      // TODO: add AbstractUIComponent.removeFromParent()
      // AbstractUIComponent c = (AbstractUIComponent) component;
      // c.removeFromParent;
      throw new IllegalArgumentException("Currently unsupported!");
    }
    if (this.componentBottomOrRight != null) {
      setParent(this.componentBottomOrRight, null);
    }
    this.componentBottomOrRight = (AbstractUIComponent) component;
    JComponent jComponent = this.componentBottomOrRight.getSwingComponent();
    this.splitPanel.setBottomComponent(jComponent);
    setParent(this.componentBottomOrRight, this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#setDividerPosition(double)
   */
  public void setDividerPosition(double proportion) {

    this.splitPanel.setDividerLocation(proportion);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#getTopOrLeftComponent()
   */
  public UIComponent getTopOrLeftComponent() {

    return this.componentTopOrLeft;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UISplitPanel#getBottomOrRightComponent()
   */
  public UIComponent getBottomOrRightComponent() {

    return this.componentBottomOrRight;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponent(int)
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
   * @see net.sf.mmm.ui.toolkit.api.composite.UIComposite#getComponentCount()
   */
  public int getComponentCount() {

    return 2;
  }

}
