/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

/**
 * This interface contains the servlet-specific configuration of the view.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchViewConfiguration {

  /**
   * The name of the
   * {@link javax.servlet.ServletConfig#getInitParameter(String)
   * servlet-parameter} for the
   * {@link net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader#loadConfiguration(String)
   * configuration-URL}.
   */
  String PARAMETER_CONFIGURATION_URL = "configuration-url";

  /**
   * The name of the
   * {@link javax.servlet.ServletConfig#getInitParameter(String)
   * servlet-parameter} for the {@link #getSearchPath() search-path}.
   */
  String PARAMETER_SEARCH_PATH = "search-path";

  /** The default for {@link #getSearchPath()}. */
  String DEFAULT_SEARCH_PATH = "/search";

  /**
   * The name of the
   * {@link javax.servlet.ServletConfig#getInitParameter(String)
   * servlet-parameter} for the {@link #getDetailsPath() details-path}.
   */
  String PARAMETER_DETAILS_PATH = "details-path";

  /** The default for {@link #getDetailsPath()}. */
  String DEFAULT_DETAILS_PATH = "/details";

  /**
   * The name of the
   * {@link javax.servlet.ServletConfig#getInitParameter(String)
   * servlet-parameter} for the {@link #getErrorPath() error-path}.
   */
  String PARAMETER_ERROR_PATH = "error-path";

  /**
   * The {@link javax.servlet.http.HttpServletRequest#getServletPath()
   * servlet-path} for the view of the actual search (enter query and show
   * results).
   * 
   * @see #DEFAULT_SEARCH_PATH
   * 
   * @return the search path.
   */
  String getSearchPath();

  /**
   * The {@link javax.servlet.http.HttpServletRequest#getServletPath()
   * servlet-path} for the view of the details of a particular
   * {@link net.sf.mmm.search.engine.api.SearchHit}.
   * 
   * @see #DEFAULT_DETAILS_PATH
   * 
   * @return the search path.
   */
  String getDetailsPath();

  /**
   * This method get the name of the path to
   * {@link javax.servlet.ServletRequest#getRequestDispatcher(String) dispatch}
   * to in case of an error (e.g. "error.jsp") or <code>null</code> to dispatch
   * to the current view even in case of an error.
   * 
   * @return the error view.
   */
  String getErrorPath();

}
