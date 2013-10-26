/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.api.common.RichTextFeature;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterPasswordField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRichTextField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextAreaField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextField;
import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This is the implementation of {@link UiWidgetAdapterTextField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestStringField extends UiWidgetAdapterTestField<String, String> implements
    UiWidgetAdapterTextField, UiWidgetAdapterPasswordField, UiWidgetAdapterTextAreaField,
    UiWidgetAdapterRichTextField {

  /** @see #getKeyboardFilter() */
  private CharFilter keyboardFilter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestStringField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharFilter getKeyboardFilter() {

    verifyNotDisposed();
    return this.keyboardFilter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setKeyboardFilter(CharFilter keyboardFilter) {

    verifyNotDisposed();
    this.keyboardFilter = keyboardFilter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int rows) {

    verifyNotDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAvailableFeatures(RichTextFeature... features) {

    verifyNotDisposed();
  }

}
