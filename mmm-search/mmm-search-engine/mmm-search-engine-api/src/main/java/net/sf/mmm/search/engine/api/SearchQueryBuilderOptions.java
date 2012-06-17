/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface for the additional options for the {@link SearchQueryBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchQueryBuilderOptions {

  /**
   * This method determines if search-terms should be required.
   * 
   * @return <code>true</code> if regular terms of the query are required ("foo bar" -> "+foo +bar"),
   *         <code>false</code> otherwise ("foo bar" -> "foo OR bar").
   */
  boolean isRequireTerms();

  /**
   * This method gets the {@link SearchQueryErrorHandler}.
   * 
   * @return the {@link SearchQueryErrorHandler}.
   */
  SearchQueryErrorHandler getErrorHandler();

}
