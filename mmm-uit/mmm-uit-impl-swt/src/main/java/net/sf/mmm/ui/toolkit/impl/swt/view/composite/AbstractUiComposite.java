/* $Id: AbstractUIComposite.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.event.UIRefreshEvent;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncGroupAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite} interface using
 * SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiComposite<E extends AbstractUiElement> extends AbstractUiElement
    implements UiComposite<E> {

  /** gives access to the {@link org.eclipse.swt.widgets.Group} */
  private final SyncGroupAccess syncGroupAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   * @param borderTitle is the title of the border or <code>null</code> for NO
   *        border.
   */
  public AbstractUiComposite(UiFactorySwt uiFactory, UiSwtNode parentObject, String borderTitle) {

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

    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      AbstractUiElement child = getChild(i);
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
   * this the {@link #getActiveSyncAccess() composite}. Override this method and
   * return <code>false</code> if you need special behaviour to build the SWT
   * tree.
   * 
   * @return <code>true</code> if children should be attached automatically,
   *         <code>false</code> if your implementation needs special behaviour.
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
    int componentCount = getChildCount();
    for (int i = 0; i < componentCount; i++) {
      AbstractUiElement component = getChild(i);
      component.refresh(event);
    }
  }

}
