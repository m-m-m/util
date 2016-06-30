/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.lang.BooleanPropertyExpression;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.math.IntegerPropertyExpression;
import net.sf.mmm.util.property.api.math.ReadableIntegerProperty;
import net.sf.mmm.util.property.api.util.ReadableContainerProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the abstract base implementation of {@link WritableProperty} and {@link ReadableContainerProperty}.
 *
 * @param <V> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractContainerProperty<V> extends AbstractValueProperty<V>
    implements ReadableContainerProperty<V> {

  private final GenericType<? extends V> type;

  private SizeProperty sizeProperty;

  private EmptyProperty emptyProperty;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractContainerProperty(String name, GenericType<? extends V> type, Bean bean) {
    this(name, type, bean, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractContainerProperty(String name, GenericType<? extends V> type, Bean bean,
      AbstractValidator<? super V> validator) {
    super(name, bean, validator);
    this.type = type;
  }

  @Override
  public GenericType<? extends V> getType() {

    return this.type;
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

    /**
     * The constructor.
     */
    public SizeProperty() {
      super(AbstractContainerProperty.this.getName() + ".size", AbstractContainerProperty.this.getBean());
    }

    @Override
    public Integer getValue() {

      return Integer.valueOf(size());
    }

    @Override
    protected void fireValueChangedEvent() {

      super.fireValueChangedEvent();
    }
  }

  private class EmptyProperty extends BooleanPropertyExpression {

    /**
     * The constructor.
     */
    public EmptyProperty() {
      super(AbstractContainerProperty.this.getName() + ".empty", AbstractContainerProperty.this.getBean());
    }

    @Override
    public Boolean getValue() {

      return Boolean.valueOf(isEmpty());
    }

    @Override
    protected void fireValueChangedEvent() {

      super.fireValueChangedEvent();
    }
  }

}
