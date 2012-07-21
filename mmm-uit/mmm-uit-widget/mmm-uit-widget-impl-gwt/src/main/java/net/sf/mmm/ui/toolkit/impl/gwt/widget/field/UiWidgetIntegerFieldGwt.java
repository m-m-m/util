/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.IntegerBox;

/**
 * This is the implementation of {@link UiWidgetIntegerField} using GWT based on {@link IntegerBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerFieldGwt extends UiWidgetInputFieldGwtValueBox<Integer, IntegerBox> implements
    UiWidgetIntegerField {

  /**
   * The constructor.
   */
  public UiWidgetIntegerFieldGwt() {

    super(new IntegerBox());
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
    public UiWidgetIntegerField create() {

      return new UiWidgetIntegerFieldGwt();
    }

  }

}
