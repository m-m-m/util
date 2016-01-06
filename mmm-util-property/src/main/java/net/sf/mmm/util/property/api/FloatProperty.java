/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.WritableFloatValue;

/**
 * This is the interface for a {@link GenericProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Float}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface FloatProperty extends NumberProperty, ObservableFloatValue, WritableFloatValue {

  @Override
  void setValue(Number value);

  @Override
  default float get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.floatValue();
  }

  @Override
  default void set(float value) {

    setValue(Float.valueOf(value));
  }

  @Override
  default FloatBinding negate() {

    return (FloatBinding) Bindings.negate(this);
  }

  @Override
  default FloatBinding add(float other) {

    return (FloatBinding) Bindings.add(this, other);
  }

  @Override
  default FloatBinding add(long other) {

    return (FloatBinding) Bindings.add(this, other);
  }

  @Override
  default FloatBinding add(int other) {

    return (FloatBinding) Bindings.add(this, other);
  }

  @Override
  default FloatBinding subtract(float other) {

    return (FloatBinding) Bindings.subtract(this, other);
  }

  @Override
  default FloatBinding subtract(long other) {

    return (FloatBinding) Bindings.subtract(this, other);
  }

  @Override
  default FloatBinding subtract(int other) {

    return (FloatBinding) Bindings.subtract(this, other);
  }

  @Override
  default FloatBinding multiply(float other) {

    return (FloatBinding) Bindings.multiply(this, other);
  }

  @Override
  default FloatBinding multiply(long other) {

    return (FloatBinding) Bindings.multiply(this, other);
  }

  @Override
  default FloatBinding multiply(int other) {

    return (FloatBinding) Bindings.multiply(this, other);
  }

  @Override
  default FloatBinding divide(float other) {

    return (FloatBinding) Bindings.divide(this, other);
  }

  @Override
  default FloatBinding divide(long other) {

    return (FloatBinding) Bindings.divide(this, other);
  }

  @Override
  default FloatBinding divide(int other) {

    return (FloatBinding) Bindings.divide(this, other);
  }

}
