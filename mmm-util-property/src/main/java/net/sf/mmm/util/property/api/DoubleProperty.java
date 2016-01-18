/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.WritableDoubleValue;

/**
 * This is the interface for a {@link GenericProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Double}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface DoubleProperty extends NumberProperty, ObservableDoubleValue, WritableDoubleValue {

  // @Override
  // Double getValue();

  @Override
  void setValue(Number value);

  @Override
  default double get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  @Override
  default void set(double value) {

    setValue(Double.valueOf(value));
  }

  @Override
  default DoubleBinding negate() {

    return (DoubleBinding) Bindings.negate(this);
  }

  @Override
  default DoubleBinding add(float other) {

    return (DoubleBinding) Bindings.add(this, other);
  }

  @Override
  default DoubleBinding add(long other) {

    return (DoubleBinding) Bindings.add(this, other);
  }

  @Override
  default DoubleBinding add(int other) {

    return (DoubleBinding) Bindings.add(this, other);
  }

  @Override
  default DoubleBinding subtract(float other) {

    return (DoubleBinding) Bindings.subtract(this, other);
  }

  @Override
  default DoubleBinding subtract(long other) {

    return (DoubleBinding) Bindings.subtract(this, other);
  }

  @Override
  default DoubleBinding subtract(int other) {

    return (DoubleBinding) Bindings.subtract(this, other);
  }

  @Override
  default DoubleBinding multiply(float other) {

    return (DoubleBinding) Bindings.multiply(this, other);
  }

  @Override
  default DoubleBinding multiply(long other) {

    return (DoubleBinding) Bindings.multiply(this, other);
  }

  @Override
  default DoubleBinding multiply(int other) {

    return (DoubleBinding) Bindings.multiply(this, other);
  }

  @Override
  default DoubleBinding divide(float other) {

    return (DoubleBinding) Bindings.divide(this, other);
  }

  @Override
  default DoubleBinding divide(long other) {

    return (DoubleBinding) Bindings.divide(this, other);
  }

  @Override
  default DoubleBinding divide(int other) {

    return (DoubleBinding) Bindings.divide(this, other);
  }

}
