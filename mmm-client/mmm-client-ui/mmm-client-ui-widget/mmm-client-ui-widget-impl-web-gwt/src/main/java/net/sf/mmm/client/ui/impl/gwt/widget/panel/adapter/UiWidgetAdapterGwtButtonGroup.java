/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterButtonGroup;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ButtonGroup;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterButtonGroup} using GWT based on
 * {@link ButtonGroup}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtButtonGroup extends UiWidgetAdapterGwtPanel<ButtonGroup> implements
    UiWidgetAdapterButtonGroup {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtButtonGroup() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ButtonGroup createToplevelWidget() {

    return new ButtonGroup();
  }

}
