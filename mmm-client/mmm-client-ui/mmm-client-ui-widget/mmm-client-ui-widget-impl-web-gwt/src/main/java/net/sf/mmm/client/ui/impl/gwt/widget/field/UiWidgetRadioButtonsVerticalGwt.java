/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsVertical;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtCellPanelRadiosVertical;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsVertical} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetRadioButtonsVerticalGwt<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterGwtCellPanelRadiosVertical<VALUE>, VALUE> implements
    UiWidgetRadioButtonsVertical<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRadioButtonsVerticalGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCellPanelRadiosVertical<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtCellPanelRadiosVertical<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetRadioButtonsVertical> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtonsVertical.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRadioButtonsVertical create(AbstractUiContext context) {

      return new UiWidgetRadioButtonsVerticalGwt(context);
    }

  }

}
