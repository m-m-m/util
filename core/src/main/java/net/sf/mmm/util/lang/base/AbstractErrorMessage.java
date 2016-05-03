/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.UUID;

import net.sf.mmm.util.exception.api.NlsThrowable;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the abstract base class for an error {@link net.sf.mmm.util.lang.api.Message}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractErrorMessage extends AbstractMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = 1997543457414946906L;

  /** @see #getType() */
  private boolean technical;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractErrorMessage() {

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
   * @param technical - {@code true} in case of {@link NlsThrowable#isTechnical() technical error} with
   *        {@link #TYPE_TECHNICAL_ERROR}, {@code false} otherwise (business error).
   */
  public AbstractErrorMessage(String code, Object source, String message, UUID uuid, String details, boolean technical) {

    super(code, source, message, uuid, details);
    this.technical = technical;
  }

  /**
   * The constructor.
   *
   * @param code - see {@link #getCode()}.
   * @param source - see {@link #getSource()}.
   * @param message - see {@link #getMessage()}.
   * @param uuid - see {@link #getUuid()}.
   * @param details - see {@link #getDetails()}.
   * @param technical - {@code true} in case of {@link NlsThrowable#isTechnical() technical error} with
   *        {@link #TYPE_TECHNICAL_ERROR}, {@code false} otherwise (business error).
   */
  public AbstractErrorMessage(String code, Object source, NlsMessage message, UUID uuid, String details,
      boolean technical) {

    super(code, source, message, uuid, details);
    this.technical = technical;
  }

  /**
   * The constructor.
   *
   * @param error is the {@link NlsThrowable} to convert as error message.
   */
  public AbstractErrorMessage(NlsThrowable error) {

    this(error.getCode(), null, error.getNlsMessage(), error.getUuid(), null, error.isTechnical());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    if (this.technical) {
      return TYPE_TECHNICAL_ERROR;
    } else {
      return TYPE_USER_ERROR;
    }
  }

}
