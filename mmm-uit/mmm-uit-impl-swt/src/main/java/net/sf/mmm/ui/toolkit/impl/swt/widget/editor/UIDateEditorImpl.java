/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget.editor;

import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.widget.AbstractUIWidget;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiDateBox} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditorImpl extends AbstractUIWidget implements UiDateBox {

  /** */
  private final SyncCompositeAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIDateEditorImpl(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.DEFAULT);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
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
