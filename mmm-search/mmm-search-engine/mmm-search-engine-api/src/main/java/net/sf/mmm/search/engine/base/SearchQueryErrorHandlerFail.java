/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;
import net.sf.mmm.util.nls.api.NlsObject;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is an implementation of {@link SearchQueryErrorHandler} that throws an exception in case of any error.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryErrorHandlerFail implements SearchQueryErrorHandler {

  /**
   * The constructor.
   */
  public SearchQueryErrorHandlerFail() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleError(String query, int start, int end, NlsObject message) throws RuntimeException {

    if (message instanceof NlsRuntimeException) {
      throw (NlsRuntimeException) message;
    }
    throw new SearchQueryParseException(message.toNlsMessage());
  }

}
