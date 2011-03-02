/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.AbstractUiElement;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) children}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UISplitPanelImpl<E extends AbstractUiElement> extends AbstractUiComposite<E> implements
    UiSplitPanel<E> {

  /** the swing split pane */
  private JSplitPane splitPanel;

  /** the component top or left */
  private E componentTopOrLeft;

  /** the component bottom or right */
  private E componentBottomOrRight;

  /** @see #getOrientation() */
  private Orientation orientation;

  /** @see #setDividerPosition(double) */
  private double dividerLocation;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   * @param orientation is the orientation of the two child-components in this
   *        split-panel.
   */
  public UISplitPanelImpl(UIFactorySwing uiFactory, AbstractUiElement parentObject,
      Orientation orientation) {

    super(uiFactory);
    this.splitPanel = new JSplitPane();
    this.componentTopOrLeft = null;
    this.componentBottomOrRight = null;
    setOrientation(orientation);
    this.dividerLocation = 0.5;
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.splitPanel;
  }

  /**
   * {@inheritDoc}
   */
  public void setOrientation(Orientation orientation) {

    boolean horizontal = (orientation == Orientation.HORIZONTAL);
    if (getFactory().isFlipHorizontal()) {
      horizontal = !horizontal;
    }
    if (horizontal) {
      this.splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    } else {
      this.splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }
    this.orientation = orientation;
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return this.orientation;
  }

  /**
   * {@inheritDoc}
   */
  public void setTopOrLeftComponent(E component) {

    if (component.getParent() != null) {
      component.removeFromParent();
    }
    if (this.componentTopOrLeft != null) {
      setParent(this.componentTopOrLeft, null);
    }
    this.componentTopOrLeft = component;
    JComponent jComponent = this.componentTopOrLeft.getSwingComponent();
    this.splitPanel.setTopComponent(jComponent);
    setParent(this.componentTopOrLeft, this);
  }

  /**
   * {@inheritDoc}
   */
  public void setBottomOrRightComponent(E component) {

    if (component.getParent() != null) {
      component.removeFromParent();
    }
    if (this.componentBottomOrRight != null) {
      setParent(this.componentBottomOrRight, null);
    }
    this.componentBottomOrRight = component;
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
  public E getTopOrLeftComponent() {

    return this.componentTopOrLeft;
  }

  /**
   * {@inheritDoc}
   */
  public E getBottomOrRightComponent() {

    return this.componentBottomOrRight;
  }

  /**
   * {@inheritDoc}
   */
  public E getChild(int index) {

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
  public int getChildCount() {

    return 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    if (event.isOrientationModified()) {
      setOrientation(this.orientation);
    }
  }

}
