/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetLongField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtLongBox;

/**
 * This is the implementation of {@link UiWidgetLongField} using GWT based on
 * {@link UiWidgetAdapterGwtLongBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLongFieldGwt extends AbstractUiWidgetInputField<UiWidgetAdapterGwtLongBox<Long>, Long, Long>
    implements UiWidgetLongField {

  /**
   * The constructor.
   */
  public UiWidgetLongFieldGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLongBox<Long> createWidgetAdapter() {

    return new UiWidgetAdapterGwtLongBox<Long>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetLongField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLongField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLongField create() {

      return new UiWidgetLongFieldGwt();
    }

  }

}
