/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetRichTextField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtRichTextField;

/**
 * This is the implementation of {@link UiWidgetRichTextField} using GWT based on
 * {@link UiWidgetAdapterGwtRichTextField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRichTextFieldGwt extends AbstractUiWidgetRichTextField<UiWidgetAdapterGwtRichTextField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRichTextFieldGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRichTextField createWidgetAdapter() {

    return new UiWidgetAdapterGwtRichTextField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetRichTextField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRichTextField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRichTextField create(UiContext context) {

      return new UiWidgetRichTextFieldGwt(context);
    }

  }

}
