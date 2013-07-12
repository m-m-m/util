/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemSeparator;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenuItemSeparator;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;

/**
 * This is the implementation of {@link UiWidgetMenuItemSeparator} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuItemSeparatorTest extends AbstractUiWidgetMenuItemSeparator<UiWidgetAdapterTest> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetMenuItemSeparatorTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTest createWidgetAdapter() {

    return new UiWidgetAdapterTest();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetMenuItemSeparator> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuItemSeparator.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenuItemSeparator create(UiContext context) {

      return new UiWidgetMenuItemSeparatorTest(context);
    }

  }

}
