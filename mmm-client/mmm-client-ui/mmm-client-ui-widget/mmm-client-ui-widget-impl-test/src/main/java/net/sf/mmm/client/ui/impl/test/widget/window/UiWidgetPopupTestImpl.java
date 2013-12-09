/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetPopup;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestCompositeRegular;

/**
 * This is the implementation of {@link UiWidgetPopup} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPopupTestImpl extends AbstractUiWidgetPopup<UiWidgetAdapterTestCompositeRegular> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetPopupTestImpl(UiContext context) {

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
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetButtonPanel createButtonPanel() {

    return getContext().getWidgetFactory().create(UiWidgetButtonPanel.class);
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetPopup> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetPopup.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetPopup create(UiContext context) {

      return new UiWidgetPopupTestImpl(context);
    }
  }

}
