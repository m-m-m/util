/* $Id: UITabbedPanelImpl.java 962 2011-02-24 23:15:27Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTabFolderAccess;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTabItemAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiTabPanel} interface using
 * SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTabbedPanelImpl<E extends AbstractUiElement> extends AbstractUiMultiComposite<E>
    implements UiTabPanel<E> {

  /** @see #getSyncAccess() */
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

    super(uiFactory);
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
  public void addChild(E child, String title) {

    child.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = child.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(itemAccess);
    doAddChild(child);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E child, String title, int index) {

    child.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE, index);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = child.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(index, itemAccess);
    insertChild(child, index);
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
      AbstractUiElement child = getChild(i);
      Control control = child.getSyncAccess().getSwtObject();
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
