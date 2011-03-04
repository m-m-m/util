/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.common.Orientation;
import net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncCompositeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiSimplePanel} interface
 * using SWT as the UI toolkit.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSimplePanelImpl<E extends AbstractUiElement> extends AbstractUiMultiComposite<E>
    implements UiSimplePanel<E> {

  /** @see #getSyncAccess() */
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
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.NORMAL);
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
  public void addChild(E child) {

    // c.getSyncAccess().setParentAccess(this.syncAccess);
    child.setParent(this);
    doAddChild(child);
  }

  /**
   * {@inheritDoc}
   */
  public void insertChild(E child, int position) {

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
  public SyncCompositeAccess getActiveSyncAccess() {

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
