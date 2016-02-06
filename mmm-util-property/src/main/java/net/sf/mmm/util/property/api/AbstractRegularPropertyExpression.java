/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import com.sun.javafx.binding.ExpressionHelper;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the abstract base implementation of regular {@link WritableProperty} that has a {@link #getValue() value}
 * that is dynamically calculated. Here regular means that the {@link #getValue() value} type is not special such as a
 * {@link java.util.Collection}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@SuppressWarnings("restriction")
public abstract class AbstractRegularPropertyExpression<VALUE> extends AbstractProperty<VALUE> {

  private ExpressionHelper<VALUE> helper;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public AbstractRegularPropertyExpression(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public AbstractRegularPropertyExpression(String name, Bean bean, AbstractValidator<? super VALUE> validator) {
    super(name, bean, validator);
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
  public void addListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.addListener(this.helper, this, listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.helper = ExpressionHelper.removeListener(this.helper, listener);
  }

  /**
   * Sends notifications to all attached {@link javafx.beans.InvalidationListener}s and
   * {@link javafx.beans.value.ChangeListener}s.
   *
   * This method is called when the value is changed, either manually or in case of a bound property, if the binding
   * becomes invalid.
   */
  @Override
  protected void fireValueChangedEvent() {

    ExpressionHelper.fireValueChangedEvent(this.helper);
  }

  @Override
  public void setValue(VALUE value) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public void bind(ObservableValue<? extends VALUE> observable) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public void unbind() {

  }

  @Override
  public boolean isBound() {

    return false;
  }

  @Override
  public void bindBidirectional(Property<VALUE> other) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public void unbindBidirectional(Property<VALUE> other) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public boolean isReadOnly() {

    return true;
  }

  @Override
  public WritableProperty<VALUE> getReadOnly() {

    return this;
  }

  @Override
  public ValidationFailure validate() {

    AbstractValidator<? super VALUE> validator = getValidator();
    if (validator == ValidatorNone.getInstance()) {
      return null;
    }
    return validator.validate(getValue());
  }

  @Override
  public boolean isValid() {

    return validate() == null;
  }

}
