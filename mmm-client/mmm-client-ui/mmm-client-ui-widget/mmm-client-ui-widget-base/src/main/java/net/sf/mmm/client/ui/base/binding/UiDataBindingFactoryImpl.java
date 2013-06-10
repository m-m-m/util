/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.Date;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;

/**
 * This is the (default) implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBindingFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingFactoryImpl extends AbstractUiDataBindingFactory implements DatatypeDetector {

  /**
   * The constructor.
   */
  public UiDataBindingFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDatatype(Class<?> type) {

    if (type == String.class) {
      return true;
    }
    if (type == Long.class) {
      return true;
    }
    if (type == Double.class) {
      return true;
    }
    if (type == Boolean.class) {
      return true;
    }
    if (type == Integer.class) {
      return true;
    }
    if (type == Date.class) {
      return true;
    }
    if (type == Float.class) {
      return true;
    }
    if (type == Short.class) {
      return true;
    }
    if (type == Byte.class) {
      return true;
    }
    if (type == Character.class) {
      return true;
    }
    // TODO: not possible for GWT...
    // if (Datatype.class.isAssignableFrom(type)) {
    // return true;
    // }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiWidget<VALUE> widget) {

    Class<VALUE> valueType = AbstractUiWidget.AccessHelper.getValueClass(widget);
    if ((valueType == null) || (valueType == Void.class)) {
      return (UiDataBinding<VALUE>) UiDataBindingNone.getInstance();
    }
    if (isDatatype(valueType)) {
      return new UiDataBindingDatatype<VALUE>(widget);
    }
    PojoDescriptorBuilderFactory descriptorBuilderFactory = widget.getContext().getContainer()
        .get(PojoDescriptorBuilderFactory.class);
    UiDataBindingAdapter<VALUE> adapter = new UiDataBindingAdapterImpl<VALUE>(valueType, descriptorBuilderFactory, this);
    if (widget instanceof UiWidgetCustomComposite) {
      return new UiDataBindingPojoComposite<VALUE>(widget, adapter);
    } else {
      return new UiDataBindingPojo<VALUE>(widget, adapter);
    }
  }

}
