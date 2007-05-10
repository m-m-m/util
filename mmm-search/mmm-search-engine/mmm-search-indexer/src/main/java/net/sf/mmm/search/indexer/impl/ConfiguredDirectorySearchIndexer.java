/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import org.w3c.dom.Element;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.util.filter.FileFilterAdapter;
import net.sf.mmm.util.filter.FilterRuleChain;
import net.sf.mmm.util.filter.FilterRuleChainXmlParser;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfiguredDirectorySearchIndexer extends DirectorySearchIndexer {

  public static final String XML_TAG_DIRECTORY = "directory";

  public static final String XML_ATR_PATH = "path";

  public static final String XML_ATR_SOURCE = "source";
  
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

  public void index(Element configurationElement) {
   FilterRuleChainXmlParser parser = new FilterRuleChainXmlParser();
   Element chainElement = configurationElement;
   FilterRuleChain chain = parser.parse(chainElement);
   setFilter(FileFilterAdapter.convertStringFilter(chain));
  }
  
}
