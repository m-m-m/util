/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.nls.api.NlsThrowable;

/**
 * This is the abstract base class for an error {@link net.sf.mmm.util.lang.api.Message}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractErrorMessage extends AbstractMessage {

  /** UID for serialization. */
  private static final long serialVersionUID = 1997543457414946906L;

  /** @see #getMessage() */
  private String message;

  /** @see #getUuid() */
  private UUID uuid;

  /** @see #getDetails() */
  private String details;

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
   */
  public AbstractErrorMessage(String code, Object source, String message, UUID uuid, String details) {

    super(code, source);
    this.message = message;
    this.uuid = uuid;
    this.details = details;
  }

  /**
   * The constructor.
   * 
   * @param error is the {@link NlsThrowable} to convert as error message.
   */
  public AbstractErrorMessage(NlsThrowable error) {

    this(error.getCode(), null, error.getMessage(), error.getUuid(), null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage(Locale locale) {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDetails() {

    return this.details;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UUID getUuid() {

    return this.uuid;
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
