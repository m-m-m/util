/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.cli.NlsBundleUtilCliRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * A {@link CliException} is thrown, if the commandline arguments are invalid for a specific main-program.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class CliException extends NlsRuntimeException {

  private static final long serialVersionUID = 8814403183579508077L;

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
