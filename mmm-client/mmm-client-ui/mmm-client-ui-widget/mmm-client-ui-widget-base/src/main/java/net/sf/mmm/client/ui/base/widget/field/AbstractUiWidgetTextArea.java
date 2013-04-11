/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextArea;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextArea;

/**
 * This is the abstract base implementation of {@link UiWidgetTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetTextArea<ADAPTER extends UiWidgetAdapterTextArea<String>> extends
    AbstractUiWidgetTextAreaBase<ADAPTER> implements UiWidgetTextArea {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetTextArea(UiContext context) {

    super(context);
  }

}
