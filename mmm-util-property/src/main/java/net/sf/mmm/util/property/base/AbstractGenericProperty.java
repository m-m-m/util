/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base;

import java.util.Objects;
import java.util.function.Function;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
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
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the implementation of {@link GenericProperty}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 7.1.0
 */
public abstract class AbstractGenericProperty<VALUE> implements GenericProperty<VALUE> {

  private final Bean bean;

  private final String name;

  private final GenericType<VALUE> type;

  private AbstractValidator<? super VALUE> validator;

  private ValidationFailure validationResult;

  private ReadOnlyPropertyImpl<VALUE> readOnlyProperty;

  private ObservableValue<? extends VALUE> binding;

  private InvalidationListener bindingListener;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractGenericProperty(String name, GenericType<VALUE> type, Bean bean) {
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
  public AbstractGenericProperty(String name, GenericType<VALUE> type, Bean bean,
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

  @Override
  public final VALUE getValue() {

    if (this.binding != null) {
      return this.binding.getValue();
    }
    return doGetValue();
  }

  protected abstract VALUE doGetValue();

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final AbstractGenericProperty<VALUE> copy(Bean newBean) {

    return copy(this.name, newBean, this.validator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final AbstractGenericProperty<VALUE> copy(AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, this.bean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final AbstractGenericProperty<VALUE> copy(Bean newBean, AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, newBean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newName the new {@link #getName() name}.
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final AbstractGenericProperty<VALUE> copy(String newName, Bean newBean) {

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
  public abstract AbstractGenericProperty<VALUE> copy(String newName, Bean newBean,
      AbstractValidator<? super VALUE> newValidator);

  /**
   * Assigns the {@link #getValue() value} from the {@link ObservableValue} (typically a {@link GenericProperty}) to
   * this property. Can be overridden to avoid boxing overhead.
   *
   * @param observableValue the property to assign the value from.
   */
  protected void assignValueFrom(ObservableValue<? extends VALUE> observableValue) {

    setValue(observableValue.getValue());
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
  public void bindBidirectional(Property<VALUE> other) {

    Bindings.bindBidirectional(this, other);
  }

  @Override
  public void unbindBidirectional(Property<VALUE> other) {

    Bindings.unbindBidirectional(this, other);
  }

  @Override
  public Bean getBean() {

    return this.bean;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public GenericType<VALUE> getType() {

    return this.type;
  }

  @Override
  public void setValue(VALUE value) {

    if (isBound()) {
      throw new RuntimeException((getBean() != null && getName() != null
          ? getBean().getClass().getSimpleName() + "." + getName() + " : " : "") + "A bound value cannot be set.");
    }
    boolean changed = doSetValue(value);
    if (changed) {
      markInvalid();
    }
  }

  /**
   * Called from {@link #setValue(Object)}.
   *
   * @param newValue the new {@link #getValue() value} to set.
   * @return <code>true</code> if the {@link #getValue() value} has changed, <code>false</code> otherwise.
   */
  protected abstract boolean doSetValue(VALUE newValue);

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
  protected abstract void fireValueChangedEvent();

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
  public final boolean isReadOnly() {

    return false;
  }

  /**
   * @param <PROPERTY> the generic type of the {@link AbstractGenericProperty}.
   * @param <BUILDER> the generic type of the {@link ObjectValidatorBuilder} for {@literal <VALUE>}.
   * @param factory the {@link Function} to use as factory for the builder.
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  protected <PROPERTY extends AbstractGenericProperty<? extends VALUE>, BUILDER extends ObjectValidatorBuilder<? extends VALUE, PropertyBuilder<PROPERTY>, ?>> BUILDER withValdidator(
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
  public abstract ObjectValidatorBuilder<? extends VALUE, ? extends PropertyBuilder<? extends AbstractGenericProperty<? extends VALUE>>, ?> withValdidator();

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append('\"');
    sb.append(this.name);
    sb.append("\": ");
    VALUE value = getValue();
    if (value == null) {
      sb.append("null");
    } else if ((value instanceof CharSequence) || ((value instanceof Enum))) {
      sb.append('\"');
      sb.append(value);
      sb.append('\"');
    } else {
      sb.append(value);
    }
    return sb.toString();
  }

  /**
   * Implementation of {@link Builder} to {@link #build() build} a copy of the {@link AbstractGenericProperty property}.
   *
   * @param <T> the generic type of the {@link AbstractGenericProperty property}.
   *
   * @author hohwille
   * @since 7.1.0
   */
  public class PropertyBuilder<T extends AbstractGenericProperty<? extends VALUE>> implements Builder<T> {

    private ObjectValidatorBuilder<?, ? extends PropertyBuilder<? extends T>, ?> builder;

    private boolean assignValue;

    private Bean newBean;

    /**
     * The constructor.
     */
    public PropertyBuilder() {
      super();
    }

    /**
     * @param otherBean the new value of {@link AbstractGenericProperty#getBean()}.
     * @return this build instance for fluent API calls.
     */
    public PropertyBuilder<T> withBean(Bean otherBean) {

      this.newBean = otherBean;
      return this;
    }

    /**
     * Also assign the {@link #getValue() value} from the original property. <br/>
     * <b>ATTENTION:</b><br/>
     * A {@link GenericProperty} may hold any kind of {@link #getValue() value} so the value may be mutable. If you are
     * using this builder to create a copy of the property this might have undesired effects.
     *
     * @return this build instance for fluent API calls.
     */
    public PropertyBuilder<T> withValue() {

      this.assignValue = true;
      return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public T build() {

      AbstractValidator<? super VALUE> newValidator = (AbstractValidator) this.builder.build();
      AbstractGenericProperty<VALUE> copy;
      if (this.newBean == null) {
        copy = copy(newValidator);
      } else {
        copy = copy(this.newBean, newValidator);
      }
      if (this.assignValue) {
        copy.assignValueFrom(AbstractGenericProperty.this);
      }
      return (T) copy;
    }
  }

}
