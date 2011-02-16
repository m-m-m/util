/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncGroupAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUIComposite extends AbstractUIComponent implements UiComposite {

  /** gives access to the {@link org.eclipse.swt.widgets.Group} */
  private final SyncGroupAccess syncGroupAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param borderTitle is the title of the border or <code>null</code> for NO
   *        border.
   */
  public AbstractUIComposite(UIFactorySwt uiFactory, UISwtNode parentObject, String borderTitle) {

    super(uiFactory, parentObject);
    if (borderTitle == null) {
      this.syncGroupAccess = null;
    } else {
      this.syncGroupAccess = new SyncGroupAccess(uiFactory, SWT.NONE);
      this.syncGroupAccess.setLayout(new FillLayout());
      this.syncGroupAccess.setText(borderTitle);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return Orientation.HORIZONTAL;
  }

  /**
   * {@inheritDoc}
   */
  public String getBorderTitle() {

    if (this.syncGroupAccess == null) {
      return null;
    } else {
      return this.syncGroupAccess.getText();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractSyncCompositeAccess getSyncAccess() {

    if (this.syncGroupAccess == null) {
      return getActiveSyncAccess();
    } else {
      return this.syncGroupAccess;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract AbstractSyncCompositeAccess getActiveSyncAccess();

  /**
   * {@inheritDoc}
   */
  public abstract AbstractUIComponent getComponent(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public void create() {

    if (this.syncGroupAccess != null) {
      getActiveSyncAccess().setParentAccess(this.syncGroupAccess);
    }
    super.create();
    createChildren();
  }

  /**
   * This method is called to create the children of this composite.
   */
  protected void createChildren() {

    int count = getComponentCount();
    for (int i = 0; i < count; i++) {
      AbstractUIComponent child = getComponent(i);
      if (child != null) {
        child.create();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    // TODO
    super.dispose();
  }

  /**
   * This method determines if an SWT child will automatically be attached to
   * this the {@link #getActiveSyncAccess() composite}. Override this method
   * and return <code>false</code> if you need special behaviour to build the
   * SWT tree.
   * 
   * @return <code>true</code> if children should be attached automatically,
   *         <code>false</code> if your implementation needs special
   *         behaviour.
   */
  public boolean isAttachToActiveAccess() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void refresh(UIRefreshEvent event) {

    super.refresh(event);
    int componentCount = getComponentCount();
    for (int i = 0; i < componentCount; i++) {
      AbstractUIComponent component = getComponent(i);
      component.refresh(event);
    }
  }

}
