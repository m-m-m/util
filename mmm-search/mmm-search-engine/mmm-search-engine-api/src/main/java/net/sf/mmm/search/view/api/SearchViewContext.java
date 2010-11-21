/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This is the major interface to access all relevant attributes and components
 * for the view (user-interface) of the search.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchViewContext {

  /**
   * The key used to associate and get this object.
   */
  String KEY = SearchViewContext.class.getName();

  /**
   * This method gets the {@link SearchViewRequestParameters}.
   * 
   * @return the {@link SearchViewRequestParameters}.
   */
  SearchViewRequestParameters getRequestParameters();

  /**
   * This method gets the {@link SearchViewLogic}.
   * 
   * @return the {@link SearchViewLogic}.
   */
  SearchViewLogic getLogic();

  /**
   * This method gets the {@link Exception} that potentially occurred.
   * 
   * @return the {@link Exception} or <code>null</code> in case of success.
   */
  NlsRuntimeException getException();

  /**
   * This method gets the {@link SearchResultPage}.
   * 
   * @return the {@link SearchResultPage} or <code>null</code> if no search has
   *         been performed.
   */
  SearchResultPage getResultPage();

  /**
   * This method gets the single {@link SearchEntry} for viewing details.
   * 
   * @see SearchViewConfiguration#getDetailsPath()
   * 
   * @return the {@link SearchEntry} or <code>null</code> if no details shall be
   *         displayed.
   */
  SearchEntry getEntry();

}
