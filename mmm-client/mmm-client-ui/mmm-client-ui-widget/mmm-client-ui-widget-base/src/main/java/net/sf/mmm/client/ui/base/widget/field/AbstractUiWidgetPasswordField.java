/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterPasswordField;

/**
 * This is the abstract base implementation of {@link UiWidgetPasswordField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetPasswordField<ADAPTER extends UiWidgetAdapterPasswordField> extends
    AbstractUiWidgetTextualInputField<ADAPTER, String, String> implements UiWidgetPasswordField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetPasswordField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<String> getValueClass() {

    return String.class;
  }

}
