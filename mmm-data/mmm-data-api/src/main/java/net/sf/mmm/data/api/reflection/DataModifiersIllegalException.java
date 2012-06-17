/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import java.util.Map;

/**
 * This is the exception thrown if some
 * {@link net.sf.mmm.data.api.reflection.DataModifiers} should be created with
 * an illegal combination of flags.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class DataModifiersIllegalException extends DataReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -9096716721784689821L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments}.
   */
  public DataModifiersIllegalException(String internationalizedMessage,
      Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments}.
   */
  public DataModifiersIllegalException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public DataModifiersIllegalException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   */
  public DataModifiersIllegalException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

}
