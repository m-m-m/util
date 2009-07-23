/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is the abstract base-class for all exceptions defined by this
 * persistence API.<br>
 * The {@link javax.persistence.PersistenceException} is NOT extended because of
 * its strict rollback contract and the better I18N support offered by
 * {@link NlsRuntimeException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class PersistenceException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6126843159978956287L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   */
  public PersistenceException(String internationalizedMessage, Object... arguments) {

    super(internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   */
  public PersistenceException(Throwable nested, String internationalizedMessage,
      Object... arguments) {

    super(nested, internationalizedMessage, arguments);
  }

}
