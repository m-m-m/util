/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetComboBox;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtListBoxCombo;

/**
 * This is a simple implementation of {@link UiWidgetComboBox} using GWT based on
 * {@link UiWidgetAdapterGwtListBoxCombo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
// TODO: use alternate widget adapter using HTML5 (ideally use browser switch to choose)
// <input list=languages>
// <datalist id=languages>
// <option value="English"></option>
// <option value="Dutch"></option>
// </datalist>
public class UiWidgetComboBoxGwt<VALUE> extends AbstractUiWidgetComboBox<UiWidgetAdapterGwtListBoxCombo<VALUE>, VALUE> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetComboBoxGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtListBoxCombo<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtListBoxCombo<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetComboBox> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetComboBox.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetComboBox create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetComboBoxGwt(factory);
    }

  }

}
