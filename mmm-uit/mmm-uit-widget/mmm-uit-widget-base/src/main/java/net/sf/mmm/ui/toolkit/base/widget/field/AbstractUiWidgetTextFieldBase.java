/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterTextFieldBase;
import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextField} or
 * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetTextFieldBase<ADAPTER extends UiWidgetAdapterTextFieldBase<?, String>> extends
    AbstractUiWidgetTextualInputField<ADAPTER, String, String> implements AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private CharFilter keyboardFilter;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetTextFieldBase(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.keyboardFilter != null) {
      adapter.setKeyboardFilter(this.keyboardFilter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setKeyboardFilter(CharFilter keyboardFilter) {

    this.keyboardFilter = keyboardFilter;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setKeyboardFilter(keyboardFilter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharFilter getKeyboardFilter() {

    return this.keyboardFilter;
  }

}
