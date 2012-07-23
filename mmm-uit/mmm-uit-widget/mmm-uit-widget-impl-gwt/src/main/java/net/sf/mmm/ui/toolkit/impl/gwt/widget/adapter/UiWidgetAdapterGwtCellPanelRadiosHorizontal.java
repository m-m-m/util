/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterOptionsField}
 * using GWT based on {@link HorizontalPanel} and {@link com.google.gwt.user.client.ui.RadioButton}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE> extends UiWidgetAdapterGwtCellPanelRadios<VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtCellPanelRadiosHorizontal() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CellPanel createWidget() {

    return new HorizontalPanel();
  }

}
