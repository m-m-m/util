/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is an implementation of {@link SearchQueryErrorHandler} that throws an
 * exception in case of any error.
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
  public void handleException(String query, int start, int end, RuntimeException exception)
      throws RuntimeException {

    throw exception;
  }

  /**
   * {@inheritDoc}
   */
  public void handleErrorMessage(String query, int start, int end, NlsMessage message)
      throws RuntimeException {

    throw new SearchQueryParserException(message);
  }

}
