/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is an implementation of {@link ValidationState} that wraps an existing {@link ValidationState} and allows to
 * {@link #onFailure(ValidationFailure) collect the potential} {@link ValidationFailure#getMessage() messages} for a
 * part of the validation process. <br>
 * <b>Example:</b><br>
 *
 * <pre>
 * public boolean {@link net.sf.mmm.util.validation.api.ValidatableObject#validate(ValidationState) validate}({@link ValidationState} state) {
 *
 *   if (state == null) {
 *     state = new {@link ValidationStateImpl}();
 *   }
 *
 *   {@link ValidationStateMessageCollector} messageCollector = new {@link ValidationStateMessageCollector}(state);
 *   validateRecursive(messageCollector);
 *   String messages = messageCollector.getFailureMessages();
 *   if (messages != null) {
 *     setTooltip(messages);
 *     setStyle("invalid");
 *   } else {
 *     setTooltip("");
 *     setStyle("valid");
 *   }
 *   return messageCollector.{@link #isValid()};
 * }
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidationStateMessageCollector extends AbstractValidationStateCollector {

  private final String separator;

  private StringBuilder failureMessages;

  /**
   * The constructor.
   *
   * @param delegate is the {@link ValidationState} to adapt.
   */
  public ValidationStateMessageCollector(ValidationState delegate) {

    this(delegate, "\n");
  }

  /**
   * The constructor.
   *
   * @param delegate is the {@link ValidationState} to {@link #getDelegate() adapt}.
   * @param separator is the {@link #getSeparator() separator}.
   */
  public ValidationStateMessageCollector(ValidationState delegate, String separator) {

    super(delegate);
    this.separator = separator;
  }

  /**
   * This method gets the {@link String} used to separate individual {@link #onFailure(ValidationFailure) validation
   * failure} {@link ValidationFailure#getMessage() messages} for {@link #getFailureMessages()}.
   *
   * @return the separator.
   */
  public String getSeparator() {

    return this.separator;
  }

  @Override
  public void onFailure(ValidationFailure failure) {

    super.onFailure(failure);
    if (this.failureMessages == null) {
      this.failureMessages = new StringBuilder(failure.getMessage());
    } else {
      this.failureMessages.append(this.separator);
      this.failureMessages.append(failure.getMessage());
    }
  }

  @Override
  public boolean isValid() {

    return (this.failureMessages == null);
  }

  /**
   * This method gets the {@link #onFailure(ValidationFailure) collected} {@link ValidationFailure#getMessage()
   * messages} separated with the {@link #getSeparator() separator}.
   *
   * @return the {@link ValidationFailure#getMessage() failure messages} or {@code null} if valid.
   */
  public String getFailureMessages() {

    if (this.failureMessages == null) {
      return null;
    }
    return this.failureMessages.toString();
  }

  @Override
  public String toString() {

    if (this.failureMessages == null) {
      return "<valid>";
    } else {
      return this.failureMessages.toString();
    }
  }

}
