/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtons;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtCellPanelRadiosHorizontal;

/**
 * This is the implementation of {@link UiWidgetRadioButtons} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetRadioButtonsGwt<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE>, VALUE> implements
    UiWidgetRadioButtons<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRadioButtonsGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetRadioButtons> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtons.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRadioButtons create(AbstractUiContext context) {

      return new UiWidgetRadioButtonsGwt(context);
    }

  }

}
