/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.util.Map;

import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a something goes wrong about values. This can be an invalid "casting", a parse
 * error, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class ValueException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8445209659250789499L;

  /**
   * The constructor.
   * 
   * @see #toMap(String, Object, String, Object)
   * 
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   * @param arguments are the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String) arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public ValueException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @see #toMap(String, Object, String, Object)
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   * @param arguments are the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String) arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public ValueException(Throwable nested, String internationalizedMessage, Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

}
