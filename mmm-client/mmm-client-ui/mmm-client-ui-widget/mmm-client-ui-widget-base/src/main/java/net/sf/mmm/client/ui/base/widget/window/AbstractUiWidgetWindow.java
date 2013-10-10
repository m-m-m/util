/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetWindow;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterWindow;

/**
 * This is the abstract base implementation of {@link UiWidgetWindow}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetWindow<ADAPTER extends UiWidgetAdapterWindow> extends
    AbstractUiWidgetAbstractDialogWindow<ADAPTER> implements UiWidgetWindow {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetWindow(UiContext context) {

    super(context);
  }

}
