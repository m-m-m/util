/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the factory used to create instances of {@link UiDataBinding}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface UiDataBindingFactory {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.client.ui.api.binding.UiDataBindingFactory";

  /**
   * This method creates a new instance of {@link UiDataBinding} for the given <code>valueType</code> and
   * <code>widget</code>.
   * 
   * @param <VALUE> is the generic type of the <code>valueType</code>.
   * 
   * @param widget is the widget the {@link UiDataBinding} is associated with.
   * @param valueType is the {@link Class} reflecting the type of the value.
   * @return the new {@link UiDataBinding}.
   */
  <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiWidget<?> widget, Class<VALUE> valueType);

}
