/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextArea;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextArea;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtTextArea;

/**
 * This is the implementation of {@link UiWidgetTextArea} using GWT based on
 * {@link UiWidgetAdapterGwtTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextAreaGwt extends AbstractUiWidgetTextArea<UiWidgetAdapterGwtTextArea<String>> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTextAreaGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTextArea<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtTextArea<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTextArea create(UiContext context) {

      return new UiWidgetTextAreaGwt(context);
    }

  }

}
