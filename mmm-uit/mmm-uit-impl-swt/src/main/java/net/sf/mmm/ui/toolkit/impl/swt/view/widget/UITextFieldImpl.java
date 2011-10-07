/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTextAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTextField} interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITextFieldImpl extends AbstractUiWidgetSwt<Text> implements UiTextField {

  /** @see #getAdapter() */
  private final SyncTextAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UITextFieldImpl(UiFactorySwt uiFactory) {

    super(uiFactory);
    int style = SWT.DEFAULT;
    this.adapter = new SyncTextAccess(uiFactory, this, style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncTextAccess getAdapter() {

    return this.adapter;
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
  public void setValue(String text) {

    this.adapter.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.adapter.getText();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.adapter.isEditable();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    this.adapter.setEditable(editableFlag);
  }

}
