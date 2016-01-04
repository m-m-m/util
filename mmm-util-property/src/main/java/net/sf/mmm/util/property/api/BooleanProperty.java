/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.WritableBooleanValue;

/**
 * This is the interface for a {@link GenericProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Boolean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface BooleanProperty extends GenericProperty<Boolean>, ObservableBooleanValue, WritableBooleanValue {

  @Override
  default boolean get() {

    Boolean value = getValue();
    if (value == null) {
      return false;
    }
    return value.booleanValue();
  }

  @Override
  void setValue(Boolean value);

  @Override
  default void set(boolean value) {

    setValue(Boolean.valueOf(value));
  }

  /**
   * @see BooleanExpression#and(ObservableBooleanValue)
   *
   * @param other the {@link ObservableBooleanValue} to build the conjunction with.
   * @return a new {@link BooleanBinding} that performs the conditional AND-operation on this {@link BooleanProperty}.
   */
  default BooleanBinding and(ObservableBooleanValue other) {

    return Bindings.and(this, other);
  }

  /**
   * @see BooleanExpression#or(ObservableBooleanValue)
   *
   * @param other the {@link ObservableBooleanValue} to build the conjunction with.
   * @return a new {@link BooleanBinding} that performs the conditional OR-operation on this {@link BooleanProperty}.
   */
  default BooleanBinding or(ObservableBooleanValue other) {

    return Bindings.or(this, other);
  }

  /**
   * @see BooleanExpression#not()
   *
   * @return a new {@link BooleanBinding} that performs the negation of this {@link BooleanProperty}.
   */
  default BooleanBinding not() {

    return Bindings.not(this);
  }

  /**
   * @see BooleanExpression#isEqualTo(ObservableBooleanValue)
   *
   * @param other the {@link ObservableBooleanValue} to compare.
   * @return a new {@link BooleanBinding} holds {@code true} if this and the given {@link ObservableBooleanValue} are
   *         {@link #equals(Object) equal}.
   */
  default BooleanBinding isEqualTo(final ObservableBooleanValue other) {

    return Bindings.equal(this, other);
  }

  /**
   * @see BooleanExpression#isNotEqualTo(ObservableBooleanValue)
   *
   * @param other the {@link ObservableBooleanValue} to compare.
   * @return a new {@link BooleanBinding} holds {@code false} if this and the given {@link ObservableBooleanValue} are
   *         {@link #equals(Object) equal}.
   */
  default BooleanBinding isNotEqualTo(final ObservableBooleanValue other) {

    return Bindings.notEqual(this, other);
  }

}
