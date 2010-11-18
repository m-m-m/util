/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the callback interface used to
 * {@link #handleException(String, int, int, RuntimeException) handle} errors.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchQueryErrorHandler {

  /**
   * This method is called if an exception occurred during
   * {@link SearchQueryBuilder#parseStandardQuery(String) query parsing}.
   * 
   * @param query is the {@link SearchQuery} to parse a {@link String}.
   * @param start is the start {@link String#charAt(int) index} in
   *        <code>query</code>where the error occurred.
   * @param end is the end {@link String#charAt(int) index} in
   *        <code>query</code>where the error occurred.
   * @param exception is the actual {@link RuntimeException exception}.
   * @throws RuntimeException if the exception should be thrown to the caller of
   *         the {@link SearchQueryBuilder}. If no exception is thrown the given
   *         <code>exception</code> is consumed and the
   *         {@link SearchQueryBuilder} continues the parsing.
   */
  void handleException(String query, int start, int end, RuntimeException exception)
      throws RuntimeException;

  /**
   * This method is called if an error occurred during
   * {@link SearchQueryBuilder#parseStandardQuery(String) query parsing}.
   * 
   * @param query is the {@link SearchQuery} to parse a {@link String}.
   * @param start is the start {@link String#charAt(int) index} in
   *        <code>query</code>where the error occurred.
   * @param end is the end {@link String#charAt(int) index} in
   *        <code>query</code>where the error occurred.
   * @param message is the actual error message.
   * @throws RuntimeException if the error should be thrown to the caller of the
   *         {@link SearchQueryBuilder}. If no exception is thrown the given
   *         <code>message</code> is consumed and the {@link SearchQueryBuilder}
   *         continues the parsing.
   */
  void handleErrorMessage(String query, int start, int end, NlsMessage message)
      throws RuntimeException;

}
