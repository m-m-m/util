/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.util.filter.FileFilterAdapter;
import net.sf.mmm.util.filter.FilterRuleChain;
import net.sf.mmm.util.filter.FilterRuleChainXmlParser;

/**
 * This class contains functionality to recursively walk through directories and
 * add contained files to a search index. In advance to
 * {@link DirectorySearchIndexer} this class adds the functionally to be
 * configurable via XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfiguredDirectorySearchIndexer extends DirectorySearchIndexer {

  /** The name of the XML element for a list of filters. */
  public static final String XML_TAG_FILTERS = "filters";

  /**
   * The name of the XML element for a list of
   * {@link #XML_TAG_DIRECTORY directories}.
   */
  public static final String XML_TAG_DIRECTORIES = "directories";

  /** The name of the XML element for an directory that should be indexed. */
  public static final String XML_TAG_DIRECTORY = "directory";

  /**
   * The name of the XML attribute for the <code>path</code> in the local
   * filesystem to index as {@link #XML_TAG_DIRECTORY directory}.
   */
  public static final String XML_ATR_DIRECTORY_PATH = "path";

  /**
   * The name of the XML attribute for the <code>source</code> of a
   * {@link #XML_TAG_DIRECTORY directory}.
   */
  public static final String XML_ATR_DIRECTORY_SOURCE = "source";

  /**
   * The name of the XML attribute pointing to the ID of the
   * <code>filter-chain</code> to use for indexing a
   * {@link #XML_TAG_DIRECTORY directory}.
   */
  public static final String XML_ATR_DIRECTORY_FILTER = "filter";

  /**
   * The name of the XML attribute with the <code>base-path</code> used for
   * the URI of the resources of a {@link #XML_TAG_DIRECTORY directory} to
   * index.
   */
  public static final String XML_ATR_DIRECTORY_INDEXBASEPATH = "index-base-path";

  /**
   * The constructor.
   */
  public ConfiguredDirectorySearchIndexer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param indexer
   *        is the indexer to use.
   */
  public ConfiguredDirectorySearchIndexer(SearchIndexer indexer) {

    super(indexer);
  }

  /**
   * This method builds the search-index from the given
   * <code>configurationElement</code>.
   * 
   * @param configurationElement
   *        is the XML-element containing the configuration which directories to
   *        index and which files to exclude.
   */
  public void index(Element configurationElement) {

    getLogger().debug("parsing configuration...");
    // find child-elements "filters" and "directories"...
    Element filtersElement = null;
    Element directoriesElement = null;
    NodeList childNodes = configurationElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_FILTERS.equals(element.getTagName())) {
          if (filtersElement != null) {
            throw new IllegalArgumentException("Duplicate tag: " + XML_TAG_FILTERS);
          }
          filtersElement = element;
        } else if (XML_TAG_DIRECTORIES.equals(element.getTagName())) {
          if (directoriesElement != null) {
            throw new IllegalArgumentException("Duplicate tag: " + XML_TAG_DIRECTORIES);
          }
          directoriesElement = element;
        }
      }
    }
    if (filtersElement == null) {
      throw new IllegalArgumentException("Missing tag: " + XML_TAG_FILTERS);
    }
    if (directoriesElement == null) {
      throw new IllegalArgumentException("Missing tag: " + XML_TAG_DIRECTORIES);
    }
    // parse filters...
    FilterRuleChainXmlParser parser = new FilterRuleChainXmlParser();
    Map<String, FilterRuleChain> chainMap = parser.parseChains(filtersElement);
    // parse directories and do indexing...
    getLogger().debug("Filters parsed - parsing directories and start indexing...");
    childNodes = directoriesElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (XML_TAG_DIRECTORY.equals(element.getTagName())) {
          String path = element.getAttribute(XML_ATR_DIRECTORY_PATH);
          String source = null;
          if (element.hasAttribute(XML_ATR_DIRECTORY_SOURCE)) {
            source = element.getAttribute(XML_ATR_DIRECTORY_SOURCE);
          }
          String filterId = element.getAttribute(XML_ATR_DIRECTORY_FILTER);
          FilterRuleChain chain = chainMap.get(filterId);
          if (chain == null) {
            throw new IllegalArgumentException("Directory (" + path + ") points to filter ("
                + filterId + ") that does NOT exist!");
          }
          setFilter(FileFilterAdapter.convertStringFilter(chain));
          if (path.startsWith("~/")) {
            path = System.getProperty("user.home") + path.substring(1);
          }
          File directory = new File(path);
          if (directory.isDirectory()) {
            String relativePath = element.getAttribute(XML_ATR_DIRECTORY_INDEXBASEPATH);
            indexDirectory(source, directory, relativePath);
          } else {
            getLogger().warn("Omitting non-existent directory (" + path + ")!");
          }
        }
      }
    }
    getIndexer().close();
  }
}
