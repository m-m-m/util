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
 */
public interface HasValueValidators<V> {

  /**
   * This method adds the given {@link ValueValidator} to this object.
   * 
   * @param validator is the {@link ValueValidator} to add.
   */
  void addValidator(ValueValidator<? super V> validator);

  /**
   * This method removes the given {@link ValueValidator} from this object.
   * 
   * @param validator is the {@link ValueValidator} to remove.
   * @return <code>true</code> if the {@link ValueValidator} has actually been removed, <code>false</code>
   *         otherwise.
   */
  boolean removeValidator(ValueValidator<? super V> validator);

}
