/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Objects;
import java.util.function.Supplier;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidationFailure;
import net.sf.mmm.util.validation.base.ValidationFailureSuccess;

/**
 * This is the implementation of {@link WritableProperty}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractValueProperty<V> extends AbstractProperty<V> {

  private ValidationFailure validationResult;

  private boolean readOnly;

  private AbstractValueProperty<V> readOnlyProperty;

  private ObservableValue<? extends V> binding;

  private InvalidationListener bindingListener;

  private Supplier<? extends V> expression;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractValueProperty(String name, Bean bean) {
    this(name, bean, (AbstractValidator<? super V>) null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractValueProperty(String name, Bean bean, AbstractValidator<? super V> validator) {
    super(name, bean, validator);
    this.readOnly = false;
    this.expression = null;
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public AbstractValueProperty(String name, Bean bean, Supplier<? extends V> expression) {
    super(name, bean);
    this.readOnly = true;
    this.expression = expression;
  }

  @Override
  protected AbstractValueProperty<V> copy() {

    AbstractValueProperty<V> copy = (AbstractValueProperty<V>) super.copy();
    copy.binding = null;
    copy.bindingListener = null;
    copy.readOnlyProperty = null;
    copy.validationResult = null;
    return copy;
  }

  @Override
  public final V getValue() {

    if (this.expression != null) {
      return this.expression.get();
    }
    if (this.binding != null) {
      return this.binding.getValue();
    }
    return doGetValue();
  }

  /**
   * @return the internal {@link #getValue() value}.
   */
  protected abstract V doGetValue();

  @Override
  public void bind(ObservableValue<? extends V> observable) {

    Objects.requireNonNull(observable, "observable");
    if (!observable.equals(this.binding)) {
      requireWritable();
      unbind();
      bindInternal(observable);
    }
  }

  void bindInternal(ObservableValue<? extends V> observable) {

    this.binding = observable;
    if (this.bindingListener == null) {
      this.bindingListener = new BindingInvalidationListener(this);
    }
    observable.addListener(this.bindingListener);
    markInvalid();
  }

  @Override
  public void unbind() {

    if (this.binding != null) {
      requireWritable();
      assignValueFrom(this.binding);
      this.binding.removeListener(this.bindingListener);
      this.binding = null;
      this.bindingListener = null;
    }
  }

  @Override
  public boolean isBound() {

    return (this.binding != null);
  }

  @Override
  public void bindBidirectional(Property<V> other) {

    Bindings.bindBidirectional(this, other);
  }

  @Override
  public void unbindBidirectional(Property<V> other) {

    Bindings.unbindBidirectional(this, other);
  }

  @Override
  public void setValue(V value) {

    if (isBound()) {
      throw new RuntimeException(
          (getBean() != null && getName() != null ? getBean().getClass().getSimpleName() + "." + getName() + " : " : "")
              + "A bound value cannot be set.");
    }
    requireWritable();
    boolean changed = doSetValue(value);
    if (changed) {
      markInvalid();
    }
  }

  /**
   * @throws ReadOnlyException if this property is {@link #isReadOnly() read-only}.
   */
  protected final void requireWritable() throws ReadOnlyException {

    if (isReadOnly()) {
      throw new ReadOnlyException(getBean(), getName());
    }
  }

  /**
   * Called from {@link #setValue(Object)}.
   *
   * @param newValue the new {@link #getValue() value} to set.
   * @return {@code true} if the {@link #getValue() value} has changed, {@code false} otherwise.
   */
  protected abstract boolean doSetValue(V newValue);

  /**
   * Invalidates this property.
   */
  protected void markInvalid() {

    if (this.validationResult != null) {
      this.validationResult = null;
      invalidated();
    }
    fireValueChangedEvent();
  }

  /**
   * May be overridden to receive invalidation notifications.
   */
  protected void invalidated() {

    // nothing by default...
  }

  @Override
  public boolean isValid() {

    if (this.validationResult == null) {
      validate();
    }
    return (this.validationResult == ValidationFailureSuccess.INSTANCE);
  }

  /**
   * Clears the cached internal {@link #validate() validation} result. Has to be called if the {@link #getValue() value}
   * has changed (from an external call).
   */
  protected void clearValidationResult() {

    this.validationResult = null;
  }

  @Override
  public ValidationFailure validate() {

    if (this.validationResult == null) {
      V v = getValue();
      String source = getName();
      ValidationFailure failure = getValidator().validate(v, source);
      if (v instanceof Bean) {
        ValidationFailure failure2 = ((Bean) v).access().validate();
        if (failure == null) {
          failure = failure2;
        } else if (failure2 != null) {
          failure = new ComposedValidationFailure(source, new ValidationFailure[] { failure, failure2 });
        }
      }
      if (failure == null) {
        failure = ValidationFailureSuccess.INSTANCE;
      }
      this.validationResult = failure;
    }
    if (this.validationResult == ValidationFailureSuccess.INSTANCE) {
      return null;
    }
    return this.validationResult;
  }

  @Override
  public WritableProperty<V> getReadOnly() {

    if (isReadOnly()) {
      return this;
    }
    if (this.readOnlyProperty == null) {
      AbstractValueProperty<V> copy = copy();
      copy.binding = null;
      copy.bindInternal(this);
      copy.readOnly = true;
      this.readOnlyProperty = copy;
    }
    return this.readOnlyProperty;
  }

  @Override
  public final boolean isReadOnly() {

    return this.readOnly;
  }

  @Override
  public int hashCode() {

    return Objects.hash(getClass(), getName());
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    AbstractValueProperty<?> other = (AbstractValueProperty<?>) obj;
    if (!Objects.equals(this.binding, other.binding)) {
      return false;
    }
    if (!Objects.equals(getValue(), other.getValue())) {
      return false;
    }
    return true;
  }

}
