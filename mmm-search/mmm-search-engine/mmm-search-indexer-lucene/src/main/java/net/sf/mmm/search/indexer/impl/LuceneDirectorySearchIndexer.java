/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.mmm.search.base.SearchConfigurator;
import net.sf.mmm.search.impl.LuceneSearchConfigurator;
import net.sf.mmm.util.xml.DomUtil;

/**
 * This class represents a search-indexer using lucene as underlying
 * search-engine that can recursively scans a set of directories and adds
 * contained files to a search index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneDirectorySearchIndexer extends ConfiguredDirectorySearchIndexer {

  /**
   * The name of the XML attribute for the <code>create</code> flag. It
   * contains a boolean value, that determines if the index will be updated
   * (true) or rebuild every time (false). The default is <code>false</code>.
   */
  public static final String XML_ATR_INDEXER_UPDATE = "update";

  /**
   * The constructor.
   */
  public LuceneDirectorySearchIndexer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param indexer
   */
  public LuceneDirectorySearchIndexer(LuceneSearchIndexer indexer) {

    super(indexer);
  }

  /**
   * This method prints out the usage of this class.
   */
  private static void usage() {

    System.out.println("Usage: " + LuceneDirectorySearchIndexer.class.getSimpleName()
        + " <xml-configuration-file>");
  }

  /**
   * The main method used to execute this class as program.
   * 
   * @param args are the command-line arguments.
   * @throws Exception if something goes wrong.
   */
  public static void main(String[] args) throws Exception {

    // command-line options...
    if (args.length != 1) {
      usage();
      System.exit(-1);
    }
    // parse xml from file...
    Element element;
    try {
      File xmlConfigFile = new File(args[0]);
      Document xmlConfigDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
          xmlConfigFile);
      element = xmlConfigDoc.getDocumentElement();
    } catch (Exception e) {
      usage();
      System.out.println("Error: Failed to load configuration from file: " + args[0]);
      throw e;
    }
    LuceneSearchConfigurator configurator = new LuceneSearchConfigurator();
    Element searchEngineElement = DomUtil.requireFirstChildElement(element,
        SearchConfigurator.XML_TAG_SEARCH_ENGINE);
    LuceneSearchIndexer luceneIndexer = configurator.createSearchIndexer(searchEngineElement);
    LuceneDirectorySearchIndexer indexer = new LuceneDirectorySearchIndexer(luceneIndexer);
    indexer.setLogger(LogFactory.getLog(LuceneDirectorySearchIndexer.class));
    indexer.initialize();
    indexer.index(element);
  }

}
