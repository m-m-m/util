/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.search.engine.api.SearchQueryErrorHandler;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * This is an implementation of {@link SearchQueryErrorHandler} that collects all errors.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchQueryErrorHandlerCollectErrors implements SearchQueryErrorHandler {

  /** @see #getMessages() */
  private final List<NlsObject> messages;

  /**
   * The constructor.
   */
  public SearchQueryErrorHandlerCollectErrors() {

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
   * This method gets the collected {@link NlsObject messages}.
   * 
   * @return the messages.
   */
  public List<NlsObject> getMessages() {

    return this.messages;
  }

  /**
   * This inner class represents an {@link SearchQueryErrorHandler#handleError(String, int, int, NlsObject)
   * error event}.
   */
  public static class Error {

    /** @see #getQuery() */
    private final String query;

    /** @see #getStartIndex() */
    private final int startIndex;

    /** @see #getEndIndex() */
    private final int endIndex;

    /** @see #getMessage() */
    private final NlsObject message;

    /**
     * The constructor.
     * 
     * @param query is the query string.
     * @param startIndex the start index in query.
     * @param endIndex the end index in query.
     * @param message the error message.
     */
    public Error(String query, int startIndex, int endIndex, NlsObject message) {

      super();
      this.query = query;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
      this.message = message;
    }

    /**
     * This method gets the query.
     * 
     * @return the query.
     */
    public String getQuery() {

      return this.query;
    }

    /**
     * This method gets the start index.
     * 
     * @return the start index.
     */
    public int getStartIndex() {

      return this.startIndex;
    }

    /**
     * This method gets the end index.
     * 
     * @return the end index.
     */
    public int getEndIndex() {

      return this.endIndex;
    }

    /**
     * This method gets the {@link NlsObject message}.
     * 
     * @return the {@link NlsObject message}.
     */
    public NlsObject getMessage() {

      return this.message;
    }

  }

}
