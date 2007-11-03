/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.w3c.dom.Element;

import net.sf.mmm.search.base.SearchConfigurator;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.util.exception.ResourceMissingException;
import net.sf.mmm.util.xml.DomUtil;

/**
 * This is the controller {@link javax.servlet.Servlet servlet} for the search
 * using a generic {@link SearchConfigurator} to create the
 * {@link ManagedSearchEngine search-engine} from an according
 * XML-configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericSearchServlet extends AbstractSearchServlet {

  /** The UID for serialization. */
  private static final long serialVersionUID = -3795814301240648103L;

  /** @see #init(ServletConfig) */
  private static final String PARAM_SEARCH_CONFIGURATOR = "search-configurator";

  /** the configurator */
  private SearchConfigurator configurator;

  /**
   * The constructor.
   */
  public GenericSearchServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {

    super.init(config);
    String configuratorClass = config.getInitParameter(PARAM_SEARCH_CONFIGURATOR);
    if (configuratorClass == null) {
      throw new ResourceMissingException("init-parameter (web.xml): " + PARAM_SEARCH_CONFIGURATOR);
    }
    try {
      this.configurator = (SearchConfigurator) Class.forName(configuratorClass).newInstance();
    } catch (Exception e) {
      // TODO: i18n
      throw new IllegalArgumentException("Failed to instantiate " + PARAM_SEARCH_CONFIGURATOR
          + " (" + configuratorClass + ")!" + e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure(Element xmlConfiguration) {

    try {
      Element searchEngineElement = DomUtil.requireFirstChildElement(xmlConfiguration,
          SearchConfigurator.XML_TAG_SEARCH_ENGINE);
      ManagedSearchEngine searchEngine = this.configurator.createSearchEngine(searchEngineElement);
      setSearchEngine(searchEngine);
      super.configure(xmlConfiguration);
    } catch (Exception e) {
      // tomcat is too stupid to print out the right root cause so we include
      // the message of "e"
      throw new IllegalStateException("Failed to configure search engine: " + e.getMessage(), e);
    }
  }

}
