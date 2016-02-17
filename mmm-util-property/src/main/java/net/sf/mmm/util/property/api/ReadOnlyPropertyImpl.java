/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is a {@link #isReadOnly() read-only} view on a {@link WritableProperty}.
 *
 * @see #getReadOnly()
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author hohwille
 * @since 8.0.0
 */
public class ReadOnlyPropertyImpl<VALUE> implements WritableProperty<VALUE> {

  private final WritableProperty<VALUE> property;

  /**
   * The constructor.
   *
   * @param property the {@link WritableProperty} to give {@link #isReadOnly() read-only} access to.
   */
  public ReadOnlyPropertyImpl(WritableProperty<VALUE> property) {
    super();
    this.property = property;
  }

  @Override
  public WritableProperty<VALUE> getReadOnly() {

    return this;
  }

  @Override
  public final boolean isReadOnly() {

    return true;
  }

  @Override
  public GenericType<? extends VALUE> getType() {

    return this.property.getType();
  }

  @Override
  public void bind(ObservableValue<? extends VALUE> observable) {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public void unbind() {

    throw new ReadOnlyException(getBean(), getName());
  }

  @Override
  public boolean isBound() {

    return this.property.isBound();
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
