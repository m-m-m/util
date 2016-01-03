/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is a {@link #isReadOnly() read-only} view on a {@link GenericProperty}.
 *
 * @see #getReadOnly()
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 7.1.0
 */
public class ReadOnlyPropertyImpl<VALUE> implements GenericProperty<VALUE> {

  private final GenericProperty<VALUE> property;

  /**
   * The constructor.
   *
   * @param property the {@link GenericProperty} to give {@link #isReadOnly() read-only} access to.
   */
  public ReadOnlyPropertyImpl(GenericProperty<VALUE> property) {
    super();
    this.property = property;
  }

  @Override
  public GenericProperty<VALUE> getReadOnly() {

    return this;
  }

  @Override
  public boolean isReadOnly() {

    return true;
  }

  @Override
  public GenericType<VALUE> getType() {

    return this.property.getType();
  }

  @Override
  public void bind(ObservableValue<? extends VALUE> observable) {

    this.property.bind(observable);
  }

  @Override
  public void unbind() {

    this.property.unbind();
  }

  @Override
  public boolean isBound() {

    return this.property.isBound();
  }

  @Override
  public void bindBidirectional(Property<VALUE> other) {

    this.property.bindBidirectional(other);
  }

  @Override
  public void unbindBidirectional(Property<VALUE> other) {

    this.property.unbindBidirectional(other);
  }

  @Override
  public Bean getBean() {

    return this.property.getBean();
  }

  @Override
  public String getName() {

    return this.property.getName();
  }

  @Override
  public void addListener(ChangeListener<? super VALUE> listener) {

    this.property.addListener(listener);
  }

  @Override
  public void removeListener(ChangeListener<? super VALUE> listener) {

    this.property.removeListener(listener);
  }

  @Override
  public VALUE getValue() {

    return this.property.getValue();
  }

  @Override
  public void addListener(InvalidationListener listener) {

    this.property.addListener(listener);
  }

  @Override
  public void removeListener(InvalidationListener listener) {

    this.property.removeListener(listener);
  }

  @Override
  public void setValue(VALUE value) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public boolean isMandatory() {

    return this.property.isMandatory();
  }

  @Override
  public boolean isValid() {

    return this.property.isValid();
  }

  @Override
  public ValidationFailure validate() {

    return this.property.validate();
  }

  @Override
  public String toString() {

    return this.property.toString();
  }

}
