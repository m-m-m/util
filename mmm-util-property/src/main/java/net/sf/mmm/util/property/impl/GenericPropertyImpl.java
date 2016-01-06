/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;
import java.util.function.Function;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.lang.api.Builder;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidationFailure;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidationFailureSuccess;
import net.sf.mmm.util.validation.base.ValidatorBuilderObject;
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
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final GenericPropertyImpl<VALUE> copy(Bean newBean) {

    return copy(this.name, newBean, this.validator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final GenericPropertyImpl<VALUE> copy(AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, this.bean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final GenericPropertyImpl<VALUE> copy(Bean newBean, AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, newBean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newName the new {@link #getName() name}.
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final GenericPropertyImpl<VALUE> copy(String newName, Bean newBean) {

    return copy(newName, newBean, this.validator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newName the new {@link #getName() name}.
   * @param newBean the new {@link #getBean() bean}.
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public GenericPropertyImpl<VALUE> copy(String newName, Bean newBean,
      AbstractValidator<? super VALUE> newValidator) {

    return new GenericPropertyImpl<>(newName, this.type, newBean, newValidator);
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

  /**
   * @return the {@link AbstractValidator}.
   */
  public AbstractValidator<? super VALUE> getValidator() {

    return this.validator;
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
      VALUE v = getValue();
      String source = getName();
      ValidationFailure failure = this.validator.validate(v, source);
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

  /**
   * @param <PROPERTY> the generic type of the {@link GenericPropertyImpl}.
   * @param <BUILDER> the generic type of the {@link ObjectValidatorBuilder} for {@literal <VALUE>}.
   * @param factory the {@link Function} to use as factory for the builder.
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  protected <PROPERTY extends GenericPropertyImpl<? extends VALUE>, BUILDER extends ObjectValidatorBuilder<?, PropertyBuilder<PROPERTY>, ?>> BUILDER withValdidator(
      Function<PropertyBuilder<PROPERTY>, BUILDER> factory) {

    PropertyBuilder<PROPERTY> parentBuilder = new PropertyBuilder<>();
    BUILDER builder = factory.apply(parentBuilder);
    parentBuilder.builder = builder;
    return builder;
  }

  /**
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  public ObjectValidatorBuilder<?, ? extends PropertyBuilder<? extends GenericPropertyImpl<? extends VALUE>>, ?> withValdidator() {

    Function<PropertyBuilder<GenericPropertyImpl<VALUE>>, ObjectValidatorBuilder<VALUE, PropertyBuilder<GenericPropertyImpl<VALUE>>, ?>> factory = new Function<PropertyBuilder<GenericPropertyImpl<VALUE>>, ObjectValidatorBuilder<VALUE, PropertyBuilder<GenericPropertyImpl<VALUE>>, ?>>() {

      @SuppressWarnings({ "unchecked", "rawtypes" })
      public ObjectValidatorBuilder<VALUE, PropertyBuilder<GenericPropertyImpl<VALUE>>, ?> apply(
          PropertyBuilder<GenericPropertyImpl<VALUE>> parent) {

        return new ValidatorBuilderObject(parent);
      }
    };
    return withValdidator(factory);
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

  /**
   * Implementation of {@link Builder} to {@link #build() build} a copy of the {@link GenericPropertyImpl property}.
   *
   * @param <T> the generic type of the {@link GenericPropertyImpl property}.
   *
   * @author hohwille
   * @since 7.1.0
   */
  public class PropertyBuilder<T extends GenericPropertyImpl<? extends VALUE>> implements Builder<T> {

    private ObjectValidatorBuilder<?, ? extends PropertyBuilder<? extends T>, ?> builder;

    private boolean copyValue;

    private Bean newBean;

    /**
     * The constructor.
     */
    public PropertyBuilder() {
      super();
    }

    /**
     * @param otherBean the new value of {@link GenericPropertyImpl#getBean()}.
     * @return this build instance for fluent API calls.
     */
    public PropertyBuilder<T> withBean(Bean otherBean) {

      this.newBean = otherBean;
      return this;
    }

    /**
     * Also copy the {@link GenericPropertyImpl#getValue() value} from the original property.
     *
     * @return this build instance for fluent API calls.
     */
    public PropertyBuilder<T> withValue() {

      this.copyValue = true;
      return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public T build() {

      AbstractValidator<? super VALUE> newValidator = (AbstractValidator) this.builder.build();
      GenericPropertyImpl<VALUE> copy;
      if (this.newBean == null) {
        copy = copy(newValidator);
      } else {
        copy = copy(this.newBean, newValidator);
      }
      if (this.copyValue) {
        copy.setValue(GenericPropertyImpl.this.value);
      }
      return (T) copy;
    }
  }

}
