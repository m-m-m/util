/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.api;

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
   * @param internaitionalizedMessage
   * @param arguments
   */
  public PersistenceException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested
   * @param internaitionalizedMessage
   * @param arguments
   */
  public PersistenceException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
