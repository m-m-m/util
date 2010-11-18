/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import javax.servlet.ServletRequest;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.view.api.SearchViewContext;
import net.sf.mmm.search.view.api.SearchViewLogic;
import net.sf.mmm.search.view.api.SearchViewRequestParameters;

/**
 * This class contains represents the context of a search-request.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchViewContextBean implements SearchViewContext {

  /** @see #getRequestParameters() */
  private final SearchViewRequestParameters requestParameters;

  /** @see #getLogic() */
  private final SearchViewLogic dependencies;

  /** @see #getResultPage() */
  private SearchResultPage resultPage;

  /** @see #getResultPage() */
  private SearchEntry entry;

  /** @see #getException() */
  private Exception exception;

  /**
   * The constructor.<br>
   * It will fill all parameters except for
   * {@link #setResultPage(SearchResultPage) result-page} and
   * {@link #setException(Exception) exception}.<br>
   * ATTENTION: This constructor will automatically
   * {@link ServletRequest#setAttribute(String, Object) set}
   * {@link SearchViewContextBean itself} (this) as attribute of the given
   * <code>request</code>.
   * 
   * @param request is the servlet request.
   * @param dependencies are the {@link SearchViewLogic}.
   */
  public SearchViewContextBean(ServletRequest request, SearchViewLogic dependencies) {

    super();
    this.dependencies = dependencies;
    this.requestParameters = new SearchViewRequestParametersBean(request);
    request.setAttribute(KEY, this);
  }

  /**
   * {@inheritDoc}
   */
  public SearchViewRequestParameters getRequestParameters() {

    return this.requestParameters;
  }

  /**
   * {@inheritDoc}
   */
  public SearchViewLogic getLogic() {

    return this.dependencies;
  }

  /**
   * {@inheritDoc}
   */
  public SearchResultPage getResultPage() {

    return this.resultPage;
  }

  /**
   * @param newResultPage the resultPage to set
   */
  public void setResultPage(SearchResultPage newResultPage) {

    this.resultPage = newResultPage;
  }

  /**
   * {@inheritDoc}
   */
  public SearchEntry getEntry() {

    return this.entry;
  }

  /**
   * @param newEntry the entry to set
   */
  public void setEntry(SearchEntry newEntry) {

    this.entry = newEntry;
  }

  /**
   * {@inheritDoc}
   */
  public Exception getException() {

    return this.exception;
  }

  /**
   * @param newException the exception to set
   */
  public void setException(Exception newException) {

    this.exception = newException;
  }

}
