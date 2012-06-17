/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.validator;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * A {@link ValueValidator} allows to {@link #validate(Object) validate} according values.<br/>
 * There can be arbitrary implementations of this interface. A regular implementation shall be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ValueValidator<V> {

  /**
   * This method performs the validation of the given <code>value</code>.
   * 
   * @param value is the value to validate.
   * @param source is the source
   * @return
   */
  ValidationFailure validate(V value, Object source);

}
