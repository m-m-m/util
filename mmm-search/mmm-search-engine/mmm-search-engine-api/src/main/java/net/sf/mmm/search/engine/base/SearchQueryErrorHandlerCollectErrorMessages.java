/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * This is an implementation of {@link SearchQueryErrorHandler} that collects all {@link #getMessages() errors
 * messages}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryErrorHandlerCollectErrorMessages implements SearchQueryErrorHandler {

  /** @see #getMessages() */
  private final List<NlsObject> messages;

  /**
   * The constructor.
   */
  public SearchQueryErrorHandlerCollectErrorMessages() {

    super();
    this.messages = new ArrayList<NlsObject>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleError(String query, int start, int end, NlsObject message) throws RuntimeException {

    this.messages.add(message);
  }

  /**
   * This method gets the {@link #handleError(String, int, int, NlsObject) collected} error {@link NlsObject
   * messages}.
   * 
   * @return the messages.
   */
  public List<NlsObject> getMessages() {

    return this.messages;
  }

}
