/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.lang.api.Message;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.base.NlsMessagePlain;

/**
 * This is the abstract base implementation of {@link Message}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractMessage implements Message {

  /** UID for serialization. */
  private static final long serialVersionUID = -88269463788978385L;

  /** @see #getCode() */
  private String code;

  /** @see #getSource() */
  private String source;

  /** @see #getMessage(java.util.Locale) */
  private NlsMessage message;

  /** @see #getUuid() */
  private UUID uuid;

  /** @see #getDetails() */
  private String details;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractMessage() {

    super();
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   * @param uuid is the {@link #getUuid() uuid}.
   * @param details are the {@link #getDetails() details}.
   */
  public AbstractMessage(String code, Object source, String message, UUID uuid, String details) {

    this(code, source, new NlsMessagePlain(message), uuid, details);
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   * @param uuid is the {@link #getUuid() uuid}.
   * @param details are the {@link #getDetails() details}.
   */
  public AbstractMessage(String code, Object source, NlsMessage message, UUID uuid, String details) {

    super();
    this.code = code;
    if (source == null) {
      this.source = null;
    } else {
      this.source = source.toString();
    }
    this.message = message;
    this.uuid = uuid;
    this.details = details;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return this.code;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getMessage() {

    return getMessage(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage(Locale locale) {

    if (locale == null) {
      return this.message.getLocalizedMessage();
    } else {
      return this.message.getLocalizedMessage(locale);
    }
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
  public String toString() {

    return getMessage();
  }

}
