/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import javax.servlet.ServletRequest;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;

/**
 * This class contains represents the context of a search-request.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchViewContext {

  /**
   * the name of this object as {@link ServletRequest#getAttribute(String)
   * request attribute}.
   */
  public static final String ATTRIBUTE_NAME = SearchViewContext.class.getName();

  /** @see SearchEntry#PROPERTY_TEXT */
  public static final String PARAM_QUERY = "query";

  /** @see SearchResultPage#getPageIndex() */
  public static final String PARAM_PAGE = "page";

  /** @see SearchResultPage#getHitsPerPage() */
  public static final String PARAM_HITS_PER_PAGE = "hits";

  /** @see SearchResultPage#getTotalHitCount() */
  public static final String PARAM_TOTAL_HIT_COUNT = "totalHits";

  /** the source */
  public static final String PARAM_SOURCE = "source";

  /** @see SearchEntry#PROPERTY_AUTHOR */
  public static final String PARAM_AUTHOR = SearchEntry.PROPERTY_AUTHOR;

  /** @see SearchEntry#PROPERTY_TYPE */
  public static final String PARAM_TYPE = SearchEntry.PROPERTY_TYPE;

  /** @see SearchEntry#PROPERTY_TITLE */
  public static final String PARAM_TITLE = SearchEntry.PROPERTY_TITLE;

  /** @see net.sf.mmm.search.engine.api.SearchHit#getEntryId() */
  public static final String PARAM_ID = "id";

  /** @see #getServletRequest() */
  private final ServletRequest servletRequest;

  /** @see #getConfiguration() */
  private SearchEngineConfiguration configuration;

  /** @see #getSearchEngine() */
  private ManagedSearchEngine searchEngine;

  /** @see #getPageNumber() */
  private int pageNumber;

  /** @see #getResultPage() */
  private SearchResultPage resultPage;

  /** @see #getHitsPerPage() */
  private int hitsPerPage;

  /** @see #getTotalHitCount() */
  private int totalHitCount;

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
   * {@link SearchViewContext itself} (this) as attribute of the given
   * <code>request</code>.
   * 
   * @param request is the servlet request.
   */
  public SearchViewContext(ServletRequest request) {

    super();
    this.servletRequest = request;
    this.pageNumber = getParameterAsInt(PARAM_PAGE, 0, 0, 10000);
    this.hitsPerPage = getParameterAsInt(PARAM_HITS_PER_PAGE,
        SearchResultPage.DEFAULT_HITS_PER_PAGE, 2, 100);
    this.totalHitCount = getParameterAsInt(PARAM_TOTAL_HIT_COUNT, -1, 0, Integer.MAX_VALUE);
    this.servletRequest.setAttribute(ATTRIBUTE_NAME, this);
  }

  /**
   * This method gets the {@link ServletRequest#getParameter(String) parameter}
   * with the given <code>name</code> from the {@link #getServletRequest()
   * request}.
   * 
   * @param name is the name of the requested parameter.
   * @return the parameter as {@link String#trim() trimmed} string. If the
   *         parameter is NOT set, the empty string is returned.
   */
  private String getParameter(String name) {

    String value = this.servletRequest.getParameter(name);
    if (value == null) {
      value = "";
    } else {
      value = value.trim();
    }
    return value;
  }

  /**
   * This method gets the {@link ServletRequest#getParameter(String) parameter}
   * with the given <code>name</code> from the {@link #getServletRequest()
   * request}.
   * 
   * @param name is the name of the requested parameter.
   * @param defaultValue is returned if the parameter is NOT set or invalid.
   * @param minimum is the minimum allowed value.
   * @param maximum is the maximum allowed value.
   * @return the parameter as integer. If the parameter is NOT set, the
   *         <code>defaultValue</code> is returned.
   */
  private int getParameterAsInt(String name, int defaultValue, int minimum, int maximum) {

    String value = this.servletRequest.getParameter(name);
    int result = defaultValue;
    if (value != null) {
      try {
        int num = Integer.parseInt(value);
        if (num < minimum) {
          result = minimum;
        } else if (num > maximum) {
          result = maximum;
        } else {
          result = num;
        }
      } catch (NumberFormatException e) {
        // ignore
      }
    }
    return result;
  }

  /**
   * This method gets the {@link SearchViewContext} from the given
   * <code>request</code>.
   * 
   * @param request is the request where to get the parameters from.
   * @return the parameters instance.
   */
  public static SearchViewContext get(ServletRequest request) {

    SearchViewContext result = (SearchViewContext) request.getAttribute(ATTRIBUTE_NAME);
    if (result == null) {
      throw new IllegalArgumentException("Request does NOT contain attribute: " + ATTRIBUTE_NAME);
    }
    return result;
  }

  /**
   * @return the servletRequest
   */
  public ServletRequest getServletRequest() {

    return this.servletRequest;
  }

  /**
   * This method gets the {@link SearchEngineConfiguration}.
   * 
   * @return the {@link SearchEngineConfiguration}.
   */
  public SearchEngineConfiguration getConfiguration() {

    return this.configuration;
  }

  /**
   * @param configuration the configuration to set
   */
  public void setConfiguration(SearchEngineConfiguration configuration) {

    this.configuration = configuration;
  }

  /**
   * This method gets the {@link ManagedSearchEngine}.
   * 
   * @return the {@link ManagedSearchEngine}.
   */
  public ManagedSearchEngine getSearchEngine() {

    return this.searchEngine;
  }

  /**
   * @param searchEngine is the searchEngine to set
   */
  public void setSearchEngine(ManagedSearchEngine searchEngine) {

    this.searchEngine = searchEngine;
  }

  /**
   * @return the resultPage
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
   * @return the entry
   */
  public SearchEntry getEntry() {

    return this.entry;
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.search.engine.api.SearchResultPage#getPageIndex()
   * page-index} from the request parameters.
   * 
   * @return the pageNumber. Will be <code>0</code> if NOT set in request.
   */
  public int getPageNumber() {

    return this.pageNumber;
  }

  /**
   * This method gets the number of {@link SearchResultPage#getHitsPerPage()
   * hits per page} from the request parameters.
   * 
   * @return the hitsPerPage. Will be
   *         {@link SearchResultPage#DEFAULT_HITS_PER_PAGE} if NOT set in
   *         request.
   */
  public int getHitsPerPage() {

    return this.hitsPerPage;
  }

  /**
   * This method gets the {@link SearchResultPage#getTotalHitCount() total
   * number of hits} from the request parameters.
   * 
   * @return the totalHitCount or <code>-1</code> if NOT set in request.
   */
  public int getTotalHitCount() {

    return this.totalHitCount;
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.search.engine.api.SearchQueryBuilder#parseStandardQuery(String)
   * query} from the request parameters.
   * 
   * @return the query. Will be the empty string if NOT set in request.
   */
  public String getQuery() {

    return getParameter(PARAM_QUERY);
  }

  /**
   * This method gets the {@link SearchEntry#PROPERTY_SOURCE source} from the
   * request parameters.
   * 
   * @return the source. Will be the empty string if NOT set in request.
   */
  public String getSource() {

    return getParameter(PARAM_SOURCE);
  }

  /**
   * This method gets the {@link SearchEntry#PROPERTY_AUTHOR author} from the
   * request parameters.
   * 
   * @return the author. Will be the empty string if NOT set in request.
   */
  public String getAuthor() {

    return getParameter(PARAM_AUTHOR);
  }

  /**
   * This method gets the {@link SearchEntry#PROPERTY_TITLE title} from the
   * request parameters.
   * 
   * @return the title. Will be the empty string if NOT set in request.
   */
  public String getTitle() {

    return getParameter(PARAM_TITLE);
  }

  /**
   * This method gets the {@link SearchEntry#PROPERTY_TYPE type} from the
   * request parameters.
   * 
   * @return the type. Will be the empty string if NOT set in request.
   */
  public String getType() {

    return getParameter(PARAM_TYPE);
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() ID} from the
   * request parameters.
   * 
   * @return the id. Will be the empty string if NOT set in request.
   */
  public String getId() {

    return getParameter(PARAM_ID);
  }

  /**
   * This method get the display title from the given <code>searchEntry</code>.
   * 
   * @param searchEntry is the entry where to get the title from.
   * 
   * @return the display title of the {@link #getEntry() entry}.
   */
  public static String getEntryTitle(SearchEntry searchEntry) {

    String uri = searchEntry.getUri();
    String filename;
    if (uri == null) {
      filename = "undefined";
    } else {
      int lastSlash = uri.lastIndexOf('/');
      filename = uri.substring(lastSlash + 1);
    }
    String title = searchEntry.getTitle();
    if (title == null) {
      return filename;
    } else {
      StringBuffer buffer = new StringBuffer(filename);
      buffer.append(" (");
      buffer.append(title);
      buffer.append(')');
      return buffer.toString();
    }
  }

  /**
   * @param newEntry the entry to set
   */
  public void setEntry(SearchEntry newEntry) {

    this.entry = newEntry;
  }

  /**
   * @return the exception
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
