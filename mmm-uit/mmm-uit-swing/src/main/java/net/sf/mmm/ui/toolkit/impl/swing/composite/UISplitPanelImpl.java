/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
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

  /** @see #setDividerPosition(double) */
  private double dividerLocation;
  
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
    this.dividerLocation = 0.5;
  }

  /**
   * {@inheritDoc}
   */
  public JComponent getSwingComponent() {

    return this.splitPanel;
  }

  /**
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
   * {@inheritDoc}
   */
  @Override
  public Orientation getOrientation() {

    if (this.splitPanel.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setTopOrLeftComponent(UIComponent component) {

    AbstractUIComponent newComponent = (AbstractUIComponent) component;
    if (newComponent.getParent() != null) {
      newComponent.removeFromParent();
    }
    if (this.componentTopOrLeft != null) {
      setParent(this.componentTopOrLeft, null);
    }
    this.componentTopOrLeft = newComponent;
    JComponent jComponent = this.componentTopOrLeft.getSwingComponent();
    this.splitPanel.setTopComponent(jComponent);
    setParent(this.componentTopOrLeft, this);
  }

  /**
   * {@inheritDoc}
   */
  public void setBottomOrRightComponent(UIComponent component) {

    AbstractUIComponent newComponent = (AbstractUIComponent) component;
    if (newComponent.getParent() != null) {
      newComponent.removeFromParent();
    }
    if (this.componentBottomOrRight != null) {
      setParent(this.componentBottomOrRight, null);
    }
    this.componentBottomOrRight = newComponent;
    JComponent jComponent = this.componentBottomOrRight.getSwingComponent();
    this.splitPanel.setBottomComponent(jComponent);
    setParent(this.componentBottomOrRight, this);
  }

  /**
   * {@inheritDoc}
   */
  public void setDividerPosition(double proportion) {

    this.dividerLocation = proportion;
    this.splitPanel.setDividerLocation(proportion);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public UIComponent getTopOrLeftComponent() {

    return this.componentTopOrLeft;
  }

  /**
   * {@inheritDoc}
   */
  public UIComponent getBottomOrRightComponent() {

    return this.componentBottomOrRight;
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public int getComponentCount() {

    return 2;
  }
  
}
