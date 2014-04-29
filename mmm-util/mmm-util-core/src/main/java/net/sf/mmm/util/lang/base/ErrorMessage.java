/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.UUID;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsThrowable;

/**
 * This is the implementation of an error {@link net.sf.mmm.util.lang.api.Message} that is already localized.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ErrorMessage extends AbstractErrorMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ErrorMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param code - see {@link #getCode()}.
   * @param source - see {@link #getSource()}.
   * @param message - see {@link #getMessage()}.
   * @param uuid - see {@link #getUuid()}.
   * @param details - see {@link #getDetails()}.
   * @param technical - <code>true</code> in case of {@link NlsThrowable#isTechnical() technical error} with
   *        {@link #TYPE_TECHNICAL_ERROR}, <code>false</code> otherwise (business error).
   */
  public ErrorMessage(String code, Object source, String message, UUID uuid, String details, boolean technical) {

    super(code, source, message, uuid, details, technical);
  }

  /**
   * The constructor.
   *
   * @param code - see {@link #getCode()}.
   * @param source - see {@link #getSource()}.
   * @param message - see {@link #getMessage()}.
   * @param uuid - see {@link #getUuid()}.
   * @param details - see {@link #getDetails()}.
   * @param technical - <code>true</code> in case of {@link NlsThrowable#isTechnical() technical error} with
   *        {@link #TYPE_TECHNICAL_ERROR}, <code>false</code> otherwise (business error).
   */
  public ErrorMessage(String code, Object source, NlsMessage message, UUID uuid, String details, boolean technical) {

    super(code, source, message, uuid, details, technical);
  }

  /**
   * The constructor.
   *
   * @param error is the {@link NlsThrowable} to convert as error message.
   */
  public ErrorMessage(NlsThrowable error) {

    super(error);
  }

}
