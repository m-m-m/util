/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDateField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtDateField;

/**
 * This is the implementation of {@link UiWidgetDateField} using GWT based on
 * {@link UiWidgetAdapterGwtDateField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDateFieldGwt extends AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtDateField<Date>, Date, Date>
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
  protected UiWidgetAdapterGwtDateField<Date> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDateField<Date>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetDateField> {

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
