/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenu;
import net.sf.mmm.client.ui.impl.test.widget.menu.adapter.UiWidgetAdapterTestMenu;

/**
 * This is the implementation of {@link UiWidgetMenu} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuTest extends AbstractUiWidgetMenu<UiWidgetAdapterTestMenu> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMenuTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestMenu createWidgetAdapter() {

    return new UiWidgetAdapterTestMenu();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetMenu> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenu.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenu create(UiContext context) {

      return new UiWidgetMenuTest(context);
    }

  }

}
