/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRadioButtons;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtCellPanelRadiosHorizontal;

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
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetRadioButtonsGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtCellPanelRadiosHorizontal<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetRadioButtons> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtons.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetRadioButtons create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetRadioButtonsGwt(factory);
    }

  }

}
