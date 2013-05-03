/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLink;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetClickable;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterLink;

/**
 * This is the abstract base implementation of {@link UiWidgetLink}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetLink<ADAPTER extends UiWidgetAdapterLink> extends
    AbstractUiWidgetClickable<ADAPTER> implements UiWidgetLink {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetLink(UiContext context) {

    super(context);
    setPrimaryStyle(PRIMARY_STYLE);
  }

}
