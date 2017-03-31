/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javafx.beans.value.WritableIntegerValue;

/**
 * This is the interface for a {@link WritableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Integer}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface WritableIntegerProperty
    extends ReadableIntegerProperty, WritableNumberProperty<Integer>, WritableIntegerValue {

  @Override
  default int get() {

    return ReadableIntegerProperty.super.get();
  }

  @Override
  void setValue(Number value);

  @Override
  default void set(int value) {

    setValue(Integer.valueOf(value));
  }

}
