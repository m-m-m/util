/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;

/**
 * This is the interface for a {@link ReadableProperty} with a non-primitive the {@link #getValue() value}-
 * {@link #getType() type}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface ReadableObjectProperty<V> extends ReadableProperty<V>, ObservableObjectValue<V> {

  @Override
  default V get() {

    return getValue();
  }

  /**
   * @see Bindings#equal(ObservableObjectValue, ObservableObjectValue)
   *
   * @param other the {@link ObservableObjectValue} to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableStringValue#getValue() value} of the given {@link ObservableObjectValue} are
   *         {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isEqualTo(ObservableObjectValue<V> other) {

    return Bindings.equal(this, other);
  }

  /**
   * @see Bindings#equal(ObservableObjectValue, Object)
   *
   * @param other the constant value to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@code value} are {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isEqualTo(V other) {

    return Bindings.equal(this, other);
  }

  /**
   * @see Bindings#notEqual(ObservableObjectValue, ObservableObjectValue)
   *
   * @param other the {@link ObservableObjectValue} to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableObjectValue#getValue() value} of the given {@link ObservableObjectValue} are NOT
   *         {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isNotEqualTo(ObservableObjectValue<V> other) {

    return Bindings.notEqual(this, other);
  }

  /**
   * @see Bindings#notEqual(ObservableObjectValue, Object)
   *
   * @param other the constant value to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@code value} are NOT {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isNotEqualTo(V other) {

    return Bindings.notEqual(this, other);
  }

  /**
   * @see Bindings#isNull(javafx.beans.value.ObservableObjectValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         {@code null}.
   */
  default BooleanBinding isNull() {

    return Bindings.isNull(this);
  }

  /**
   * @see Bindings#isNotNull(javafx.beans.value.ObservableObjectValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         NOT {@code null}.
   */
  default BooleanBinding isNotNull() {

    return Bindings.isNotNull(this);
  }

}
