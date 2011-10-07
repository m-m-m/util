/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.base.view.composite.AbstractUiComposite;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiElementAdapterSwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public class UiSplitPanelImpl<CHILD extends AbstractUiElement<? extends JComponent>> extends
    AbstractUiComposite<JSplitPane, CHILD> implements UiSplitPanel<CHILD> {

  /** @see #getAdapter() */
  private final UiElementAdapterSwing<JSplitPane> adapter;

  /** the component top or left */
  private CHILD componentTopOrLeft;

  /** the component bottom or right */
  private CHILD componentBottomOrRight;

  /** @see #getOrientation() */
  private Orientation orientation;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param orientation is the {@link #getOrientation() orientation}.
   */
  public UiSplitPanelImpl(UiFactorySwing uiFactory, Orientation orientation) {

    super(uiFactory);
    this.componentTopOrLeft = null;
    this.componentBottomOrRight = null;
    JSplitPane splitPanel = new JSplitPane();
    this.adapter = new UiElementAdapterSwing<JSplitPane>(this, splitPanel);
    setOrientation(orientation);
    // initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiElementAdapterSwing<JSplitPane> getAdapter() {

    return this.adapter;
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
      getDelegate().setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    } else {
      getDelegate().setOrientation(JSplitPane.VERTICAL_SPLIT);
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
  public void setTopOrLeftComponent(CHILD component) {

    if (component.getParent() != null) {
      component.setParent(null);
    }
    if (this.componentTopOrLeft != null) {
      this.componentTopOrLeft.setParent(null);
    }
    this.componentTopOrLeft = component;
    JComponent jComponent = this.componentTopOrLeft.getAdapter().getDelegate();
    getDelegate().setTopComponent(jComponent);
    this.componentTopOrLeft.setParent(this);
  }

  /**
   * {@inheritDoc}
   */
  public void setBottomOrRightComponent(CHILD component) {

    if (this.componentBottomOrRight != null) {
      this.componentBottomOrRight.setParent(null);
    }
    this.componentBottomOrRight = component;
    JComponent jComponent = this.componentBottomOrRight.getAdapter().getDelegate();
    getDelegate().setBottomComponent(jComponent);
    this.componentBottomOrRight.setParent(this);
  }

  /**
   * {@inheritDoc}
   */
  public void setDividerPosition(double proportion) {

    getDelegate().setDividerLocation(proportion);
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
  public CHILD getTopOrLeftComponent() {

    return this.componentTopOrLeft;
  }

  /**
   * {@inheritDoc}
   */
  public CHILD getBottomOrRightComponent() {

    return this.componentBottomOrRight;
  }

  /**
   * {@inheritDoc}
   */
  public CHILD getChild(int index) {

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
