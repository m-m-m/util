/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetRichTextArea;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtRichTextArea;

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
   */
  public UiWidgetRichTextAreaGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRichTextArea<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtRichTextArea<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetRichTextArea> {

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
    public UiWidgetRichTextArea create() {

      return new UiWidgetRichTextAreaGwt();
    }

  }

}
