/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This class is a helper or builder used to collect {@link ValidationFailure}s and {@link ComposedValidationFailure
 * compose} them as needed.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidationFailureComposer {

  private ValidationFailure result;

  private List<ValidationFailure> failureList;

  /**
   * The constructor.
   */
  public ValidationFailureComposer() {
    super();
  }

  /**
   * @param failure the {@link ValidationFailure} to add.
   */
  public void add(ValidationFailure failure) {

    if (failure == null) {
      return;
    }
    if (this.failureList == null) {
      if (this.result == null) {
        this.result = failure;
      } else {
        this.failureList = new ArrayList<>();
        this.failureList.add(this.result);
      }
    }
    if (this.failureList != null) {
      this.failureList.add(failure);
    }
  }

  /**
   * @param valueSource the {@link ValidationFailure#getSource() source}.
   * @return the {@link ValidationFailure} representing the {@link #add(ValidationFailure) collected} failures or
   *         {@code null} if none were collected.
   */
  public ValidationFailure get(Object valueSource) {

    if (this.failureList != null) {
      this.result = new ComposedValidationFailure(valueSource,
          this.failureList.toArray(new ValidationFailure[this.failureList.size()]));
    }
    return this.result;
  }

}
