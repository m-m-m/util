/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

/**
 * This is the interface for a {@link ReadableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Byte}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableByteProperty extends ReadableIntegerBindingProperty {

  // @Override
  // Byte getValue();

  /**
   * @return the primitive {@link #getValue() value}.
   */
  default byte get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.byteValue();
  }

}
