/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javafx.beans.value.ObservableIntegerValue;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableNumberProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Integer}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableIntegerProperty extends ReadableIntegerBindingProperty<Integer>, ObservableIntegerValue {

  /** @see #getType() */
  GenericType<Integer> TYPE = new SimpleGenericTypeImpl<>(Integer.class);

  @Override
  default GenericType<Integer> getType() {

    return TYPE;
  }

  // @Override
  // Integer getValue();

  @Override
  default int get() {

    Number value = getValue();
    if (value == null) {
      return 0;
    }
    return value.intValue();
  }

}
