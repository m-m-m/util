/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.base.view.widget.AbstractUiWidget;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.AbstractSyncControlAccess;

import org.eclipse.swt.widgets.Control;

/**
 * This class is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiWidget} using SWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type for the {@link #getAdapter() adapter}.
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetSwt<DELEGATE extends Control> extends
    AbstractUiWidget<DELEGATE> {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiWidgetSwt(UiFactorySwt uiFactory) {

    super(uiFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract AbstractSyncControlAccess<DELEGATE> getAdapter();

}
