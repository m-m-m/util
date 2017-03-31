/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.function.Supplier;

import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.math.IntegerProperty;
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
 * @since 8.5.0
 */
public abstract class AbstractContainerProperty<V> extends AbstractValueProperty<V> implements ReadableContainerProperty<V> {

  private final GenericType<? extends V> type;

  private IntegerProperty sizeProperty;

  private BooleanProperty emptyProperty;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractContainerProperty(String name, GenericType<? extends V> type, Object bean) {
    this(name, type, bean, (AbstractValidator<? super V>) null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractContainerProperty(String name, GenericType<? extends V> type, Object bean, AbstractValidator<? super V> validator) {
    super(name, bean, validator);
    this.type = type;
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public AbstractContainerProperty(String name, GenericType<? extends V> type, Object bean, Supplier<? extends V> expression) {
    super(name, bean, expression);
    this.type = type;
  }

  @Override
  public GenericType<? extends V> getType() {

    return this.type;
  }

  @Override
  public ReadableIntegerProperty sizeProperty() {

    if (this.sizeProperty == null) {
      this.sizeProperty = new IntegerProperty(getName() + ".size", getBean(), () -> Integer.valueOf(size()));
    }
    return this.sizeProperty;
  }

  @Override
  public ReadableBooleanProperty emptyProperty() {

    if (this.emptyProperty == null) {
      this.emptyProperty = new BooleanProperty(getName() + ".empty", getBean(), () -> Boolean.valueOf(isEmpty()));
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

}
