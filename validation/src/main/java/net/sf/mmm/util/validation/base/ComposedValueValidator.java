/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the interface for a {@link ValueValidator} {@link #getValidator(int) composed} out of other validators.
 *
 * @param <V> the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface ComposedValueValidator<V> extends ValueValidator<V> {

  /**
   * @see #getValidator(int)
   * @see java.util.Collection#size()
   *
   * @return the number of {@link #getValidator(int) validators}.
   */
  int getValidatorCount();

  /**
   * Gets the {@link ValueValidator} at the given <code>index</code>.
   *
   * @see java.util.List#get(int)
   *
   * @param index is the index of the {@link ValueValidator} to get.
   * @return the requested {@link ValueValidator}.
   */
  AbstractValidator<?> getValidator(int index);

}
