/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Objects;
import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.lang.api.Builder;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the implementation of {@link WritableProperty}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractProperty<VALUE> implements WritableProperty<VALUE>, Cloneable {

  private String name;

  private Bean bean;

  private AbstractValidator<? super VALUE> validator;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractProperty(String name, Bean bean) {
    this(name, bean, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractProperty(String name, Bean bean, AbstractValidator<? super VALUE> validator) {
    super();
    this.name = name;
    this.bean = bean;
    if (validator == null) {
      this.validator = ValidatorNone.getInstance();
    } else {
      this.validator = validator;
    }
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public Bean getBean() {

    return this.bean;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected AbstractProperty<VALUE> clone() {

    try {
      return (AbstractProperty<VALUE>) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @return a new empty instance of this property.
   */
  protected AbstractProperty<VALUE> copy() {

    return clone();
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final AbstractProperty<VALUE> copy(Bean newBean) {

    return copy(this.name, newBean, this.validator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final AbstractProperty<VALUE> copy(AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, this.bean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newBean the new {@link #getBean() bean}.
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  public final AbstractProperty<VALUE> copy(Bean newBean, AbstractValidator<? super VALUE> newValidator) {

    return copy(this.name, newBean, newValidator);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newName the new {@link #getName() name}.
   * @param newBean the new {@link #getBean() bean}.
   * @return the new property instance.
   */
  public final AbstractProperty<VALUE> copy(String newName, Bean newBean) {

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
  public final AbstractProperty<VALUE> copy(String newName, Bean newBean,
      AbstractValidator<? super VALUE> newValidator) {

    AbstractProperty<VALUE> copy = clone();
    copy.name = newName;
    copy.bean = newBean;
    copy.validator = newValidator;
    return copy;
  }

  /**
   * Assigns the {@link #getValue() value} from the {@link ObservableValue} (typically a {@link WritableProperty}) to
   * this property. Can be overridden to avoid boxing overhead.
   *
   * @param observableValue the property to assign the value from.
   */
  protected void assignValueFrom(ObservableValue<? extends VALUE> observableValue) {

    setValue(observableValue.getValue());
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
  public final AbstractValidator<? super VALUE> getValidator() {

    return this.validator;
  }

  @Override
  public final boolean isMandatory() {

    return this.validator.isMandatory();
  }

  /**
   * @param <PROPERTY> the generic type of the {@link AbstractProperty}.
   * @param <BUILDER> the generic type of the {@link ObjectValidatorBuilder} for {@literal <VALUE>}.
   * @param factory the {@link Function} to use as factory for the builder.
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  protected <PROPERTY extends AbstractProperty<? extends VALUE>, BUILDER extends ObjectValidatorBuilder<? extends VALUE, PropertyBuilder<PROPERTY>, ?>> BUILDER withValdidator(
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
  public abstract ObjectValidatorBuilder<? extends VALUE, ? extends PropertyBuilder<? extends AbstractProperty<? extends VALUE>>, ?> withValdidator();

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append('\"');
    sb.append(getName());
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
   * Implementation of {@link Builder} to {@link #build() build} a copy of the {@link AbstractProperty property}.
   *
   * @param <T> the generic type of the {@link AbstractProperty property}.
   */
  public class PropertyBuilder<T extends AbstractProperty<? extends VALUE>> implements Builder<T> {

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
     * @param otherBean the new value of {@link AbstractProperty#getBean()}.
     * @return this build instance for fluent API calls.
     */
    public PropertyBuilder<T> withBean(Bean otherBean) {

      this.newBean = otherBean;
      return this;
    }

    /**
     * Also assign the {@link #getValue() value} from the original property. <br/>
     * <b>ATTENTION:</b><br/>
     * A {@link WritableProperty} may hold any kind of {@link #getValue() value} so the value may be mutable. If you are
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
      AbstractProperty<VALUE> copy;
      if (this.newBean == null) {
        copy = copy(newValidator);
      } else {
        copy = copy(this.newBean, newValidator);
      }
      if (this.assignValue) {
        copy.assignValueFrom(AbstractProperty.this);
      }
      return (T) copy;
    }
  }

  @Override
  public int hashCode() {

    return Objects.hash(getClass(), this.name, this.validator);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractProperty<?> other = (AbstractProperty<?>) obj;
    // if (this.bean != other.bean) {
    // return false;
    // }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.validator, other.validator)) {
      return false;
    }
    return true;
  }

}
