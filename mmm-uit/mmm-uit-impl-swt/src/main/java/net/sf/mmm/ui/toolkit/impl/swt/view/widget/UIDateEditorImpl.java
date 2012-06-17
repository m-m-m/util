/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import java.util.Date;
import java.util.Locale;

import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncCompositeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditorImpl extends AbstractUiWidgetSwt<Composite> implements UiDateBox {

  /** @see #getAdapter() */
  private final SyncCompositeAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UIDateEditorImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    this.adapter = new SyncCompositeAccess(uiFactory, this, SWT.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncCompositeAccess getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(Date newDate) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  public Date getValue() {

    // TODO Auto-generated method stub
    return null;
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
  public Locale getLocale() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public void setLocale(Locale newLocale) {

    // TODO Auto-generated method stub

  }

}
