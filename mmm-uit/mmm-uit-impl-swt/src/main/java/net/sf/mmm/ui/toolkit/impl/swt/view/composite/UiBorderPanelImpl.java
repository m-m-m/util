/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.composite;

import net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiElement;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncGroupAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiBorderPanel} interface
 * using SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int)
 *        child-elements}.
 * @since 1.0.0
 */
public class UiBorderPanelImpl<CHILD extends AbstractUiElement<? extends Widget>> extends
    AbstractUiSingleCompositeSwt<Group, CHILD> implements UiBorderPanel<CHILD> {

  /** @see #getAdapter() */
  private final SyncGroupAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiBorderPanelImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    this.adapter = new SyncGroupAccess(uiFactory, this, SWT.NONE);
    this.adapter.setLayout(new FillLayout());
    // this.syncGroupAccess.setText(borderTitle);
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.adapter.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    this.adapter.setText(title);
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
  public SyncGroupAccess getAdapter() {

    return this.adapter;
  }
}
