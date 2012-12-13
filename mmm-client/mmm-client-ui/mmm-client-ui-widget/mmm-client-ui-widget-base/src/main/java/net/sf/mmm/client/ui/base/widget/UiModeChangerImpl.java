/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the default implementation of {@link UiModeChanger}. It delegates to
 * {@link UiWidgetAdapter#setMode(boolean, AbstractUiWidgetReal)} if the {@link UiWidgetAdapter} has
 * {@link AbstractUiWidgetReal#hasWidgetAdapter() already been created}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiModeChangerImpl extends AbstractUiModeChanger {

  /**
   * The constructor.
   * 
   */
  public UiModeChangerImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void changeMode(AbstractUiWidgetReal<?, ?> widget, UiMode newMode, UiWidgetAdapter<?> widgetAdapter) {

    boolean editMode = (newMode == UiMode.EDIT);
    widgetAdapter.setMode(editMode, widget);
  }

}
