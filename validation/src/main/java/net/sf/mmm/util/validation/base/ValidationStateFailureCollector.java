/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is an implementation of {@link ValidationState} that wraps an existing {@link ValidationState} and allows to
 * {@link #onFailure(ValidationFailure) collect the potential failures} for a part of the validation process. <br>
 * <b>Example:</b><br>
 *
 * <pre>
 * public boolean {@link net.sf.mmm.util.validation.api.ValidatableObject#validate(ValidationState) validate}({@link ValidationState} state) {
 *
 *   if (state == null) {
 *     state = new {@link ValidationStateImpl}();
 *   }
 *
 *   {@link ValidationStateFailureCollector} failureCollector = new {@link ValidationStateFailureCollector}(state);
 *   validateRecursive(failureCollector);
 *   {@link List}&lt;{@link ValidationFailure}&gt; childFailureList = failureCollector.{@link #getFailureList()};
 *   if (!childFailureList.isEmpty()) {
 *     applyValidationFailures(childFailureList);
 *   }
 *   return failureCollector.{@link #isValid()};
 * }
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidationStateFailureCollector extends AbstractValidationStateCollector {

  private final List<ValidationFailure> failureList;

  /**
   * The constructor.
   *
   * @param delegate is the {@link ValidationState} to adapt.
   */
  public ValidationStateFailureCollector(ValidationState delegate) {

    super(delegate);
    this.failureList = new LinkedList<>();
  }

  @Override
  public void onFailure(ValidationFailure failure) {

    super.onFailure(failure);
    this.failureList.add(failure);
  }

  @Override
  public boolean isValid() {

    return this.failureList.isEmpty();
  }

  /**
   * This method gets the {@link List} with the {@link ValidationFailure}s {@link #onFailure(ValidationFailure)
   * collected} during {@link net.sf.mmm.util.validation.api.AbstractValidatableObject#validate(ValidationState)
   * validation}.
   *
   * @return the {@link List} of {@link ValidationFailure}s.
   */
  public List<ValidationFailure> getFailureList() {

    return this.failureList;
  }

  @Override
  public String toString() {

    if (this.failureList.isEmpty()) {
      return "<valid>";
    }
    StringBuilder buffer = new StringBuilder();
    for (ValidationFailure failure : this.failureList) {
      if (buffer.length() > 0) {
        buffer.append('\n');
      }
      buffer.append(failure);
    }
    return buffer.toString();
  }

}
