/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenuBar;
import net.sf.mmm.client.ui.impl.test.widget.menu.adapter.UiWidgetAdapterTestMenuBar;

/**
 * This is the implementation of {@link UiWidgetMenuBar} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuBarTestImpl extends AbstractUiWidgetMenuBar<UiWidgetAdapterTestMenuBar> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMenuBarTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestMenuBar createWidgetAdapter() {

    return new UiWidgetAdapterTestMenuBar();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetMenuBar> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuBar.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenuBar create(UiContext context) {

      return new UiWidgetMenuBarTestImpl(context);
    }

  }

}
