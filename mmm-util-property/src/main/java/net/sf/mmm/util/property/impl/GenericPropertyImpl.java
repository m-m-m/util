/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the implementation of {@link GenericProperty}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("restriction")
public class GenericPropertyImpl<VALUE> implements GenericProperty<VALUE> {

  private final Bean bean;

  private final String name;

  private final GenericType<VALUE> type;

  private AbstractValidator<? super VALUE> validator;

  private VALUE value;

  private ObservableValue<? extends VALUE> binding;

  private InvalidationListener bindingListener;

  private ExpressionHelper<VALUE> helper;

  private ReadOnlyPropertyImpl<VALUE> readOnlyProperty;

  private ValidationFailure validationResult;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   */
  public GenericPropertyImpl(String name, GenericType<VALUE> type) {
    this(name, type, null, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param validator - see {@link #validate()}.
   */
  public GenericPropertyImpl(String name, GenericType<VALUE> type, AbstractValidator<? super VALUE> validator) {
    this(name, type, null, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public GenericPropertyImpl(String name, GenericType<VALUE> type, Bean bean) {
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
  public GenericPropertyImpl(String name, GenericType<VALUE> type, Bean bean,
      AbstractValidator<? super VALUE> validator) {
    super();
    this.name = name;
    this.type = type;
    this.bean = bean;
    if (validator == null) {
      this.validator = ValidatorNone.getInstance();
    } else {
      this.validator = validator;
    }
  }

  /**
   * Creates a new empty instance of this class for the given {@link Bean}.
   *
   * @param newBean the bean to create this property for.
   * @return the new property instance.
   */
  public GenericPropertyImpl<VALUE> createFor(Bean newBean) {

    return new GenericPropertyImpl<>(this.name, this.type, newBean, this.validator);
  }

  /**
   * @return the {@link AbstractValidator}.
   */
  public AbstractValidator<? super VALUE> getValidator() {

    return this.validator;
  }

  @Override
  public void bind(ObservableValue<? extends VALUE> observable) {

    Objects.requireNonNull(observable, "observable");
    if (!observable.equals(this.binding)) {
      unbind();
      this.binding = observable;
      if (this.bindingListener == null) {
        this.bindingListener = new BindingInvalidationListener(this);
      }
      observable.addListener(this.bindingListener);
      markInvalid();
    }
  }

  @Override
  public void unbind() {

    if (this.binding != null) {

      this.value = this.binding.getValue();
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
  public void bindBidirectional(Property<VALUE> other) {

    Bindings.bindBidirectional(this, other);
  }

  @Override
  public void unbindBidirectional(Property<VALUE> other) {

    Bindings.unbindBidirectional(this, other);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Bean getBean() {

    return this.bean;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public void addListener(ChangeListener<? super VALUE> listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(ChangeListener<? super VALUE> listener) {

    this.helper = ExpressionHelper.removeListener(this.helper, listener);
  }

  @Override
  public VALUE getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.removeListener(this.helper, listener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(VALUE value) {

    markInvalid();
    this.value = value;
  }

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

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener}s and
   * {@link javafx.beans.value.ChangeListener}s.
   *
   * This method is called when the value is changed, either manually by calling {@link #setValue(Object)} or in case of
   * a bound property, if the binding becomes invalid.
   */
  protected void fireValueChangedEvent() {

    ExpressionHelper.fireValueChangedEvent(this.helper);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<VALUE> getType() {

    return this.type;
  }

  @Override
  public boolean isMandatory() {

    return this.validator.isMandatory();
  }

  @Override
  public boolean isValid() {

    if (this.validationResult == null) {
      validate();
    }
    return (this.validationResult == ValidationFailureSuccess.INSTANCE);
  }

  @Override
  public ValidationFailure validate() {

    if (this.validationResult == null) {
      ValidationFailure failure = this.validator.validate(getValue(), this);
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
  public GenericProperty<VALUE> getReadOnly() {

    if (this.readOnlyProperty == null) {
      this.readOnlyProperty = new ReadOnlyPropertyImpl<>(this);
    }
    return this.readOnlyProperty;
  }

  @Override
  public boolean isReadOnly() {

    return false;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append('\"');
    sb.append(this.name);
    sb.append("\": ");
    if (this.value == null) {
      sb.append("null");
    } else if (this.value instanceof CharSequence) {
      sb.append('\"');
      sb.append(this.value);
      sb.append('\"');
    } else {
      sb.append(this.value);
    }
    return sb.toString();
  }

}
