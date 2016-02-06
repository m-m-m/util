/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableNumberValue;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Number}.
 *
 * @see WritableNumberProperty
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableNumberProperty extends ReadableProperty<Number>, NumberExpression {

  /** @see #getType() */
  GenericType<Number> TYPE = new SimpleGenericTypeImpl<>(Number.class);

  @Override
  default GenericType<Number> getType() {

    return TYPE;
  }

  @Override
  default NumberBinding negate() {

    return Bindings.negate(this);
  }

  @Override
  default NumberBinding add(ObservableNumberValue other) {

    return Bindings.add(this, other);
  }

  @Override
  default DoubleBinding add(double other) {

    return Bindings.add(this, other);
  }

  @Override
  default NumberBinding add(float other) {

    return Bindings.add(this, other);
  }

  @Override
  default NumberBinding add(long other) {

    return Bindings.add(this, other);
  }

  @Override
  default NumberBinding add(int other) {

    return Bindings.add(this, other);
  }

  @Override
  default NumberBinding subtract(ObservableNumberValue other) {

    return Bindings.subtract(this, other);
  }

  @Override
  default DoubleBinding subtract(double other) {

    return Bindings.subtract(this, other);
  }

  @Override
  default NumberBinding subtract(float other) {

    return Bindings.subtract(this, other);
  }

  @Override
  default NumberBinding subtract(long other) {

    return Bindings.subtract(this, other);
  }

  @Override
  default NumberBinding subtract(int other) {

    return Bindings.subtract(this, other);
  }

  @Override
  default NumberBinding multiply(ObservableNumberValue other) {

    return Bindings.multiply(this, other);
  }

  @Override
  default DoubleBinding multiply(double other) {

    return Bindings.multiply(this, other);
  }

  @Override
  default NumberBinding multiply(float other) {

    return Bindings.multiply(this, other);
  }

  @Override
  default NumberBinding multiply(long other) {

    return Bindings.multiply(this, other);
  }

  @Override
  default NumberBinding multiply(int other) {

    return Bindings.multiply(this, other);
  }

  @Override
  default NumberBinding divide(ObservableNumberValue other) {

    return Bindings.divide(this, other);
  }

  @Override
  default DoubleBinding divide(double other) {

    return Bindings.divide(this, other);
  }

  @Override
  default NumberBinding divide(float other) {

    return Bindings.divide(this, other);
  }

  @Override
  default NumberBinding divide(long other) {

    return Bindings.divide(this, other);
  }

  @Override
  default NumberBinding divide(int other) {

    return Bindings.divide(this, other);
  }

  @Override
  default BooleanBinding isEqualTo(ObservableNumberValue other) {

    return Bindings.equal(this, other);
  }

  @Override
  default BooleanBinding isEqualTo(ObservableNumberValue other, double epsilon) {

    return Bindings.equal(this, other);
  }

  @Override
  default BooleanBinding isEqualTo(double other, double epsilon) {

    return Bindings.equal(this, other, epsilon);
  }

  @Override
  default BooleanBinding isEqualTo(float other, double epsilon) {

    return Bindings.equal(this, other, epsilon);
  }

  @Override
  default BooleanBinding isEqualTo(long other) {

    return Bindings.equal(this, other);
  }

  @Override
  default BooleanBinding isEqualTo(long other, double epsilon) {

    return Bindings.equal(this, other, epsilon);
  }

  @Override
  default BooleanBinding isEqualTo(int other) {

    return Bindings.equal(this, other);
  }

  @Override
  default BooleanBinding isEqualTo(int other, double epsilon) {

    return Bindings.equal(this, other, epsilon);
  }

  @Override
  default BooleanBinding isNotEqualTo(ObservableNumberValue other) {

    return Bindings.notEqual(this, other);
  }

  @Override
  default BooleanBinding isNotEqualTo(ObservableNumberValue other, double epsilon) {

    return Bindings.notEqual(this, other, epsilon);
  }

  @Override
  default BooleanBinding isNotEqualTo(double other, double epsilon) {

    return Bindings.notEqual(this, other, epsilon);
  }

  @Override
  default BooleanBinding isNotEqualTo(float other, double epsilon) {

    return Bindings.notEqual(this, other, epsilon);
  }

  @Override
  default BooleanBinding isNotEqualTo(long other) {

    return Bindings.notEqual(this, other);
  }

  @Override
  default BooleanBinding isNotEqualTo(long other, double epsilon) {

    return Bindings.notEqual(this, other, epsilon);
  }

  @Override
  default BooleanBinding isNotEqualTo(int other) {

    return Bindings.notEqual(this, other);
  }

  @Override
  default BooleanBinding isNotEqualTo(int other, double epsilon) {

    return Bindings.notEqual(this, other, epsilon);
  }

  @Override
  default BooleanBinding greaterThan(ObservableNumberValue other) {

    return Bindings.greaterThan(this, other);
  }

  @Override
  default BooleanBinding greaterThan(double other) {

    return Bindings.greaterThan(this, other);
  }

  @Override
  default BooleanBinding greaterThan(float other) {

    return Bindings.greaterThan(this, other);
  }

  @Override
  default BooleanBinding greaterThan(long other) {

    return Bindings.greaterThan(this, other);
  }

  @Override
  default BooleanBinding greaterThan(int other) {

    return Bindings.greaterThan(this, other);
  }

  @Override
  default BooleanBinding lessThan(ObservableNumberValue other) {

    return Bindings.lessThan(this, other);
  }

  @Override
  default BooleanBinding lessThan(double other) {

    return Bindings.lessThan(this, other);
  }

  @Override
  default BooleanBinding lessThan(float other) {

    return Bindings.lessThan(this, other);
  }

  @Override
  default BooleanBinding lessThan(long other) {

    return Bindings.lessThan(this, other);
  }

  @Override
  default BooleanBinding lessThan(int other) {

    return Bindings.lessThan(this, other);
  }

  @Override
  default BooleanBinding greaterThanOrEqualTo(ObservableNumberValue other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding greaterThanOrEqualTo(double other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding greaterThanOrEqualTo(float other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding greaterThanOrEqualTo(long other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding greaterThanOrEqualTo(int other) {

    return Bindings.greaterThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding lessThanOrEqualTo(ObservableNumberValue other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding lessThanOrEqualTo(double other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding lessThanOrEqualTo(float other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding lessThanOrEqualTo(long other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  @Override
  default BooleanBinding lessThanOrEqualTo(int other) {

    return Bindings.lessThanOrEqual(this, other);
  }

  @SuppressWarnings("restriction")
  @Override
  default StringBinding asString() {

    return (StringBinding) com.sun.javafx.binding.StringFormatter.convert(this);
  }

  @Override
  default StringBinding asString(String format) {

    return (StringBinding) Bindings.format(format, this);
  }

  @Override
  default StringBinding asString(Locale locale, String format) {

    return (StringBinding) Bindings.format(locale, format, this);
  }

  @Override
  default int intValue() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.intValue();
  }

  @Override
  default long longValue() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.longValue();
  }

  @Override
  default double doubleValue() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  @Override
  default float floatValue() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.floatValue();
  }

}
