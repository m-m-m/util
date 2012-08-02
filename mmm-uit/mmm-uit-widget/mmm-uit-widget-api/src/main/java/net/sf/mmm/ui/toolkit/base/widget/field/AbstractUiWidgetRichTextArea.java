/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterRichTextArea;

/**
 * This is the abstract base implementation of {@link UiWidgetRichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetRichTextArea<ADAPTER extends UiWidgetAdapterRichTextArea<?, String>> extends
    AbstractUiWidgetTextAreaBase<ADAPTER> implements UiWidgetRichTextArea {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetRichTextArea(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

}
