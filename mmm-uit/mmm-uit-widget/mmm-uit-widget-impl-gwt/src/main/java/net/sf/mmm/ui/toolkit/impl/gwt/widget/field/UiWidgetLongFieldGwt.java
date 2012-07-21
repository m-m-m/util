/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetLongField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.LongBox;

/**
 * This is the implementation of {@link UiWidgetLongField} using GWT based on {@link LongBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLongFieldGwt extends UiWidgetInputFieldGwtValueBox<Long, LongBox> implements UiWidgetLongField {

  /**
   * The constructor.
   */
  public UiWidgetLongFieldGwt() {

    super(new LongBox());
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
    public UiWidgetLongField create() {

      return new UiWidgetLongFieldGwt();
    }

  }

}
