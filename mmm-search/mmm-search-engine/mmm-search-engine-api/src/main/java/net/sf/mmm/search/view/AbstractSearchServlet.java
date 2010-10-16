/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationReader;
import net.sf.mmm.util.component.api.IocContainer;

/**
 * This is the abstract base implementation of the controller
 * {@link javax.servlet.Servlet servlet} of the search.
 * 
 * @author Joerg Hohwiller
 */
public abstract class AbstractSearchServlet extends HttpServlet {

  /** The UID for serialization. */
  private static final long serialVersionUID = -2197345445602977147L;

  /** @see #init(ServletConfig) */
  private static final String PARAM_CONFIG_FILE = "config-file";

  /** The name of the view for the actual search. */
  private String searchView;

  /** The name of the view for the details. */
  private String detailsView;

  /** @see #getConfiguration() */
  private SearchEngineConfiguration configuration;

  /** The search engine. */
  private ManagedSearchEngine searchEngine;

  /**
   * The constructor.
   */
  public AbstractSearchServlet() {

    super();
  }

  /**
   * @return the searchEngine
   */
  public ManagedSearchEngine getSearchEngine() {

    return this.searchEngine;
  }

  /**
   * This method gets the {@link IocContainer} used to manage components with
   * their implementation. The {@link IocContainer} will be created and
   * initialized on the first call of this method.
   * 
   * @return the {@link IocContainer}.
   */
  protected abstract IocContainer getIocContainer();

  /**
   * This method gets the custom configuration for the search.
   * 
   * @return the {@link SearchEngineConfiguration}.
   */
  protected SearchEngineConfiguration getConfiguration() {

    return this.configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {

    super.init(config);
    try {
      this.detailsView = config.getInitParameter("details-view");
      if (this.detailsView == null) {
        this.detailsView = "details";
      }
      this.searchView = config.getInitParameter("search-view");
      if (this.searchView == null) {
        this.searchView = "search";
      }
      String configPath = config.getInitParameter(PARAM_CONFIG_FILE);
      if (configPath == null) {
        configPath = SearchConfiguration.DEFAULT_CONFIGURATION_URL;
      }
      IocContainer container = getIocContainer();
      SearchEngineConfigurationReader reader = container
          .getComponent(SearchEngineConfigurationReader.class);
      this.configuration = reader.readConfiguration(configPath);
      this.searchEngine = container.getComponent(SearchEngineBuilder.class).createSearchEngine(
          this.configuration.getSearchIndex());
    } catch (Exception e) {
      throw new ServletException("Initialization failed!", e);
    }
  }

  /**
   * This method extends the given <code>complexQuery</code> with a query for
   * the given <code>property</code>.
   * 
   * @param complexQuery is the existing query to extend.
   * @param property is the name of the property to add to the query.
   * @param value is the value to search in the given <code>property</code>.
   */
  private void appendFieldQuery(ComplexSearchQuery complexQuery, String property, String value) {

    if (value.length() > 0) {
      SearchQuery query = getSearchEngine().getQueryBuilder().createPhraseQuery(property, value);
      complexQuery.addRequiredQuery(query);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // you need to set URIEncoding = "UTF-8" or better
    // useBodyEncodingForURI = "true" for the connector in the server.xml of
    // your tomcat!
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");

    SearchViewContext searchContext = new SearchViewContext(request);
    searchContext.setConfiguration(this.configuration);
    // request.setAttribute(SearchContext.ATTRIBUTE_NAME, searchContext);

    String viewName = request.getServletPath().substring(1);
    if (viewName.equals(this.detailsView)) {
      String idString = searchContext.getId();
      if (idString.length() > 0) {
        try {
          SearchEntry entry = getSearchEngine().getEntry(idString);
          searchContext.setEntry(entry);
        } catch (Exception e) {
          searchContext.setException(e);
        }
      }
    } else if (viewName.equals(this.searchView)) {
      try {
        SearchQueryBuilder queryBuilder = getSearchEngine().getQueryBuilder();
        ComplexSearchQuery mainQuery = queryBuilder.createComplexQuery();
        SearchQuery query = null;
        String textQuery = searchContext.getQuery();
        if (textQuery.length() > 0) {
          query = queryBuilder.parseStandardQuery(textQuery);
          mainQuery.addRequiredQuery(query);
        }
        appendFieldQuery(mainQuery, SearchEntry.PROPERTY_AUTHOR, searchContext.getAuthor());
        appendFieldQuery(mainQuery, SearchEntry.PROPERTY_TITLE, searchContext.getTitle());
        appendFieldQuery(mainQuery, SearchEntry.PROPERTY_TYPE, searchContext.getType());
        appendFieldQuery(mainQuery, SearchEntry.PROPERTY_SOURCE, searchContext.getSource());
        int querySize = mainQuery.getSubQueryCount();
        if (querySize > 0) {
          if ((query == null) || (querySize > 1)) {
            query = mainQuery;
          }
          // perform search...
          int totalHitCount = searchContext.getTotalHitCount();
          SearchResultPage result;
          int hitsPerPage = searchContext.getHitsPerPage();
          if (totalHitCount == -1) {
            // initial search
            result = getSearchEngine().search(query, hitsPerPage);
          } else {
            // subsequent search
            int pageNumber = searchContext.getPageNumber();
            result = getSearchEngine().search(query, hitsPerPage, pageNumber, totalHitCount);
          }
          searchContext.setResultPage(result);
        }
      } catch (Exception e) {
        searchContext.setException(e);
        // request.getRequestDispatcher("error.jsp").include(request,
        // response);
        // return;
      }
    }
    String jspName;
    int lastSlash = viewName.lastIndexOf('/');
    if (lastSlash >= 0) {
      jspName = viewName.substring(lastSlash) + ".jsp";
    } else {
      jspName = viewName + ".jsp";
    }
    request.getRequestDispatcher(jspName).include(request, response);
  }

}
