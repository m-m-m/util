/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.WritableIntegerValue;

/**
 * This is the interface for a {@link GenericProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Integer}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface IntegerProperty extends NumberProperty, ObservableIntegerValue, WritableIntegerValue {

  @Override
  default int get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.intValue();
  }

  @Override
  default void set(int value) {

    setValue(Integer.valueOf(value));
  }

  @Override
  default IntegerBinding negate() {

    return (IntegerBinding) Bindings.negate(this);
  }

  @Override
  default FloatBinding add(float other) {

    return (FloatBinding) Bindings.add(this, other);
  }

  @Override
  default LongBinding add(long other) {

    return (LongBinding) Bindings.add(this, other);
  }

  @Override
  default IntegerBinding add(int other) {

    return (IntegerBinding) Bindings.add(this, other);
  }

  @Override
  default FloatBinding subtract(float other) {

    return (FloatBinding) Bindings.subtract(this, other);
  }

  @Override
  default LongBinding subtract(long other) {

    return (LongBinding) Bindings.subtract(this, other);
  }

  @Override
  default IntegerBinding subtract(int other) {

    return (IntegerBinding) Bindings.subtract(this, other);
  }

  @Override
  default FloatBinding multiply(float other) {

    return (FloatBinding) Bindings.multiply(this, other);
  }

  @Override
  default LongBinding multiply(long other) {

    return (LongBinding) Bindings.multiply(this, other);
  }

  @Override
  default IntegerBinding multiply(int other) {

    return (IntegerBinding) Bindings.multiply(this, other);
  }

  @Override
  default FloatBinding divide(float other) {

    return (FloatBinding) Bindings.divide(this, other);
  }

  @Override
  default LongBinding divide(long other) {

    return (LongBinding) Bindings.divide(this, other);
  }

  @Override
  default IntegerBinding divide(int other) {

    return (IntegerBinding) Bindings.divide(this, other);
  }

}
