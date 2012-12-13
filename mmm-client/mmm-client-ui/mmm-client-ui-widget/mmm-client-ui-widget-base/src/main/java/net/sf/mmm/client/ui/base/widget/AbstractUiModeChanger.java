/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link UiModeChanger}. It delegates to
 * {@link #changeMode(AbstractUiWidgetReal, UiMode, UiWidgetAdapter)} if the {@link UiWidgetAdapter} has
 * {@link AbstractUiWidgetReal#hasWidgetAdapter() already been created}. Otherwise it does nothing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiModeChanger implements UiModeChanger {

  /**
   * The constructor.
   */
  public AbstractUiModeChanger() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeMode(AbstractUiWidgetReal<?, ?> widget, UiMode newMode) {

    if (widget.hasWidgetAdapter()) {
      UiWidgetAdapter<?> widgetAdapter = widget.getWidgetAdapter();
      changeMode(widget, newMode, widgetAdapter);
    }
  }

  /**
   * This method is called from {@link #changeMode(AbstractUiWidgetReal, UiMode)} if the
   * {@link UiWidgetAdapter} has {@link AbstractUiWidgetReal#hasWidgetAdapter() already been created}.
   * 
   * @param widget is the {@link net.sf.mmm.client.ui.api.widget.UiWidget} where to change the {@link UiMode}
   *        to <code>newMode</code>.
   * @param newMode is the new {@link UiMode}.
   * @param widgetAdapter is the {@link UiWidgetAdapter} of the <code>widget</code>.
   */
  protected abstract void changeMode(AbstractUiWidgetReal<?, ?> widget, UiMode newMode, UiWidgetAdapter<?> widgetAdapter);
}
