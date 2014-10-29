/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base implementation of {@link ValidationState} that wraps an existing
 * {@link ValidationState} and allows to {@link #onFailure(ValidationFailure) collect the potential failures}
 * for a part of the validation process. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractValidationStateCollector implements ValidationState {

  /** The wrapped {@link ValidationState}. */
  private final ValidationState delegate;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link ValidationState} to adapt.
   */
  public AbstractValidationStateCollector(ValidationState delegate) {

    super();
    NlsNullPointerException.checkNotNull(ValidationState.class, delegate);
    this.delegate = delegate;
  }

  /**
   * @return the {@link ValidationState} to adapt. Each call to {@link #onFailure(ValidationFailure)} will
   *         also be propagated to this delegate.
   */
  protected ValidationState getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFailure(ValidationFailure failure) {

    this.delegate.onFailure(failure);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFailureCount() {

    return this.delegate.getFailureCount();
  }

  /**
   * {@inheritDoc}
   * 
   * <br>
   * <b>ATTENTION:</b><br>
   * This method only returns <code>false</code> if a {@link ValidationFailure} has been
   * {@link #onFailure(ValidationFailure) collected} by this instance. It may therefore return
   * <code>true</code> even if the {@link #getDelegate() delegate} would return <code>false</code>.
   */
  @Override
  public boolean isValid() {

    return this.delegate.isValid();
  }

}
