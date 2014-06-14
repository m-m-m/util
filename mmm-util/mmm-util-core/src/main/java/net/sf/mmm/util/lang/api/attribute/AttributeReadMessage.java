/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getMessage() message} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract interface AttributeReadMessage {

  /**
   * This method gets the actual message that describes this object. Such message should contain contextual
   * information to describe and track down the information, problem, warning or error. It is either
   * {@link net.sf.mmm.util.exception.api.NlsThrowable#isTechnical() technical} and should help operators or
   * developers to identify what happened or it will be information displayed to end-users and should be clear
   * and easy to understand. For internationalization see {@link net.sf.mmm.util.lang.api.Message} and
   * {@link net.sf.mmm.util.nls.api.NlsMessage}. In any case it should NOT contain sensitive information such
   * as passwords or (detailed) personal data.
   * 
   * @see Throwable#getMessage()
   * @see net.sf.mmm.util.exception.api.NlsThrowable
   * @see net.sf.mmm.util.lang.api.Message
   * 
   * @return the message text.
   */
  String getMessage();

}
