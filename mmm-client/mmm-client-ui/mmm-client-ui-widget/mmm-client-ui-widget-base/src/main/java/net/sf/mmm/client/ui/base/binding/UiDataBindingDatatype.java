/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBinding} for an immutable
 * {@link net.sf.mmm.util.lang.api.Datatype}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingDatatype<VALUE> extends AbstractUiDataBinding<VALUE> {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget} to bind.
   */
  public UiDataBindingDatatype(AbstractUiWidget<VALUE> widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createNewValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createCopyOfValue(VALUE value) {

    return null;
  }

}
