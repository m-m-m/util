/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetRichTextArea;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtRichTextArea;

/**
 * This is the implementation of {@link UiWidgetRichTextArea} using GWT based on
 * {@link UiWidgetAdapterGwtRichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRichTextAreaGwt extends AbstractUiWidgetRichTextArea<UiWidgetAdapterGwtRichTextArea<String>> {

  /**
   * The constructor.
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetRichTextAreaGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRichTextArea<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtRichTextArea<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetRichTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRichTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRichTextArea create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetRichTextAreaGwt(factory);
    }

  }

}
