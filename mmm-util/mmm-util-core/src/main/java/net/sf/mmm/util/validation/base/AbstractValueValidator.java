/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the abstract base implementation of {@link ValueValidator}.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public abstract class AbstractValueValidator<V> implements ValueValidator<V> {

  /**
   * The constructor.
   */
  public AbstractValueValidator() {

    super();
  }

  /**
   * This is the default implementation to retrieve the {@link ValidationFailure#getCode() code} of this
   * {@link ValueValidator}.<br/>
   * <b>ATTENTION:</b><br/>
   * This default implementation returns the {@link Class#getSimpleName() classname} of the actual
   * {@link ValueValidator} implementation. This strategy is chosen for simplicity when implementing new a new
   * validator. To ensure stable codes override this method and return a string constant for the code. This
   * shall at least be done when the name of the class is changed.
   * 
   * @return the {@link ValidationFailure#getCode() code}.
   */
  protected String getCode() {

    return getClass().getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  public final ValidationFailure validate(V value) {

    return validate(value, null);
  }

}
