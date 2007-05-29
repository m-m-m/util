/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import org.w3c.dom.Element;

import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.indexer.api.SearchIndexer;

/**
 * This is the interface for a configurator that allows to create a
 * {@link #createSearchIndexer(Element) search-indexer} or
 * {@link #createSearchEngine(Element) search-engine} from a given
 * XML-configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchConfigurator {

  /**
   * The name of the XML element for a the configuration of the search.
   */
  String XML_TAG_SEARCH_ENGINE = "search-engine";

  /**
   * This method parses the XML-configuration given by <code>element</code>
   * and creates an according {@link SearchIndexer}.
   * 
   * @param element
   *        is the XML-element containing the configuration for the requested
   *        {@link SearchIndexer}. See {@link #XML_TAG_SEARCH_ENGINE}.
   * @return a new {@link SearchIndexer} for the configuration given by
   *         <code>element</code>.
   * @throws IllegalArgumentException
   *         if the configuration is invalid.
   * @throws Exception
   *         if the {@link SearchIndexer} could NOT be created.
   */
  SearchIndexer createSearchIndexer(Element element) throws IllegalArgumentException, Exception;

  /**
   * This method parses the XML-configuration given by <code>element</code>
   * and creates an according {@link SearchEngine}.
   * 
   * @param element
   *        is the XML-element containing the configuration for the requested
   *        {@link SearchEngine}. See {@link #XML_TAG_SEARCH_ENGINE}.
   * @return a new {@link SearchEngine} for the configuration given by
   *         <code>element</code>.
   * @throws IllegalArgumentException
   *         if the configuration is invalid.
   * @throws Exception
   *         if the {@link SearchEngine} could NOT be created.
   */
  SearchEngine createSearchEngine(Element element) throws IllegalArgumentException, Exception;

}
