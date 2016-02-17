/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.value.ObservableLongValue;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Long}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableLongProperty extends ReadableNumberProperty, ObservableLongValue {

  /** @see #getType() */
  GenericType<Long> TYPE = new SimpleGenericTypeImpl<>(Long.class);

  @Override
  default GenericType<Long> getType() {

    return TYPE;
  }

  // @Override
  // Long getValue();

  @Override
  default long get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.longValue();
  }

  @Override
  default LongBinding negate() {

    return (LongBinding) Bindings.negate(this);
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
  default LongBinding add(int other) {

    return (LongBinding) Bindings.add(this, other);
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
  default LongBinding subtract(int other) {

    return (LongBinding) Bindings.subtract(this, other);
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
  default LongBinding multiply(int other) {

    return (LongBinding) Bindings.multiply(this, other);
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
  default LongBinding divide(int other) {

    return (LongBinding) Bindings.divide(this, other);
  }

}
