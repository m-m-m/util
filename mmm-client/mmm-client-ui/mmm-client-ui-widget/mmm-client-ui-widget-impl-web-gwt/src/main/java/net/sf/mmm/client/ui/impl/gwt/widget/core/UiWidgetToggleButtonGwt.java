/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetToggleButton;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetToggleButton;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtToggleButton;

/**
 * This is the implementation of {@link UiWidgetToggleButton} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetToggleButtonGwt extends AbstractUiWidgetToggleButton<UiWidgetAdapterGwtToggleButton> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetToggleButtonGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtToggleButton createWidgetAdapter() {

    return new UiWidgetAdapterGwtToggleButton();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetToggleButton> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetToggleButton.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetToggleButton create(UiContext context) {

      return new UiWidgetToggleButtonGwt(context);
    }

  }

}
