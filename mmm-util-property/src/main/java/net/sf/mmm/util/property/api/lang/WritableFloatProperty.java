/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import javafx.beans.value.WritableFloatValue;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Float}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableFloatProperty
    extends ReadableFloatProperty, WritableNumberProperty<Float>, WritableFloatValue {

  @Override
  default float get() {

    return ReadableFloatProperty.super.get();
  }

  @Override
  void setValue(Number value);

  @Override
  default void set(float value) {

    setValue(Float.valueOf(value));
  }

}
