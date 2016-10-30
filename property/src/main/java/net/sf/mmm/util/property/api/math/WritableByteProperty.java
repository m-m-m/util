/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

/**
 * This is the interface for a {@link WritableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Byte}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface WritableByteProperty extends ReadableByteProperty, WritableNumberProperty<Byte> {

  @Override
  default byte get() {

    return ReadableByteProperty.super.get();
  }

  @Override
  void setValue(Number value);

  /**
   * @param value the new {@link #get() value}.
   */
  default void set(byte value) {

    setValue(Byte.valueOf(value));
  }

}
