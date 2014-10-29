/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.persistence.NlsBundlePersistenceRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the abstract base-class for all exceptions defined by this persistence API. <br>
 * The {@link javax.persistence.PersistenceException} is NOT extended because of its strict rollback contract
 * and the better I18N support offered by {@link NlsRuntimeException}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class PersistenceException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6126843159978956287L;

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public PersistenceException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link NlsMessage#getInternationalizedMessage() internationalization} and should be in English
   *        language.
   */
  public PersistenceException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public PersistenceException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link NlsMessage#getInternationalizedMessage() internationalization} and should be in English
   *        language.
   */
  public PersistenceException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * @return the default bundle {@link NlsBundlePersistenceRoot}.
   */
  protected static NlsBundlePersistenceRoot getBundle() {

    return createBundle(NlsBundlePersistenceRoot.class);
  }
}
