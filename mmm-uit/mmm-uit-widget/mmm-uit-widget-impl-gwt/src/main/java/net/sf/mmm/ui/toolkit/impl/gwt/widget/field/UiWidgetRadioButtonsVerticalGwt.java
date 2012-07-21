/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRadioButtonsVertical;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsVertical} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetRadioButtonsVerticalGwt<VALUE> extends UiWidgetRadioButtonsGwtBase<VALUE, VerticalPanel> implements
    UiWidgetRadioButtonsVertical<VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetRadioButtonsVerticalGwt() {

    super(new VerticalPanel());
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetRadioButtonsVertical> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtonsVertical.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetRadioButtonsVertical create() {

      return new UiWidgetRadioButtonsVerticalGwt();
    }

  }

}
