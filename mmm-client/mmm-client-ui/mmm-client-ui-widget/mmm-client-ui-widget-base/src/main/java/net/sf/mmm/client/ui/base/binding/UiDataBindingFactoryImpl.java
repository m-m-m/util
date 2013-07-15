/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.base.AbstractUiContextImpl;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;

/**
 * This is the (default) implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBindingFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingFactoryImpl extends AbstractUiDataBindingFactory {

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
  public <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiWidget<VALUE> widget) {

    Class<VALUE> valueType = AbstractUiWidget.AccessHelper.getValueClass(widget);
    if ((valueType == null) || (valueType == Void.class)) {
      return (UiDataBinding<VALUE>) UiDataBindingNone.getInstance();
    }
    AbstractUiContextImpl context = (AbstractUiContextImpl) widget.getContext();
    DatatypeDetector datatypeDetector = context.getDatatypeDetector();
    if (datatypeDetector.isDatatype(valueType)) {
      return new UiDataBindingDatatype<VALUE>(widget);
    }
    PojoDescriptorBuilderFactory descriptorBuilderFactory = context.getContainer().get(
        PojoDescriptorBuilderFactory.class);
    UiDataBindingAdapter<VALUE> adapter = new UiDataBindingAdapterImpl<VALUE>(valueType, descriptorBuilderFactory,
        datatypeDetector);
    if (widget instanceof UiWidgetCustomComposite) {
      return new UiDataBindingPojoComposite<VALUE>(widget, adapter);
    } else {
      return new UiDataBindingPojo<VALUE>(widget, adapter);
    }
  }

}
