/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetGridRow;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridRow;

/**
 * This is the implementation of {@link UiWidgetGridRow} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetGridRowGwt extends AbstractUiWidgetGridRow<UiWidgetAdapterGwtGridRow> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetGridRowGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtGridRow createWidgetAdapter() {

    return new UiWidgetAdapterGwtGridRow();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetGridRow> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetGridRow.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetGridRow create(UiContext context) {

      return new UiWidgetGridRowGwt(context);
    }
  }

}
