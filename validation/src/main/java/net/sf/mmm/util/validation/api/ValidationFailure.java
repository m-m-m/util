/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

import java.util.Locale;

import net.sf.mmm.util.lang.api.Message;

/**
 * This is the interface for a failure of a {@link ValueValidator#validate(Object) validation}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface ValidationFailure extends Message {

  /**
   * This method gets the (optional) {@link ValueValidator#validate(Object, Object) source} of the validation.
   * It describes the origin of the given {@code value}. This may be the filename where the value was
   * read from, an XPath where the value was located in an XML document, etc. It can be used to enrich
   * {@link #getMessage() messages} displayed to end-users. This will help to find the problem easier.
   * 
   * @return the source or {@code null} if NOT available.
   */
  String getSource();

  /**
   * This method gets the message of the validation failure. This is a localized text displayed to end-users.
   * It should explain the reason of the failure in a understandable but short form. <br>
   * Some examples are "This field may not be blank." or "This value has to be in the range from 5 to 9.".
   * Depending on the {@link ValueValidator#validate(Object, Object) usage} the message may also contain the
   * name of the field or other context information in order to determine the source of the problem.
   * 
   * @return the message.
   */
  String getMessage();

  /**
   * This method gets the {@link #getMessage() message} localized for the given {@link Locale}. <br>
   * <b>ATTENTION:</b><br>
   * This method is designed for server applications with NLS. On client side (e.g. for GWT clients) only a
   * single locale may be supported at a time and this method will behave like {@link #getMessage()} ignoring
   * the lcoale.
   * 
   * @param locale is the {@link Locale}.
   * @return the message.
   */
  String getMessage(Locale locale);

  /**
   * This method gets the <em>code</em> of this {@link ValidationFailure}. The code is a stable identifier
   * that indicates the type of the failure. It can be used for automated testing in order to make the
   * test-cases independent from the actual message texts so they are maintainable and will not break e.g. if
   * typos are fixed in the messages.
   * 
   * @return the failure code.
   */
  String getCode();

}
