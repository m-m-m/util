/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.WritableStringValue;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Boolean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableStringProperty
    extends ReadableStringProperty, WritableProperty<String>, WritableStringValue {

  @Override
  default String get() {

    return getValue();
  }

  @Override
  default void set(String value) {

    setValue(value);
  }

  /**
   * @return the {@link #getValue() value} or the empty {@link String} if {@link #getValue() value} is <code>null</code>
   *         .
   */
  default String getValueSafe() {

    final String value = getValue();
    return value == null ? "" : value;
  }

  /**
   * @see StringExpression#length()
   *
   * @return a new {@link IntegerBinding} that holds the {@link String#length() length} of the {@code String}
   *         {@link #getValue() value}.
   */
  default IntegerBinding length() {

    return Bindings.length(this);
  }

}
