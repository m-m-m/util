/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtIntegerBox;

/**
 * This is the implementation of {@link UiWidgetIntegerField} using GWT based on
 * {@link UiWidgetAdapterGwtIntegerBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerFieldGwt extends
    AbstractUiWidgetInputField<UiWidgetAdapterGwtIntegerBox<Integer>, Integer, Integer> implements UiWidgetIntegerField {

  /**
   * The constructor.
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetIntegerFieldGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtIntegerBox<Integer> createWidgetAdapter() {

    return new UiWidgetAdapterGwtIntegerBox<Integer>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetIntegerField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetIntegerField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetIntegerField create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetIntegerFieldGwt(factory);
    }

  }

}
