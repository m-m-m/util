/* $Id: UISlicePanelImpl.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.common.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncCompositeAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel} interface using
 * SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSlicePanelImpl<E extends AbstractUiElement> extends AbstractUiMultiComposite<E>
    implements UiSlicePanel<E> {

  /** @see #getSyncAccess() */
  private final SyncCompositeAccess syncAccess;

  /** the layout manager for the panel */
  private final LayoutManager layoutManager;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param orientation is the orientation for the layout of the panel.
   */
  public UiSlicePanelImpl(UiFactorySwt uiFactory, Orientation orientation) {

    super(uiFactory);
    this.layoutManager = new LayoutManager(uiFactory);
    this.layoutManager.setOrientation(orientation);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
    this.syncAccess.setLayout(this.layoutManager);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(E component) {

    addChild(component, LayoutConstraints.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(E child, LayoutConstraints constraints) {

    // c.getSyncAccess().setParentAccess(this.syncAccess);
    child.getSyncAccess().setLayoutData(constraints);
    child.setParent(this);
    super.addChild(child);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E child, LayoutConstraints constraints, int position) {

    // c.getSyncAccess().setParentAccess(this.syncAccess);
    child.getSyncAccess().setLayoutData(constraints);
    child.setParent(this);
    super.insertChild(child, position);
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
  @Override
  public SyncCompositeAccess getActiveSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void setOrientation(Orientation orientation) {

    this.layoutManager.setOrientation(orientation);
    this.syncAccess.layout();
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return this.layoutManager.getOrientation();
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E component, int index) {

    throw new IllegalStateException();
  }

}
