/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;
import net.sf.mmm.util.lang.api.attribute.AttributeReadUuid;

/**
 * This is the interface for a message that gets displayed to an end-user.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface Message extends Serializable, AttributeReadUuid, AttributeReadMessage, AttributeReadMessageCode {

  /**
   * The {@link #getType() type} for a technical error.
   *
   * @see net.sf.mmm.util.exception.api.NlsThrowable#isTechnical()
   */
  String TYPE_TECHNICAL_ERROR = "TechnicalError";

  /**
   * The {@link #getType() type} for a user error.
   *
   * @see net.sf.mmm.util.exception.api.NlsThrowable#isForUser()
   */
  String TYPE_USER_ERROR = "UserError";

  /** The {@link #getType() type} for a validation failure. */
  String TYPE_VALIDATION_FAILURE = "ValidationFailure";

  /** The {@link #getType() type} for a warning. */
  String TYPE_WARNING = "Warning";

  /** The {@link #getType() type} for an information. */
  String TYPE_INFORMATION = "Information";

  /**
   * This method gets the (optional) source of the message. E.g. the name or a field or file that is invalid.
   * It can be used to enrich {@link #getMessage() messages} displayed to end-users. This will help to find
   * the problem easier.
   *
   * @return the source or <code>null</code> if NOT available.
   */
  String getSource();

  /**
   * This method gets the {@link #getMessage() message} localized for the given {@link Locale}. <br>
   * <b>ATTENTION:</b><br>
   * This method is designed for server applications with {@link net.sf.mmm.util.nls.api.NlsMessage NLS}. On
   * client side (e.g. for GWT clients) only a single locale may be supported at a time and this method may
   * behave like {@link #getMessage()} ignoring the {@link Locale}.
   *
   * @param locale is the {@link Locale}.
   * @return the message.
   */
  String getMessage(Locale locale);

  /**
   * This method gets optional details for this message. E.g. in case of an exception this can be the
   * stacktrace.
   *
   * @return the details or <code>null</code> if no additional details are available.
   */
  String getDetails();

  /**
   * This method gets the type of this {@link Message}. This should be one of the following options:
   * <ul>
   * <li>{@value #TYPE_VALIDATION_FAILURE} - for a {@link net.sf.mmm.util.validation.api.ValidationFailure}.</li>
   * <li>{@value #TYPE_USER_ERROR} - for an {@link net.sf.mmm.util.exception.api.NlsThrowable error} caused by the
   * end-user (e.g. by providing invalid data). Unlike {@link #TYPE_VALIDATION_FAILURE} this is an actual
   * error that has been detected after a successful validation.</li>
   * <li>{@value #TYPE_TECHNICAL_ERROR} - for an {@link net.sf.mmm.util.exception.api.NlsThrowable error} related to
   * a technical problem (e.g. programming failure such as {@link NullPointerException} or operational error
   * like {@link java.net.ConnectException}).</li>
   * <li>{@value #TYPE_WARNING} - for a warning such as "The hit-list for your query has been truncated."). In
   * such case it should be possible to continue the use-case or operation normally.</li>
   * <li>{@value #TYPE_INFORMATION} - for additional information - e.g. a hint.</li>
   * </ul>
   *
   * @return the message type.
   */
  String getType();

  /**
   * {@inheritDoc}
   *
   * @return the {@link UUID} if this {@link Message}. Will typically only be available if {@link #getType()
   *         type} is {@link #TYPE_TECHNICAL_ERROR} or {@link #TYPE_USER_ERROR}. Will be <code>null</code> if
   *         not available.
   */
  @Override
  UUID getUuid();

}
