/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the interface for an object that allows to {@link #addValidator(ValueValidator) add} and
 * {@link #removeValidator(ValueValidator) remove} {@link ValueValidator validators}.
 *
 * @param <V> is the generic type of the value that shall be validated.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @deprecated use {@link Validatable}
 */
@Deprecated
public interface HasValueValidators<V> {

  /**
   * This method adds the given {@link ValueValidator} to this object. All {@link ValueValidator}s are
   * {@link ValueValidator#validate(Object) invoked} in the same order as they are added by this method. They are
   * {@link ValueValidator#validate(Object) invoked} no matter if the previous {@link ValueValidator}s failed or
   * succeeded. You should always design {@link ValueValidator}s in a robust and reusable way (e.g. a range validator
   * should accept {@code null} as valid input so it can be used for both mandatory and optional fields).
   *
   * @param validator is the {@link ValueValidator} to add.
   */
  void addValidator(ValueValidator<? super V> validator);

  /**
   * This method removes the given {@link ValueValidator} from this object. <br>
   * <b>ATTENTION:</b><br>
   * Having dynamic validators is an exotic case that should be avoided. This feature is provided but not recommended.
   *
   * @param validator is the {@link ValueValidator} to remove.
   * @return {@code true} if the {@link ValueValidator} has actually been removed, {@code false} otherwise (it has NOT
   *         previously been {@link #addValidator(ValueValidator) added}).
   */
  boolean removeValidator(ValueValidator<? super V> validator);

}
