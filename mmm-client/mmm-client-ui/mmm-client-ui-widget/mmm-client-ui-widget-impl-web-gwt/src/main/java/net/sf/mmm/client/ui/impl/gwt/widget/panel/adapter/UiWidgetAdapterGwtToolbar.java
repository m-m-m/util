/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterToolbar;
import net.sf.mmm.client.ui.gwt.widgets.Toolbar;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link Toolbar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtToolbar extends UiWidgetAdapterGwtPanel<Toolbar> implements UiWidgetAdapterToolbar {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtToolbar() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Toolbar createToplevelWidget() {

    return new Toolbar();
  }

}
