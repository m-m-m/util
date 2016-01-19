/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.value.WritableLongValue;

/**
 * This is the interface for a {@link WritableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Long}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableLongProperty extends ReadableLongProperty, WritableNumberProperty, WritableLongValue {

  @Override
  default long get() {

    return ReadableLongProperty.super.get();
  }

  @Override
  void setValue(Number value);

  @Override
  default void set(long value) {

    setValue(Long.valueOf(value));
  }

}
