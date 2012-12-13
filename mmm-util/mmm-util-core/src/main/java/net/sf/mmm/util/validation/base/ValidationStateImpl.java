/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This class represents the state of a validation.
 * 
 * @see ValidationStateFailureCollector
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidationStateImpl implements ValidationState {

  /** @see #onFailure(ValidationFailure) */
  private final List<ValidationFailure> failureList;

  /** @see #getFailureList() */
  private final List<ValidationFailure> failureView;

  /**
   * The constructor.
   */
  public ValidationStateImpl() {

    super();
    this.failureList = new LinkedList<ValidationFailure>();
    this.failureView = Collections.unmodifiableList(this.failureList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {

    return this.failureList.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
