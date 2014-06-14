/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api;

import net.sf.mmm.search.NlsBundleSearchRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is a technical exception that can be thrown by the {@link net.sf.mmm.search.engine.api.SearchEngine}
 * and related objects. The {@link net.sf.mmm.search.engine.api.SearchEngine} implementation is suggested to
 * be tolerant (e.g. accept malformed queries). Anyways in some situations (e.g. {@link java.io.IOException})
 * it can be necessary to throw an exception. Therefore this is the suggested exception type to be used.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class SearchException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2903854338698104923L;

  /**
   * Key for the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String) argument} {@value}.
   */
  protected static final String KEY_ENTRY = "entry";

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and
   *        should be in English language.
   */
  public SearchException(String internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public SearchException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public SearchException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * @return the default bundle {@link NlsBundleSearchRoot}.
   */
  protected static NlsBundleSearchRoot getBundle() {

    return createBundle(NlsBundleSearchRoot.class);
  }

}
