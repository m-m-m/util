/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableStringValue;
import net.sf.mmm.util.property.api.ReadableObjectProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Boolean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableStringProperty extends ReadableObjectProperty<String>, ObservableStringValue {
  /** @see #getType */
  GenericType<String> TYPE = new SimpleGenericTypeImpl<>(String.class);

  @Override
  default GenericType<String> getType() {

    return TYPE;
  }

  /**
   * @return the {@link #getValue() value} or the empty {@link String} if the {@link #getValue() value} is {@code null}.
   */
  default String getValueSafe() {

    final String value = getValue();
    return value == null ? "" : value;
  }

  /**
   * @see Bindings#length(ObservableStringValue)
   *
   * @return a new {@link IntegerBinding} that holds the {@link String#length() length} of the {@code String}
   *         {@link #getValue() value}.
   */
  default IntegerBinding length() {

    return Bindings.length(this);
  }

  /**
   * @see Bindings#concat(Object...)
   *
   * @param other the {@code Object} to concat.
   * @return a new {@code StringExpression} that holds the {@link #getValue() value} of this property concatenated with
   *         the given {@link Object}.
   */
  default StringExpression concat(Object other) {

    return Bindings.concat(this, other);
  }

  /**
   * @see Bindings#equal(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@link ObservableStringValue} to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableStringValue#getValue() value} of the given {@link ObservableStringValue} are
   *         {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isEqualTo(ObservableStringValue other) {

    return Bindings.equal(this, other);
  }

  /**
   * @see Bindings#equal(ObservableStringValue, String)
   *
   * @param other the constant {@link String} value to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@link String} are {@link Object#equals(Object) equal}.
   */
  @Override
  default BooleanBinding isEqualTo(String other) {

    return Bindings.equal(this, other);
  }

  /**
   * @see Bindings#notEqual(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@link ObservableStringValue} to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableStringValue#getValue() value} of the given {@link ObservableStringValue} are NOT
   *         {@link Object#equals(Object) equal}.
   */
  default BooleanBinding isNotEqualTo(ObservableStringValue other) {

    return Bindings.notEqual(this, other);
  }

  /**
   * @see Bindings#notEqual(ObservableStringValue, String)
   *
   * @param other the constant {@link String} value to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@link String} are NOT {@link Object#equals(Object) equal}.
   */
  @Override
  default BooleanBinding isNotEqualTo(String other) {

    return Bindings.notEqual(this, other);
  }

  /**
   * @see Bindings#equalIgnoreCase(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@link ObservableStringValue} to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableStringValue#getValue() value} of the given {@link ObservableStringValue} are
   *         {@link String#equalsIgnoreCase(String) equal ignoring the case}.
   */
  default BooleanBinding isEqualToIgnoreCase(ObservableStringValue other) {

    return Bindings.equalIgnoreCase(this, other);
  }

  /**
   * @see Bindings#equalIgnoreCase(ObservableStringValue, String)
   *
   * @param other the {@link String} to check for equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@link String} are {@link String#equalsIgnoreCase(String) equal ignoring the case}.
   */
  default BooleanBinding isEqualToIgnoreCase(String other) {

    return Bindings.equalIgnoreCase(this, other);
  }

  /**
   * @see Bindings#notEqualIgnoreCase(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@link ObservableStringValue} to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the {@link ObservableStringValue#getValue() value} of the given {@link ObservableStringValue} are NOT
   *         {@link String#equalsIgnoreCase(String) equal ignoring the case}.
   */
  default BooleanBinding isNotEqualToIgnoreCase(ObservableStringValue other) {

    return Bindings.notEqualIgnoreCase(this, other);
  }

  /**
   * @see Bindings#notEqualIgnoreCase(ObservableStringValue, String)
   *
   * @param other the {@link String} to check for non-equality.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property and
   *         the given {@link String} are NOT {@link String#equalsIgnoreCase(String) equal ignoring the case}.
   */
  default BooleanBinding isNotEqualToIgnoreCase(String other) {

    return Bindings.notEqualIgnoreCase(this, other);
  }

  /**
   * @see Bindings#greaterThan(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@code ObservableStringValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         greater than the {@link #getValue() value} of another the given {@link ObservableStringValue}.
   */
  default BooleanBinding greaterThan(ObservableStringValue other) {

    return Bindings.greaterThan(this, other);
  }

  /**
   * @see Bindings#greaterThan(ObservableStringValue, String)
   *
   * @param other the {@code String} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         greater than the given {@link String}.
   */
  default BooleanBinding greaterThan(String other) {

    return Bindings.greaterThan(this, other);
  }

  /**
   * @see Bindings#lessThan(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@code ObservableStringValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         less than the {@link #getValue() value} of another the given {@link ObservableStringValue}.
   */
  default BooleanBinding lessThan(ObservableStringValue other) {

    return Bindings.lessThan(this, other);
  }

  /**
   * @see Bindings#lessThan(ObservableStringValue, String)
   *
   * @param other the {@code String} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         less than the given {@link String}.
   */
  default BooleanBinding lessThan(String other) {

    return Bindings.lessThan(this, other);
  }

  /**
   * @see Bindings#greaterThanOrEqual(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@code ObservableStringValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         greater than or equal to the {@link #getValue() value} of another the given {@link ObservableStringValue}.
   */
  default BooleanBinding greaterThanOrEqualTo(ObservableStringValue other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  /**
   * @see Bindings#greaterThanOrEqual(ObservableStringValue, String)
   *
   * @param other the {@code String} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         greater than or equal to the given {@link String}.
   */
  default BooleanBinding greaterThanOrEqualTo(String other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  /**
   * @see Bindings#lessThanOrEqual(ObservableStringValue, ObservableStringValue)
   *
   * @param other the {@code ObservableStringValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         less than the {@link #getValue() value} of another the given {@link ObservableStringValue}.
   */
  default BooleanBinding lessThanOrEqualTo(ObservableStringValue other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  /**
   * @see Bindings#lessThanOrEqual(ObservableStringValue, String)
   *
   * @param other the {@code String} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         less than or equal to the given {@link String}.
   */
  default BooleanBinding lessThanOrEqualTo(String other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  /**
   * @see Bindings#isNull(javafx.beans.value.ObservableObjectValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         {@code null}.
   */
  @Override
  default BooleanBinding isNull() {

    return Bindings.isNull(this);
  }

  /**
   * @see Bindings#isNotNull(javafx.beans.value.ObservableObjectValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         NOT {@code null}.
   */
  @Override
  default BooleanBinding isNotNull() {

    return Bindings.isNotNull(this);
  }

  /**
   * @see Bindings#isEmpty(ObservableStringValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         {@link String#isEmpty() empty}.
   */
  default BooleanBinding isEmpty() {

    return Bindings.isEmpty(this);
  }

  /**
   * @see Bindings#isNotEmpty(ObservableStringValue)
   *
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         NOT {@link String#isEmpty() empty}.
   */
  default BooleanBinding isNotEmpty() {

    return Bindings.isNotEmpty(this);
  }

}
