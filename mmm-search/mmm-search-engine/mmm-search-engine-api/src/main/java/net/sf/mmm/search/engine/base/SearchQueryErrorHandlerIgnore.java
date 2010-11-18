/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is an implementation of {@link SearchQueryErrorHandler} that simply
 * ignores all errors.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryErrorHandlerIgnore implements SearchQueryErrorHandler {

  /**
   * The constructor.
   */
  public SearchQueryErrorHandlerIgnore() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void handleException(String query, int start, int end, RuntimeException exception)
      throws RuntimeException {

  }

  /**
   * {@inheritDoc}
   */
  public void handleErrorMessage(String query, int start, int end, NlsMessage message)
      throws RuntimeException {

  }

}
