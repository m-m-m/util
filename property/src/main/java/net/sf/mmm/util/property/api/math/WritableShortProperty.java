/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

/**
 * This is the interface for a {@link WritableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Short}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface WritableShortProperty extends ReadableShortProperty, WritableNumberProperty<Short> {

  @Override
  default short get() {

    return ReadableShortProperty.super.get();
  }

  @Override
  void setValue(Number value);

  /**
   * @param value the new {@link #get() value}.
   */
  default void set(short value) {

    setValue(Short.valueOf(value));
  }

}
