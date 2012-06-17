/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import javax.servlet.ServletRequest;

import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.view.api.SearchViewRequestParameters;

/**
 * This class contains represents the context of a search-request.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchViewRequestParametersBean implements SearchViewRequestParameters {

  /** @see #getServletRequest() */
  private final ServletRequest servletRequest;

  /** @see #getPageNumber() */
  private int pageNumber;

  /** @see #getHitsPerPage() */
  private int hitsPerPage;

  /** @see #getTotalHitCount() */
  private int totalHitCount;

  /**
   * The constructor.<br>
   * 
   * @param request is the servlet request.
   */
  public SearchViewRequestParametersBean(ServletRequest request) {

    super();
    this.servletRequest = request;
    this.pageNumber = getParameterAsInt(PARAMETER_PAGE, 0, 0, 10000);
    this.hitsPerPage = getParameterAsInt(PARAMETER_HITS_PER_PAGE, SearchResultPage.DEFAULT_HITS_PER_PAGE, 2, 100);
    this.totalHitCount = getParameterAsInt(PARAMETER_TOTAL_HIT_COUNT, -1, 0, Integer.MAX_VALUE);
  }

  /**
   * This method gets the {@link ServletRequest#getParameter(String) parameter} with the given
   * <code>name</code> from the {@link #getServletRequest() request}.
   * 
   * @param name is the name of the requested parameter.
   * @return the parameter as {@link String#trim() trimmed} string. If the parameter is NOT set, the empty
   *         string is returned.
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
   * This method gets the {@link ServletRequest#getParameter(String) parameter} with the given
   * <code>name</code> from the {@link #getServletRequest() request}.
   * 
   * @param name is the name of the requested parameter.
   * @param defaultValue is returned if the parameter is NOT set or invalid.
   * @param minimum is the minimum allowed value.
   * @param maximum is the maximum allowed value.
   * @return the parameter as integer. If the parameter is NOT set, the <code>defaultValue</code> is returned.
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
   * This method gets the {@link ServletRequest}.<br/>
   * <b>NOTE:</b><br>
   * This method is only for very specific cases.
   * 
   * @return the servletRequest
   */
  public ServletRequest getServletRequest() {

    return this.servletRequest;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPageNumber() {

    return this.pageNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHitsPerPage() {

    return this.hitsPerPage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTotalHitCount() {

    return this.totalHitCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQuery() {

    return getParameter(PARAMETER_QUERY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    return getParameter(PARAMETER_SOURCE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCreator() {

    return getParameter(PARAMETER_CREATOR);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return getParameter(PARAMETER_TITLE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return getParameter(PARAMETER_TYPE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return getParameter(PARAMETER_ID);
  }

}
