/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import javax.servlet.ServletRequest;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.view.api.SearchViewConfiguration;
import net.sf.mmm.search.view.api.SearchViewContext;
import net.sf.mmm.search.view.api.SearchViewLogic;
import net.sf.mmm.search.view.api.SearchViewRequestParameters;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This class contains represents the context of a search-request.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchViewContextBean implements SearchViewContext {

  /** @see #getRequestParameters() */
  private final SearchViewRequestParameters requestParameters;

  /** @see #getLogic() */
  private final SearchViewLogic logic;

  /** @see #getResultPage() */
  private SearchResultPage resultPage;

  /** @see #getResultPage() */
  private SearchEntry entry;

  /** @see #getException() */
  private NlsRuntimeException exception;

  /** @see #getViewConfiguration() */
  private SearchViewConfiguration viewConfiguration;

  /**
   * The constructor. <br>
   * It will fill all parameters except for {@link #setResultPage(SearchResultPage) result-page} and
   * {@link #setException(NlsRuntimeException) exception}. <br>
   * ATTENTION: This constructor will automatically {@link ServletRequest#setAttribute(String, Object) set}
   * {@link SearchViewContextBean itself} (this) as attribute of the given <code>request</code>.
   * 
   * @param request is the servlet request.
   * @param logic is the {@link SearchViewLogic}.
   * @param viewConfiguration is the {@link SearchViewConfiguration}.
   */
  public SearchViewContextBean(ServletRequest request, SearchViewLogic logic, SearchViewConfiguration viewConfiguration) {

    super();
    this.logic = logic;
    this.viewConfiguration = viewConfiguration;
    this.requestParameters = new SearchViewRequestParametersBean(request);
    request.setAttribute(KEY, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchViewRequestParameters getRequestParameters() {

    return this.requestParameters;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchViewLogic getLogic() {

    return this.logic;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchViewConfiguration getViewConfiguration() {

    return this.viewConfiguration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
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
  @Override
  public NlsRuntimeException getException() {

    return this.exception;
  }

  /**
   * @param newException the exception to set
   */
  public void setException(NlsRuntimeException newException) {

    this.exception = newException;
  }

}
