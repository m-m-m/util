/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetSlot;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtSlot;

/**
 * This is the implementation of {@link UiWidgetSlot} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetSlotGwt extends AbstractUiWidgetSlot<UiWidgetAdapterGwtSlot> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetSlotGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtSlot createWidgetAdapter() {

    return new UiWidgetAdapterGwtSlot();
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

      return new UiWidgetSlotGwt(context);
    }

  }

}
