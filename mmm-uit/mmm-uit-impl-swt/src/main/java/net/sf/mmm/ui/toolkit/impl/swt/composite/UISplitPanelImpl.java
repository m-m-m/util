/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncGroupAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncSashFormAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSplitPanel} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UISplitPanelImpl extends AbstractUIComposite implements UiSplitPanel {

  /** the synchronous access to the sash-form */
  private final SyncSashFormAccess syncAccess;

  /** the synchronous access to the top or left composite */
  private final SyncCompositeAccess syncTopLeft;

  /** the synchronous access to the bottom or right composite */
  private final SyncCompositeAccess syncBottomRight;

  /** the component top or left */
  private AbstractUIComponent componentTopOrLeft;

  /** the component bottom or right */
  private AbstractUIComponent componentBottomOrRight;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param borderTitle is the title of the border or <code>null</code> for NO
   *        border.
   * @param orientation is the orientation of the two child-components in this
   *        split-pane.
   */
  public UISplitPanelImpl(UIFactorySwt uiFactory, AbstractUIComponent parentObject,
      String borderTitle, Orientation orientation) {

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
    this.syncTopLeft = new SyncCompositeAccess(uiFactory, SWT.NONE);
    this.syncTopLeft.setLayout(new FillLayout());
    this.syncTopLeft.setParentAccess(this.syncAccess);
    this.syncBottomRight = new SyncCompositeAccess(uiFactory, SWT.NONE);
    this.syncBottomRight.setLayout(new FillLayout());
    this.syncBottomRight.setParentAccess(this.syncAccess);
    /*
     * final int sashStyle = style; getFactory().invokeSynchron(new Runnable() {
     * 
     * public void run() {
     * 
     * UISplitPanelImpl.this.splitPanel = new SashForm(parent, sashStyle);
     * UISplitPanelImpl.this.compositeTopLeft = new
     * Composite(UISplitPanelImpl.this.splitPanel, SWT.NONE);
     * UISplitPanelImpl.this.compositeTopLeft.setLayout(new FillLayout());
     * UISplitPanelImpl.this.compositeBottomRight = new Composite(
     * UISplitPanelImpl.this.splitPanel, SWT.NONE);
     * UISplitPanelImpl.this.compositeBottomRight.setLayout(new FillLayout()); }
     * });
     */
    this.componentTopOrLeft = null;
    this.componentBottomOrRight = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createChildren() {

    this.syncTopLeft.create();
    this.syncBottomRight.create();
    super.createChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncSashFormAccess getActiveSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void setOrientation(Orientation orientation) {

    if (orientation == Orientation.HORIZONTAL) {
      this.syncAccess.setOrientation(SWT.HORIZONTAL);
    } else {
      this.syncAccess.setOrientation(SWT.VERTICAL);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Orientation getOrientation() {

    if (this.syncAccess.hasStyle(SWT.HORIZONTAL)) {
      return Orientation.HORIZONTAL;
    } else {
      return Orientation.VERTICAL;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setTopOrLeftComponent(UiElement component) {

    if (this.componentTopOrLeft != null) {
      // TODO
    }
    this.componentTopOrLeft = (AbstractUIComponent) component;
    this.componentTopOrLeft.getSyncAccess().setParentAccess(this.syncTopLeft);
    this.componentTopOrLeft.setParent(this);

  }

  /**
   * {@inheritDoc}
   */
  public void setBottomOrRightComponent(UiElement component) {

    if (this.componentBottomOrRight != null) {
      // TODO
    }
    this.componentBottomOrRight = (AbstractUIComponent) component;
    this.componentBottomOrRight.getSyncAccess().setParentAccess(this.syncBottomRight);
    this.componentBottomOrRight.setParent(this);
  }

  /**
   * {@inheritDoc}
   */
  public void setDividerPosition(double proportion) {

    final int percent = (int) (proportion * 100);
    this.syncAccess.setWeights(percent);
    /*
     * if ((this.componentBottomOrRight != null) && (this.componentTopOrLeft !=
     * null)) { } getFactory().invokeSynchron(new Runnable() {
     * 
     * public void run() { UISplitPanelImpl.this.splitPanel.setWeights(new int[]
     * {percent, 100 - percent}); } });
     */
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
  public AbstractUIComponent getTopOrLeftComponent() {

    return this.componentTopOrLeft;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractUIComponent getBottomOrRightComponent() {

    return this.componentBottomOrRight;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    this.syncAccess.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    return this.syncAccess.isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUIComponent getComponent(int index) {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAttachToActiveAccess() {

    return false;
  }

}
