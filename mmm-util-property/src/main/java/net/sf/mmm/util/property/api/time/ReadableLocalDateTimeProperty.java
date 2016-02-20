/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.LocalDateTime;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableObjectValue;
import net.sf.mmm.util.property.api.ReadableObjectProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.base.AbstractBooleanBinding;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Boolean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableLocalDateTimeProperty extends ReadableObjectProperty<LocalDateTime> {

  /** @see #getType */
  GenericType<LocalDateTime> TYPE = new SimpleGenericTypeImpl<>(LocalDateTime.class);

  @Override
  default GenericType<LocalDateTime> getType() {

    return TYPE;
  }

  /**
   * @param other the {@code ObservableObjectValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         {@link LocalDateTime#isBefore(java.time.chrono.ChronoLocalDateTime) before} the {@link #getValue() value}
   *         of another the given {@link ObservableObjectValue}.
   */
  default BooleanBinding isBefore(ObservableObjectValue<LocalDateTime> other) {

    return new AbstractBooleanBinding(this, other) {

      @Override
      protected boolean computeValue() {

        LocalDateTime date1 = ReadableLocalDateTimeProperty.this.getValue();
        if (date1 == null) {
          return false;
        }
        LocalDateTime date2 = other.getValue();
        if (date2 == null) {
          return false;
        }
        return date1.isBefore(date2);
      }
    };
  }

  /**
   * @param other the {@code ObservableObjectValue} to compare.
   * @return a new {@link BooleanBinding} that holds {@code true} if the {@link #getValue() value} of this property is
   *         {@link LocalDateTime#isAfter(java.time.chrono.ChronoLocalDateTime) after} the {@link #getValue() value} of
   *         another the given {@link ObservableObjectValue}.
   */
  default BooleanBinding isAfter(ObservableObjectValue<LocalDateTime> other) {

    return new AbstractBooleanBinding(this, other) {

      @Override
      protected boolean computeValue() {

        LocalDateTime date1 = ReadableLocalDateTimeProperty.this.getValue();
        if (date1 == null) {
          return false;
        }
        LocalDateTime date2 = other.getValue();
        if (date2 == null) {
          return false;
        }
        return date1.isAfter(date2);
      }
    };
  }

}
