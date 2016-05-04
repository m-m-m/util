/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import javafx.beans.value.WritableBooleanValue;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Boolean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableBooleanProperty
    extends ReadableBooleanProperty, WritableProperty<Boolean>, WritableBooleanValue {

  @Override
  default boolean get() {

    return ReadableBooleanProperty.super.get();
  }

  @Override
  void setValue(Boolean value);

  @Override
  default void set(boolean value) {

    setValue(Boolean.valueOf(value));
  }

}
