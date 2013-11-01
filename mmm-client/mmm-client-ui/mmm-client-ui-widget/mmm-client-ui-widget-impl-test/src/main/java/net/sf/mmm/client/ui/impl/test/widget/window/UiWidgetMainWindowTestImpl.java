/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetMainWindow;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestCompositeRegular;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMainWindowTestImpl extends AbstractUiWidgetMainWindow<UiWidgetAdapterTestCompositeRegular> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMainWindowTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestCompositeRegular createWidgetAdapter() {

    return new UiWidgetAdapterTestCompositeRegular();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWindowPositionAbsolute() {

    return false;
  }

}
