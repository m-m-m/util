/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.menu;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemSeparator;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetReal;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemSeparator;

/**
 * This is the abstract base implementation of {@link UiWidgetMenuItemSeparator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetMenuItemSeparator<ADAPTER extends UiWidgetAdapterMenuItemSeparator> extends
    AbstractUiWidgetReal<ADAPTER, Void> implements UiWidgetMenuItemSeparator {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetMenuItemSeparator(AbstractUiContext context) {

    super(context);
  }

}
