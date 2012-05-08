/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.util.validation.api.ValidationCollector;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This class represents the state of a validation.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public class ValidationState implements ValidationCollector {

  /** @see #onFailure(ValidationFailure) */
  private final List<ValidationFailure> failureList;

  /** @see #getFailureList() */
  private final List<ValidationFailure> failureView;

  /**
   * The constructor.
   */
  public ValidationState() {

    super();
    this.failureList = new LinkedList<ValidationFailure>();
    this.failureView = Collections.unmodifiableList(this.failureList);
  }

  /**
   * This method determines if the
   * {@link net.sf.mmm.util.validation.api.AbstractValidatableObject#validate(ValidationState) validation} has
   * been successful.
   * 
   * @return <code>true</code> if the {@link #getFailureList() failure list} is {@link List#isEmpty() empty},
   *         <code>false</code> otherwise.
   */
  public boolean isValid() {

    return this.failureList.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  public void onFailure(ValidationFailure failure) {

    this.failureList.add(failure);
  }

  /**
   * This method gets an {@link Collections#unmodifiableList(List) unmodifiable} {@link List} with the
   * {@link ValidationFailure}s {@link #onFailure(ValidationFailure) collected} during
   * {@link net.sf.mmm.util.validation.api.AbstractValidatableObject#validate(ValidationState) validation}.
   * 
   * @return the {@link List} of {@link ValidationFailure}s.
   */
  public List<ValidationFailure> getFailureList() {

    return this.failureView;
  }

}
