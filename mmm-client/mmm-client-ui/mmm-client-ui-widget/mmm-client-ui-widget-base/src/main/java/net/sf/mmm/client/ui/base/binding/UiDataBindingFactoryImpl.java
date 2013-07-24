/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import javax.validation.Validation;
import javax.validation.Validator;

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

  /** @see #getValidator() */
  private Validator validator;

  /**
   * The constructor.
   */
  public UiDataBindingFactoryImpl() {

    super();
  }

  /**
   * @return the {@link Validator} instance.
   */
  protected Validator getValidator() {

    if (this.validator == null) {
      this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    return this.validator;
  }

  /**
   * @param validator is the validator to set
   */
  public void setValidator(Validator validator) {

    getInitializationState().requireNotInitilized();
    this.validator = validator;
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
        datatypeDetector, getValidator());
    if (widget instanceof UiWidgetCustomComposite) {
      return new UiDataBindingPojoComposite<VALUE>(widget, adapter);
    } else {
      return new UiDataBindingPojo<VALUE>(widget, adapter);
    }
  }

}
