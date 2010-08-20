/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.validator.ValueValidator;

/**
 * This is the abstract-base implementation of the {@link ValueValidator}
 * interface.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object)
 *        validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractValueValidator<V> implements ValueValidator<V> {

  /**
   * {@inheritDoc}
   */
  public void validate(V value) throws RuntimeException {

    validate(value, null);
  }

}
