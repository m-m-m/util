/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view;

import java.io.IOException;

import org.w3c.dom.Element;

import net.sf.mmm.search.base.SearchConfigurator;
import net.sf.mmm.search.engine.impl.LuceneSearchEngine;
import net.sf.mmm.search.impl.LuceneSearchConfigurator;
import net.sf.mmm.util.xml.base.DomUtilImpl;

/**
 * This is the controller {@link javax.servlet.Servlet servlet} for the search
 * using lucene as underlying search-engine.
 * 
 * @see GenericSearchServlet
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchServlet extends AbstractSearchServlet {

  /** The UID for serialization. */
  private static final long serialVersionUID = 6559795446604858538L;

  /**
   * The constructor.
   */
  public LuceneSearchServlet() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure(Element xmlConfiguration) {

    try {
      Element searchEngineElement = DomUtilImpl.requireFirstChildElement(xmlConfiguration,
          SearchConfigurator.XML_TAG_SEARCH_ENGINE);
      LuceneSearchConfigurator configurator = new LuceneSearchConfigurator();
      LuceneSearchEngine searchEngine = configurator.createSearchEngine(searchEngineElement);
      setSearchEngine(searchEngine);
      super.configure(xmlConfiguration);
    } catch (IOException e) {
      // tomcat is too stupid to print out the right root cause so we include
      // the message of "e"
      throw new IllegalStateException("Failed to configure search engine: " + e.getMessage(), e);
    }
  }
}
