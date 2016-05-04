/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValidationFailure}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidationFailureImpl extends AbstractValidationFailure {

  private static final long serialVersionUID = -7710863486871741334L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValidationFailureImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public ValidationFailureImpl(String code, Object source, NlsMessage message) {

    super(code, source, message, null, null);
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public ValidationFailureImpl(String code, Object source, String message) {

    super(code, source, message, null, null);
  }

}
