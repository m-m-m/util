/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDateField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtDateBox;

/**
 * This is the implementation of {@link UiWidgetDateField} using GWT based on
 * {@link UiWidgetAdapterGwtDateBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDateFieldGwt extends AbstractUiWidgetInputField<UiWidgetAdapterGwtDateBox<Date>, Date, Date>
    implements UiWidgetDateField {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetDateFieldGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtDateBox<Date> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDateBox<Date>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetDateField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetDateField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetDateField create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetDateFieldGwt(factory);
    }

  }

}
