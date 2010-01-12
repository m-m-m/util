/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.util.file.base.FileStringFilterAdapter;
import net.sf.mmm.util.file.base.FileUtilImpl;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.filter.base.FilterRuleChainXmlParser;
import net.sf.mmm.util.transformer.api.Transformer;
import net.sf.mmm.util.transformer.base.StringTransformerChainXmlParser;

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

  /** The name of the XML element for a list of transformers (URI-rewriters). */
  public static final String XML_TAG_URITRANSFORMERS = "uri-transformers";

  /**
   * The name of the XML element for a list of {@link #XML_TAG_DIRECTORY
   * directories}.
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
   * The name of the XML attribute for the <code>encoding</code> used to read
   * textual files in a {@link #XML_TAG_DIRECTORY directory}.
   */
  public static final String XML_ATR_DIRECTORY_ENCODING = "encoding";

  /**
   * The name of the XML attribute pointing to the ID of the
   * <code>filter-chain</code> to use for indexing a {@link #XML_TAG_DIRECTORY
   * directory}.
   */
  public static final String XML_ATR_DIRECTORY_FILTER = "filter";

  /**
   * The name of the XML attribute pointing to the ID of the
   * <code>transformer-chain</code> to use for rewriting the URIs when indexing
   * a {@link #XML_TAG_DIRECTORY directory}.
   */
  public static final String XML_ATR_DIRECTORY_URITRANSFORMER = "uri-transformer";

  /**
   * The name of the XML attribute with the <code>base-path</code> used for the
   * URI of the resources of a {@link #XML_TAG_DIRECTORY directory} to index.
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
   * @param indexer is the indexer to use.
   */
  public ConfiguredDirectorySearchIndexer(SearchIndexer indexer) {

    super(indexer);
  }

  /**
   * This method builds the search-index from the given
   * <code>configurationElement</code>.
   * 
   * @param configurationElement is the XML-element containing the configuration
   *        which directories to index and which files to exclude.
   */
  public void index(Element configurationElement) {

    getLogger().debug("parsing configuration...");
    // find child-elements "filters" and "directories"...
    Element transformersElement = null;
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
        } else if (XML_TAG_URITRANSFORMERS.equals(element.getTagName())) {
          if (transformersElement != null) {
            throw new IllegalArgumentException("Duplicate tag: " + XML_TAG_URITRANSFORMERS);
          }
          transformersElement = element;
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
    FilterRuleChainXmlParser filterParser = new FilterRuleChainXmlParser();
    Map<String, FilterRuleChain<String>> chainMap = filterParser.parseChains(filtersElement);

    Map<String, ? extends Transformer<String>> transformerMap;
    if (transformersElement == null) {
      transformerMap = new HashMap<String, Transformer<String>>();
    } else {
      StringTransformerChainXmlParser transformerParser = new StringTransformerChainXmlParser();
      transformerMap = transformerParser.parseChains(transformersElement);
    }
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
          String encoding = null;
          if (element.hasAttribute(XML_ATR_DIRECTORY_ENCODING)) {
            encoding = element.getAttribute(XML_ATR_DIRECTORY_ENCODING);
          }
          setEncoding(encoding);
          // set filter...
          String filterId = element.getAttribute(XML_ATR_DIRECTORY_FILTER);
          FilterRuleChain<String> chain = chainMap.get(filterId);
          if (chain == null) {
            throw new IllegalArgumentException("Directory (" + path + ") points to filter ("
                + filterId + ") that does NOT exist!");
          }
          setFilter(new FileStringFilterAdapter(chain));
          // set transformer/rewriter
          Transformer<String> urlRewriter = null;
          if (element.hasAttribute(XML_ATR_DIRECTORY_URITRANSFORMER)) {
            String transformerId = element.getAttribute(XML_ATR_DIRECTORY_URITRANSFORMER);
            urlRewriter = transformerMap.get(transformerId);
            if (urlRewriter == null) {
              throw new IllegalArgumentException("Directory (" + path + ") points to transformer ("
                  + transformerId + ") that does NOT exist!");
            }
          }
          setUriRewriter(urlRewriter);
          path = FileUtilImpl.getInstance().normalizePath(path);
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

  /**
   * This method builds the search-index from the given
   * <code>configurationElement</code>.
   * 
   * @param configuration is the configuration.
   */
  public void index(SearchIndexerConfiguration configuration) {

  }

}
