/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javafx.beans.value.WritableDoubleValue;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Double}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface WritableDoubleProperty
    extends ReadableDoubleProperty, WritableNumberProperty<Double>, WritableDoubleValue {

  @Override
  default double get() {

    return ReadableDoubleProperty.super.get();
  }

  @Override
  void setValue(Number value);

  @Override
  default void set(double value) {

    setValue(Double.valueOf(value));
  }

}
