/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.UUID;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the implementation of an {@link net.sf.mmm.util.lang.api.Message#TYPE_INFORMATION information}
 * {@link net.sf.mmm.util.lang.api.Message}.
 *
 * @deprecated use {@link net.sf.mmm.util.nls.base.InformationMessage} instead.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Deprecated
public class InformationMessage extends AbstractMessage {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected InformationMessage() {

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
  public InformationMessage(String code, Object source, String message, UUID uuid, String details) {

    super(code, source, message, uuid, details);
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
  public InformationMessage(String code, Object source, NlsMessage message, UUID uuid, String details) {

    super(code, source, message, uuid, details);
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public InformationMessage(String code, Object source, String message) {

    super(code, source, message, null, null);
  }

  /**
   * The constructor.
   *
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public InformationMessage(String code, Object source, NlsMessage message) {

    super(code, source, message, null, null);
  }

  @Override
  public String getDetails() {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getType() {

    return TYPE_INFORMATION;
  }

  @Override
  public UUID getUuid() {

    return null;
  }

}
