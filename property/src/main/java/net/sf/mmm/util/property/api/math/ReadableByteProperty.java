/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Byte}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface ReadableByteProperty extends ReadableIntegerBindingProperty<Byte> {

  /** @see #getType() */
  GenericType<Byte> TYPE = new SimpleGenericTypeImpl<>(Byte.class);

  @Override
  default GenericType<Byte> getType() {

    return TYPE;
  }

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
