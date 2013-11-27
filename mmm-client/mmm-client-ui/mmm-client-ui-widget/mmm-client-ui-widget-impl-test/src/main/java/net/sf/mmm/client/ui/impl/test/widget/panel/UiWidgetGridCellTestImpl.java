/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetGridCell;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTestCompositeRegular;

/**
 * This is the implementation of {@link UiWidgetGridCell} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetGridCellTestImpl extends AbstractUiWidgetGridCell<UiWidgetAdapterTestCompositeRegular> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetGridCellTestImpl(UiContext context) {

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
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetGridCell> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetGridCell.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetGridCell create(UiContext context) {

      return new UiWidgetGridCellTestImpl(context);
    }
  }

}
