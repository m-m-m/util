/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextField;

/**
 * This is the abstract base implementation of {@link UiWidgetTextField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetTextField<ADAPTER extends UiWidgetAdapterTextField<?, String>> extends
    AbstractUiWidgetTextFieldBase<ADAPTER> implements UiWidgetTextField {

  /**
   * The constructor.
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetTextField(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

}
