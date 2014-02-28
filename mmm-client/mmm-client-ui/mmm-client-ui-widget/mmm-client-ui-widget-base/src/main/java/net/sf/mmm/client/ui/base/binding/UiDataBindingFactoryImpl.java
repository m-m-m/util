/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Validation;
import javax.validation.Validator;

import net.sf.mmm.client.ui.api.binding.DatatypeDetector;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomComposite;
import net.sf.mmm.util.math.api.MathUtilLimited;
import net.sf.mmm.util.math.base.MathUtilLimitedImpl;
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

  /** @see #getMathUtil() */
  private MathUtilLimited mathUtil;

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
   * @return the instance of {@link MathUtilLimited}.
   */
  protected MathUtilLimited getMathUtil() {

    return this.mathUtil;
  }

  /**
   * @param mathUtil is the instance of {@link MathUtilLimited} to {@link Inject}.
   */
  @Inject
  public void setMathUtil(MathUtilLimited mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.mathUtil == null) {
      MathUtilLimitedImpl impl = new MathUtilLimitedImpl();
      impl.initialize();
      this.mathUtil = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiContext context, Class<VALUE> valueType) {

    return createDataBinding(context, null, valueType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiWidget<VALUE> widget, Class<VALUE> valueType) {

    return createDataBinding(widget.getContext(), widget, valueType);
  }

  /**
   * @see #createDataBinding(AbstractUiWidget, Class)
   * 
   * @param <VALUE> is the generic type of the <code>valueType</code>.
   * 
   * @param context is the {@link AbstractUiContext}.
   * @param widget is the widget the {@link UiDataBinding} is associated with. May be <code>null</code> if
   *        called from {@link #createDataBinding(AbstractUiWidget, Class)}.
   * @param valueType is the {@link Class} reflecting the type of the value.
   * @return the new {@link UiDataBinding}.
   */
  @SuppressWarnings("unchecked")
  protected <VALUE> UiDataBinding<VALUE> createDataBinding(AbstractUiContext context, AbstractUiWidget<VALUE> widget,
      Class<VALUE> valueType) {

    if ((valueType == null) || (valueType == Void.class)) {
      return (UiDataBinding<VALUE>) UiDataBindingNone.getInstance();
    }
    if (valueType == List.class) {
      return (UiDataBinding<VALUE>) new UiDataBindingList(widget);
    }
    DatatypeDetector datatypeDetector = context.getDatatypeDetector();
    if ((widget != null) && datatypeDetector.isDatatype(valueType)) {
      return new UiDataBindingDatatype<VALUE>(widget);
    }
    PojoDescriptorBuilderFactory descriptorBuilderFactory = context.getContainer().get(
        PojoDescriptorBuilderFactory.class);
    UiDataBindingAdapter<VALUE> adapter = new UiDataBindingAdapterImpl<VALUE>(valueType, descriptorBuilderFactory,
        datatypeDetector, getValidator(), this.mathUtil);
    if (widget instanceof UiWidgetCustomComposite) {
      return new UiDataBindingPojoComposite<VALUE>(widget, adapter);
    } else {
      return new UiDataBindingPojo<VALUE>(widget, adapter);
    }
  }
}
