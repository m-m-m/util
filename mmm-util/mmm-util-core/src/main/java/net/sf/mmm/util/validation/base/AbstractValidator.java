/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the abstract base class all {@link ValueValidator} implementations should extend.
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractValidator<V> implements ValueValidator<V> {

  /**
   * The constructor.
   */
  public AbstractValidator() {

    super();
  }

  /**
   * This is the default implementation to retrieve the {@link ValidationFailure#getCode() code} of this
   * {@link ValueValidator}.<br/>
   * <b>ATTENTION:</b><br/>
   * This default implementation returns the {@link Class#getSimpleName() classname} of the actual
   * {@link ValueValidator} implementation. This strategy is chosen for simplicity when implementing a new
   * validator. To ensure stable codes override this method and return a string constant. This shall at least
   * be done when the name of the class is changed.
   * 
   * @return the {@link ValidationFailure#getCode() code}.
   */
  protected String getCode() {

    return getClass().getSimpleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final ValidationFailure validate(V value) {

    return validate(value, null);
  }

  /**
   * This method determines if this {@link ValueValidator} is <em>dynamic</em>. Here dynamic means that the
   * validation of the same input may not always return the same validation result (e.g. it holds references
   * to instances that have dynamic impact on the validation).
   * 
   * @return <code>true</code> if this {@link ValueValidator} is dynamic, <code>false</code> otherwise.
   */
  public boolean isDynamic() {

    return false;
  }

  /**
   * @return <code>true</code> if this is a validator for mandatory fields (that will not accept
   *         <code>null</code> or empty values), <code>false</code> otherwise.
   */
  public boolean isMandatory() {

    return false;
  }

}
