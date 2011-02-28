/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTabFolderAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTabItemAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTabbedPanelImpl extends AbstractUiPanel implements UiTabPanel<AbstractUiElement> {

  /** the synchronous access to the {@link org.eclipse.swt.widgets.TabFolder} */
  private final SyncTabFolderAccess syncAccess;

  /** the list of the tab-items */
  private final List<SyncTabItemAccess> tabItems;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UiTabbedPanelImpl(UiFactorySwt uiFactory, AbstractUiElement parentObject) {

    super(uiFactory, parentObject, null);
    this.syncAccess = new SyncTabFolderAccess(uiFactory, SWT.DEFAULT);
    this.tabItems = new ArrayList<SyncTabItemAccess>();
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    // TODO: allow vertical tabs?
    return Orientation.HORIZONTAL;
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(UiElement component, String title) {

    AbstractUiElement c = (AbstractUiElement) component;
    c.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = c.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(itemAccess);
    this.components.add(c);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(UiElement component, String title, int position) {

    AbstractUiElement c = (AbstractUiElement) component;
    c.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE, position);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = c.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(position, itemAccess);
    this.components.add(position, c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiElement removeChild(int position) {

    AbstractUiElement component = super.removeChild(position);
    SyncTabItemAccess itemAccess = this.tabItems.remove(position);
    itemAccess.dispose();
    return component;
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
  protected void createChildren() {

    super.createChildren();
    int len = this.tabItems.size();
    for (int i = 0; i < len; i++) {
      AbstractUiElement component = this.components.get(i);
      Control control = component.getSyncAccess().getSwtObject();
      SyncTabItemAccess itemAccess = this.tabItems.get(i);
      itemAccess.setControl(control);
      itemAccess.create();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncTabFolderAccess getActiveSyncAccess() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(UiElement component, int index) {

    throw new IllegalStateException();
  }

}
