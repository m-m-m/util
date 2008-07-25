/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.w3c.dom.Element;

import net.sf.mmm.search.base.AbstractSearchConfigurator;
import net.sf.mmm.search.engine.impl.LuceneSearchEngine;
import net.sf.mmm.search.indexer.impl.LuceneSearchIndexer;
import net.sf.mmm.util.xml.base.DomUtilImpl;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.base.SearchConfigurator} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchConfigurator extends AbstractSearchConfigurator {

  /**
   * The name of the XML element for the configuration of the
   * {@link org.apache.lucene.analysis.Analyzer analyzer} to use.
   */
  public static final String XML_TAG_ANALYZER = "analyzer";

  /**
   * The name of the XML attribute that contains the full-qualified class-name
   * of the {@link org.apache.lucene.analysis.Analyzer analyzer} to use.
   */
  public static final String XML_ATR_ANALYZER_CLASS = "class";

  /**
   * The name of the XML attribute that contains the <code>path</code> to the
   * directory with the {@link #XML_TAG_INDEX search-index}.
   */
  public static final String XML_ATR_INDEX_PATH = "path";

  /**
   * The name of the XML attribute for the <code>update</code> flag. It
   * contains a boolean value, that determines if the index will be updated
   * (true) or rebuild every time (false). The default is <code>false</code>.
   */
  public static final String XML_ATR_INDEX_UPDATE = "update";

  /**
   * The name of the XML attribute for the <code>leading-wildcard</code> flag.
   * It contains a boolean value, that determines if leading wildcards are
   * supported in queries (true) or will be ignored (false). The default is
   * <code>false</code>.
   */
  public static final String XML_ATR_SEARCH_LEADINGWILDCARD = "leading-wildcard";

  /**
   * The constructor.
   * 
   */
  public LuceneSearchConfigurator() {

    super();
  }

  /**
   * This method gets the index-path from the configuration given by
   * <code>element</code>.
   * 
   * @param element is the element containing the index-path.
   * @return the index-path.
   */
  private String getIndexPath(Element element) {

    Element indexElement = DomUtilImpl.requireFirstChildElement(element, XML_TAG_INDEX);
    String indexPath = indexElement.getAttribute(XML_ATR_INDEX_PATH);
    if (indexPath.length() == 0) {
      throw new IllegalArgumentException("Missing attribute " + XML_ATR_INDEX_PATH + " in element "
          + XML_TAG_INDEX + "!");
    }
    if (indexPath.startsWith("~/")) {
      indexPath = System.getProperty("user.home") + indexPath.substring(1);
    }
    return indexPath;
  }

  /**
   * This method creates an analyzer from the configuration given by
   * <code>element</code>.
   * 
   * @param element is the element containing the configuration of the analyzer.
   * @return a new analyzer according to the given configuration.
   */
  private Analyzer createAnalyzer(Element element) {

    Element analyzerElement = DomUtilImpl.getFirstChildElement(element, XML_TAG_ANALYZER);
    Analyzer analyzer = null;
    if (analyzerElement != null) {
      if (analyzerElement.hasAttribute(XML_ATR_ANALYZER_CLASS)) {
        String analyzerClass = analyzerElement.getAttribute(XML_ATR_ANALYZER_CLASS);
        try {
          analyzer = (Analyzer) Class.forName(analyzerClass).newInstance();
        } catch (Exception e) {
          throw new IllegalArgumentException("Illegal analyzer: " + analyzerClass, e);
        }
      }
    }
    if (analyzer == null) {
      analyzer = new StandardAnalyzer();
    }
    return analyzer;
  }

  /**
   * This method gets the flag <code>ignore-leading-wildcards</code> from the
   * configuration given by <code>element</code>.
   * 
   * @param element is the element containing the flag.
   * @return the flag.
   */
  private boolean isIgnoreLeadingWildcards(Element element) {

    Element queryElement = DomUtilImpl.getFirstChildElement(element, XML_TAG_SEARCH);
    if (queryElement == null) {
      return true;
    } else {
      return !DomUtilImpl.getAttributeAsBoolean(queryElement, XML_ATR_SEARCH_LEADINGWILDCARD, false);
    }
  }

  /**
   * This method gets the flag <code>update</code> from the configuration
   * given by <code>element</code>.
   * 
   * @param element is the element containing the flag.
   * @return the flag.
   */
  private boolean isUpdate(Element element) {

    Element indexElement = DomUtilImpl.getFirstChildElement(element, XML_TAG_INDEX);
    if (indexElement == null) {
      return false;
    } else {
      return DomUtilImpl.getAttributeAsBoolean(indexElement, XML_ATR_INDEX_UPDATE, false);
    }
  }

  /**
   * {@inheritDoc}
   */
  public LuceneSearchIndexer createSearchIndexer(Element element) throws IOException {

    LuceneSearchIndexer indexer = new LuceneSearchIndexer();
    indexer.setAnalyzer(createAnalyzer(element));
    indexer.setIndexPath(getIndexPath(element));
    indexer.setUpdate(isUpdate(element));
    indexer.initialize();
    return indexer;
  }

  /**
   * {@inheritDoc}
   */
  public LuceneSearchEngine createSearchEngine(Element element) throws IOException {

    LuceneSearchEngine searchEngine = new LuceneSearchEngine();
    searchEngine.setAnalyzer(createAnalyzer(element));
    searchEngine.setIndexPath(getIndexPath(element));
    searchEngine.setIgnoreLeadingWildcards(isIgnoreLeadingWildcards(element));
    searchEngine.initialize();
    setupRefreshThread(element, searchEngine);
    return searchEngine;
  }

}
