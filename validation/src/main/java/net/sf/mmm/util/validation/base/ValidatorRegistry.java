/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

/**
 * This is the interface for a registry where {@link AbstractValidator validators} can be {@link #add(AbstractValidator)
 * added}.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}. May be
 *        {@link Object} to register any validator. Otherwise only validators can be {@link #add(AbstractValidator)
 *        added} that apply to this type.
 * @param <SELF> the result of the {@link #add(AbstractValidator)} method. Typically the self reference (this) for
 *        fluent API calls. See {@link ObjectValidatorBuilder}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public interface ValidatorRegistry<V, SELF> {

  /**
   * @param validator the {@link AbstractValidator} to add to this builder.
   * @return this build instance for fluent API calls.
   */
  SELF add(AbstractValidator<? super V> validator);

}
