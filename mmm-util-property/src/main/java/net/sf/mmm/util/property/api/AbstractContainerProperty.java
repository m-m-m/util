/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the abstract base implementation of {@link WritableProperty} and {@link ReadableContainerProperty}.
 *
 * @param <VALUE> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractContainerProperty<VALUE> extends AbstractGenericProperty<VALUE>
    implements ReadableContainerProperty<VALUE> {

  private SizeProperty sizeProperty;

  private EmptyProperty emptyProperty;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractContainerProperty(String name, GenericType<VALUE> type, Bean bean) {
    super(name, type, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractContainerProperty(String name, GenericType<VALUE> type, Bean bean,
      AbstractValidator<? super VALUE> validator) {
    super(name, type, bean, validator);
  }

  @Override
  public ReadableIntegerProperty sizeProperty() {

    if (this.sizeProperty == null) {
      this.sizeProperty = new SizeProperty();
    }
    return this.sizeProperty;
  }

  @Override
  public ReadableBooleanProperty emptyProperty() {

    if (this.emptyProperty == null) {
      this.emptyProperty = new EmptyProperty();
    }
    return this.emptyProperty;
  }

  /**
   * Invalidates internal properties such as {@link #sizeProperty()} and {@link #emptyProperty()}.
   */
  protected void invalidateProperties() {

    if (this.sizeProperty != null) {
      this.sizeProperty.fireValueChangedEvent();
    }
    if (this.emptyProperty != null) {
      this.emptyProperty.fireValueChangedEvent();
    }
  }

  private class SizeProperty extends IntegerPropertyExpression {
    @Override
    public Integer getValue() {

      return Integer.valueOf(size());
    }

    @Override
    public Bean getBean() {

      return AbstractContainerProperty.this.getBean();
    }

    @Override
    public String getName() {

      return AbstractContainerProperty.this.getName() + ".size";
    }

    @Override
    protected void fireValueChangedEvent() {

      super.fireValueChangedEvent();
    }
  }

  private class EmptyProperty extends BooleanPropertyExpression {

    @Override
    public Boolean getValue() {

      return Boolean.valueOf(isEmpty());
    }

    @Override
    public Bean getBean() {

      return AbstractContainerProperty.this.getBean();
    }

    @Override
    public String getName() {

      return AbstractContainerProperty.this.getName() + ".empty";
    }

    @Override
    protected void fireValueChangedEvent() {

      super.fireValueChangedEvent();
    }
  }

}
