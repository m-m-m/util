/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.nls.impl.ResourceMissingException;
import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.ComplexSearchQuery;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.search.engine.api.SearchResultPage;

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

  /** @see #configure(Element) */
  private SearchViewConfiguration configuration;

  /** The search engine. */
  private ManagedSearchEngine searchEngine;

  /** The name of the JSP to dispatch for regular search. */
  private String searchJsp;

  /** The name of the JSP to dispatch for details of an entry. */
  private String detailsJsp;

  /** The URL-pattern of the servlet-mapping for the details view. */
  private String detailsPath;

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
   * @param searchEngine
   *        the searchEngine to set
   */
  public void setSearchEngine(ManagedSearchEngine searchEngine) {

    this.searchEngine = searchEngine;
  }

  /**
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {

    super.init(config);
    try {
      this.searchJsp = config.getInitParameter("search-jsp");
      if (this.searchJsp == null) {
        this.searchJsp = "search.jsp";
      }
      this.detailsJsp = config.getInitParameter("details-jsp");
      if (this.detailsJsp == null) {
        this.detailsJsp = "details.jsp";
      }
      this.detailsPath = config.getInitParameter("details-path");
      if (this.detailsPath == null) {
        this.detailsPath = "/details";
      }
      String configPath = config.getInitParameter("config-file");
      if (configPath == null) {
        throw new ResourceMissingException(PARAM_CONFIG_FILE);
      }
      if (configPath.startsWith("~/")) {
        String home = System.getProperty("user.home");
        configPath = home + configPath.substring(1);
      }
      File configFile = new File(configPath);
      Document xmlConfigDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
          configFile);
      configure(xmlConfigDoc.getDocumentElement());
    } catch (Exception e) {
      throw new ServletException("Initialization failed!", e);
    }
  }

  /**
   * This method configures this servlet with the given
   * <code>xmlConfiguration</code>.
   * 
   * @param xmlConfiguration
   *        is the top-level element of the XML-configuration.
   */
  protected void configure(Element xmlConfiguration) {

    this.configuration = new SearchViewConfiguration(xmlConfiguration);
  }

  /**
   * This method extends the given <code>complexQuery</code> with a query for
   * the given <code>property</code>.
   * 
   * @param complexQuery
   *        is the existing query to extend.
   * @param property
   *        is the name of the property to add to the query.
   * @param value
   *        is the value to search in the given <code>property</code>.
   */
  private void appendFieldQuery(ComplexSearchQuery complexQuery, String property, String value) {

    if (value.length() > 0) {
      SearchQuery query = this.searchEngine.getQueryBuilder().createPhraseQuery(property, value);
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

    String jspName;
    if (request.getServletPath().equals(this.detailsPath)) {
      jspName = this.detailsJsp;
      String idString = searchContext.getId();
      if (idString.length() > 0) {
        try {
          SearchEntry entry = this.searchEngine.getEntry(idString);
          searchContext.setEntry(entry);
        } catch (Exception e) {
          searchContext.setException(e);
        }
      }
    } else {
      jspName = this.searchJsp;
      try {
        SearchQueryBuilder queryBuilder = this.searchEngine.getQueryBuilder();
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
          SearchResultPage result = this.searchEngine.search(query, searchContext.getPageNumber(),
              searchContext.getHitsPerPage());
          searchContext.setResultPage(result);
          // TODO: remove this hack - only for testing!!!
          this.searchEngine.refresh();
          System.out.println("refreshing search...");
        }
      } catch (Exception e) {
        searchContext.setException(e);
        // request.getRequestDispatcher("error.jsp").include(request,
        // response);
        // return;
      }
    }
    request.getRequestDispatcher(jspName).include(request, response);
  }

}
