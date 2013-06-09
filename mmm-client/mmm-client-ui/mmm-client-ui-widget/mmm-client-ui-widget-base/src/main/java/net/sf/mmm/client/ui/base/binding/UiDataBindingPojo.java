/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBinding} for any
 * {@link net.sf.mmm.util.pojo.api.Pojo POJO} (no {@link net.sf.mmm.util.lang.api.Datatype}).
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingPojo<VALUE> extends AbstractUiDataBinding<VALUE> {

  private final PojoDescriptor<VALUE> descriptor;

  /**
   * The constructor.
   * 
   * @param widget
   */
  public UiDataBindingPojo(AbstractUiWidget<VALUE> widget) {

    super(widget);
    PojoDescriptorBuilderFactory factory = widget.getContext().getContainer().get(PojoDescriptorBuilderFactory.class);
    PojoDescriptorBuilder descriptorBuilder = factory.createPublicMethodDescriptorBuilder();
    this.descriptor = descriptorBuilder.getDescriptor(AbstractUiWidget.AccessHelper.getValueClass(widget));
  }

  /**
   * @return the descriptor
   */
  public PojoDescriptor<VALUE> getDescriptor() {

    return this.descriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createNewValue() {

    // TODO
    // return this.valueDescriptor.newInstance();
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createCopyOfValue(VALUE value) {

    return null;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected VALUE createCopyOfValue(VALUE value) {
  //
  // if (value instanceof AbstractTransferObject) {
  // AbstractTransferObject to = (AbstractTransferObject) value;
  // TransferObjectUtilLimited transferObjectUtil =
  // this.context.getContainer().get(TransferObjectUtilLimited.class);
  // return (VALUE) transferObjectUtil.copy(to);
  // }
  // return super.createCopyOfValue(value);
  // }

}
