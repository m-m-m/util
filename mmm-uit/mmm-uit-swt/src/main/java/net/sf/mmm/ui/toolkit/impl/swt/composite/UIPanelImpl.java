/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.LayoutConstraints;
import net.sf.mmm.ui.toolkit.api.composite.Orientation;
import net.sf.mmm.ui.toolkit.api.composite.UIPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIPanel} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPanelImpl extends UIMultiComposite implements UIPanel {

  /** the synchron access to the {@link org.eclipse.swt.widgets.Composite} */
  private final SyncCompositeAccess syncAccess;

  /** the layout manager for the panel */
  private final LayoutManager layoutManager;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param borderTitle
   *        is the title of the border or <code>null</code> for NO border.
   * @param orientation
   *        is the orientation for the layout of the panel.
   */
  public UIPanelImpl(UIFactorySwt uiFactory, UISwtNode parentObject, String borderTitle,
      Orientation orientation) {

    super(uiFactory, parentObject, borderTitle);
    this.layoutManager = new LayoutManager();
    this.layoutManager.setOrientation(orientation);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
    this.syncAccess.setLayout(this.layoutManager);
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component) {

    addComponent(component, LayoutConstraints.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component, LayoutConstraints constraints) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    // c.getSyncAccess().setParentAccess(this.syncAccess);
    c.getSyncAccess().setLayoutData(constraints);
    c.setParent(this);
    this.components.add(c);
  }

  /**
   * {@inheritDoc}
   */
  public void addComponent(UIComponent component, LayoutConstraints constraints, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    // c.getSyncAccess().setParentAccess(this.syncAccess);
    c.getSyncAccess().setLayoutData(constraints);
    c.setParent(this);
    this.components.add(position, c);
  }

  /**
   * {@inheritDoc}
   */
  public void removeComponent(UIComponent component) {

    int index = this.components.indexOf(component);
    if (index >= 0) {
      removeComponent(index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void removeComponent(int index) {

    AbstractUIComponent c = this.components.remove(index);
    c.setParent(null);
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

}
