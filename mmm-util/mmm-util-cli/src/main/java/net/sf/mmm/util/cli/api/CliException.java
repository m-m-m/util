/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.util.Map;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link CliException} is thrown, if the commandline arguments are invalid for a specific main-program.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class CliException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8814403183579508077L;

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   */
  public CliException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   */
  public CliException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   * @param arguments are the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String) arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public CliException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   * @param arguments are the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String) arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() filled into
   *        <code>internationalizedMessage</code>}.
   */
  public CliException(Throwable nested, String internationalizedMessage, Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public CliException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public CliException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return false;
  }

  /**
   * @return the instance of {@link NlsBundleUtilCliRoot}.
   */
  protected static NlsBundleUtilCliRoot createBundle() {

    return createBundle(NlsBundleUtilCliRoot.class);
  }

}
