/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBinding} for the type
 * {@link List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UiDataBindingList extends AbstractUiDataBinding<List> {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget} to bind.
   */
  public UiDataBindingList(AbstractUiWidget widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List createNewValue() {

    return new ArrayList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List createCopyOfValue(List value) {

    return new ArrayList(value);
  }

}
