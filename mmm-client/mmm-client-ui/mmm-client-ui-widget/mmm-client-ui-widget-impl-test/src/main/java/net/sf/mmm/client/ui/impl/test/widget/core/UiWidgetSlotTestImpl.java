/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetSlot;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestCompositeRegular;

/**
 * This is the implementation of {@link UiWidgetSlot} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetSlotTestImpl extends AbstractUiWidgetSlot<UiWidgetAdapterTestCompositeRegular> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetSlotTestImpl(UiContext context) {

    super(context, null);
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
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetSlot> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetSlot.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetSlot create(UiContext context) {

      return new UiWidgetSlotTestImpl(context);
    }

  }

}
