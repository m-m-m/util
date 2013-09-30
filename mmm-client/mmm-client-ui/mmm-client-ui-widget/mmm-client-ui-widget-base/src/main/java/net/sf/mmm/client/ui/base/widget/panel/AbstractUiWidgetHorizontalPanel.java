/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterHorizontalPanel;

/**
 * This is the abstract base implementation of {@link UiWidgetHorizontalPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetHorizontalPanel<ADAPTER extends UiWidgetAdapterHorizontalPanel> extends
    AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetRegular> implements UiWidgetHorizontalPanel {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetHorizontalPanel(UiContext context) {

    super(context);
    setPrimaryStyle(STYLE_PRIMARY);
  }

}
