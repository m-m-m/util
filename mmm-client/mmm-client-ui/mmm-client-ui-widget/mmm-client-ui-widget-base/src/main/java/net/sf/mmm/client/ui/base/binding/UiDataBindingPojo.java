/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

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

  /** @see #getAdapter() */
  private final UiDataBindingAdapter<VALUE> adapter;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget} to bind.
   * @param adapter is the {@link UiDataBindingAdapter}.
   */
  public UiDataBindingPojo(AbstractUiWidget<VALUE> widget, UiDataBindingAdapter<VALUE> adapter) {

    super(widget);
    this.adapter = adapter;
  }

  /**
   * @return the {@link UiDataBindingAdapter}.
   */
  public UiDataBindingAdapter<VALUE> getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyAccessor<VALUE, ?> createPropertyAccessor(final String property) {

    PropertyAccessor<VALUE, ?> accessor = new PropertyAccessor<VALUE, Object>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public Object getValue(VALUE element) {

        return UiDataBindingPojo.this.adapter.getPropertyValue(element, property);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void setValue(VALUE element, Object value) {

        UiDataBindingPojo.this.adapter.setPropertyValue(element, property, value);
      }

    };
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> PropertyAccessor<VALUE, P> createPropertyAccessor(final TypedProperty<P> property) {

    PropertyAccessor<VALUE, P> accessor = new PropertyAccessor<VALUE, P>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public P getValue(VALUE element) {

        return UiDataBindingPojo.this.adapter.getPropertyValue(element, property);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void setValue(VALUE element, P value) {

        UiDataBindingPojo.this.adapter.setPropertyValue(element, property, value);
      }

    };
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createNewValue() {

    return this.adapter.createNewValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE createCopyOfValue(VALUE value) {

    return this.adapter.copy(value);
  }

}
