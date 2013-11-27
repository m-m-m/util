/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemClickable;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetClickable;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemClickable;

/**
 * This is the abstract base implementation of {@link UiWidgetMenuItemClickable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenuItemClickable<ADAPTER extends UiWidgetAdapterMenuItemClickable> extends
    AbstractUiWidgetClickable<ADAPTER, Void> implements UiWidgetMenuItemClickable {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetMenuItemClickable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

}
