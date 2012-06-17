/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncCompositeAccess;
import net.sf.mmm.util.lang.api.Orientation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel} interface
 * using SWT as the UI toolkit.
 * 
 * @param <CHILD> is the generic type of the {@link #getChild(int)
 *        child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSimplePanelImpl<CHILD extends AbstractUiElement<? extends Control>> extends
    AbstractUiMultiCompositeSwt<Composite, CHILD> implements UiSimplePanel<CHILD> {

  /** @see #getAdapter() */
  private final SyncCompositeAccess syncAccess;

  /** @see #getOrientation() */
  private Orientation orientation;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param orientation is the orientation for the layout of the panel.
   */
  public UiSimplePanelImpl(UiFactorySwt uiFactory, Orientation orientation) {

    super(uiFactory);
    this.syncAccess = new SyncCompositeAccess(uiFactory, this, SWT.NORMAL);
    RowLayout rowLayout = new RowLayout();
    rowLayout.wrap = false;
    rowLayout.pack = false;
    rowLayout.justify = true;
    rowLayout.type = convertOrientation(orientation);
    rowLayout.marginLeft = 5;
    rowLayout.marginTop = 5;
    rowLayout.marginRight = 5;
    rowLayout.marginBottom = 5;
    rowLayout.spacing = 0;
    this.syncAccess.setLayout(rowLayout);
  }

  /**
   * {@inheritDoc}
   */
  public void addChild(CHILD child) {

    // c.getSyncAccess().setParentAccess(this.syncAccess);
    child.setParent(this);
    doAddChild(child);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(CHILD child, int position) {

    // c.getSyncAccess().setParentAccess(this.syncAccess);
    // child.getSyncAccess().setLayoutData(constraints);
    child.setParent(this);
    doInsertChild(child, position);
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
  public SyncCompositeAccess getAdapter() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void setOrientation(Orientation orientation) {

    this.orientation = orientation;
    // this.syncAccess.layout();
  }

  /**
   * {@inheritDoc}
   */
  public Orientation getOrientation() {

    return this.orientation;
  }

}
