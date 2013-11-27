/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetMainWindow;
import net.sf.mmm.client.ui.impl.javafx.widget.window.adapter.UiWidgetAdapterJavaFxMainWindow;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMainWindowJavaFx extends AbstractUiWidgetMainWindow<UiWidgetAdapterJavaFxMainWindow> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMainWindowJavaFx(UiContext context) {

    this(context, null);
  }

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetMainWindowJavaFx(UiContext context, UiWidgetAdapterJavaFxMainWindow widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWindowPositionAbsolute() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterJavaFxMainWindow createWidgetAdapter() {

    return new UiWidgetAdapterJavaFxMainWindow();
  }

}
