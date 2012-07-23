/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtListBoxCombo;

/**
 * This is a simple implementation of {@link UiWidgetComboBox} using GWT based on
 * {@link UiWidgetAdapterGwtListBoxCombo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetComboBoxGwt<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterGwtListBoxCombo<VALUE>, VALUE> implements UiWidgetComboBox<VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetComboBoxGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtListBoxCombo<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtListBoxCombo<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetComboBox> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetComboBox.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetComboBox create() {

      return new UiWidgetComboBoxGwt();
    }

  }

}
