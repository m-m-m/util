/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Double}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableDoubleProperty extends ReadableNumberProperty<Double>, ObservableDoubleValue {

  /** @see #getType() */
  GenericType<Double> TYPE = new SimpleGenericTypeImpl<>(Double.class);

  @Override
  default GenericType<Double> getType() {

    return TYPE;
  }

  // @Override
  // Double getValue();

  @Override
  default double get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
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
