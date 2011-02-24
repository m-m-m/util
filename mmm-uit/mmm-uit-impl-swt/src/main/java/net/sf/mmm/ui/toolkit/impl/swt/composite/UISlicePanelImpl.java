/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSlicePanel} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UISlicePanelImpl extends AbstractUIPanel implements UiSlicePanel<AbstractUiElement> {

  /** the synchronous access to the {@link org.eclipse.swt.widgets.Composite} */
  private final SyncCompositeAccess syncAccess;

  /** the layout manager for the panel */
  private final LayoutManager layoutManager;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   * @param borderTitle is the title of the border or <code>null</code> for NO
   *        border.
   * @param orientation is the orientation for the layout of the panel.
   */
  public UISlicePanelImpl(UiFactorySwt uiFactory, UiSwtNode parentObject, String borderTitle,
      Orientation orientation) {

    super(uiFactory, parentObject, borderTitle);
    this.layoutManager = new LayoutManager(uiFactory);
    this.layoutManager.setOrientation(orientation);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
    this.syncAccess.setLayout(this.layoutManager);
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(AbstractUiElement component) {

    addChild(component, LayoutConstraints.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(AbstractUiElement component, LayoutConstraints constraints) {

    AbstractUiElement c = component;
    // c.getSyncAccess().setParentAccess(this.syncAccess);
    c.getSyncAccess().setLayoutData(constraints);
    c.setParent(this);
    this.components.add(c);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(AbstractUiElement component, LayoutConstraints constraints, int position) {

    AbstractUiElement c = component;
    // c.getSyncAccess().setParentAccess(this.syncAccess);
    c.getSyncAccess().setLayoutData(constraints);
    c.setParent(this);
    this.components.add(position, c);
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
  @Override
  public Orientation getOrientation() {

    return this.layoutManager.getOrientation();
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(AbstractUiElement component, int index) {

    throw new IllegalStateException();
  }

}
