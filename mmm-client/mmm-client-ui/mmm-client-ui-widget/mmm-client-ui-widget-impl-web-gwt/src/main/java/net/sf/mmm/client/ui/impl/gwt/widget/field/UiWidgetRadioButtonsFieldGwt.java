/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtCellPanelRadiosHorizontal;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsField} using GWT.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRadioButtonsFieldGwt<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE>, VALUE> implements
    UiWidgetRadioButtonsField<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRadioButtonsFieldGwt(UiContext context) {

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
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetRadioButtonsField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtonsField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRadioButtonsField create(UiContext context) {

      return new UiWidgetRadioButtonsFieldGwt(context);
    }

  }

}
