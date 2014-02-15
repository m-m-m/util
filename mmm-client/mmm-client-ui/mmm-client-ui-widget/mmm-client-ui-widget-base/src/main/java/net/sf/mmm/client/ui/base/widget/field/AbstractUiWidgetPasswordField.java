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
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetPasswordField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<String> getValueClass() {

    return String.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String convertValueToString(String value) {

    // Security: Never display password as plain text
    if ((value == null) || (value.length() == 0)) {
      return "";
    } else {
      char[] chars = new char[value.length()];
      for (int i = 0; i < chars.length; i++) {
        chars[i] = '*';
      }
      return new String(chars);
    }
  }

}
