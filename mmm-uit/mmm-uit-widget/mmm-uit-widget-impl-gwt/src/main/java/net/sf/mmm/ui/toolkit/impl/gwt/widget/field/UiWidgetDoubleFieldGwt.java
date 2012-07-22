/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.DoubleBox;

/**
 * This is the implementation of {@link UiWidgetDoubleField} using GWT based on {@link DoubleBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDoubleFieldGwt extends UiWidgetInputFieldGwtValueBox<Double, DoubleBox> implements
    UiWidgetDoubleField {

  /**
   * The constructor.
   */
  public UiWidgetDoubleFieldGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DoubleBox createWidget() {

    return new DoubleBox();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetDoubleField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetDoubleField.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetDoubleField create() {

      return new UiWidgetDoubleFieldGwt();
    }

  }

}
