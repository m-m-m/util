/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.base.NlsRuntimeException;
import net.sf.mmm.search.engine.api.SearchEngine;

/**
 * This is a technical exception that can be thrown by the {@link SearchEngine}
 * and related objects. The {@link SearchEngine} implementation is suggested to
 * be tolerant (e.g. accept malformed queries). Anyways in some situations (e.g.
 * {@link java.io.IOException}) it can be necessary to throw an exception.
 * Therefore this is the suggested exception type to be used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2903854338698104923L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object...)
   */
  public SearchException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object...)
   */
  public SearchException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(NlsMessage)
   */
  public SearchException(NlsMessage internationalizedMessage) {

    super(internationalizedMessage);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, NlsMessage)
   */
  public SearchException(Throwable nested, NlsMessage internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

}
