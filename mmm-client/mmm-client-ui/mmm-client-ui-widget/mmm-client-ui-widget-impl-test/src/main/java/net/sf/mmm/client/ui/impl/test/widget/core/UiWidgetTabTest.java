/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetTab;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetTab;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestCompositeRegular;

/**
 * This is the implementation of {@link UiWidgetTab} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabTest extends AbstractUiWidgetTab<UiWidgetAdapterTestCompositeRegular> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTabTest(UiContext context) {

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
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTab> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTab.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTab create(UiContext context) {

      return new UiWidgetTabTest(context);
    }

  }

}
