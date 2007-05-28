/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexModifier;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.mmm.search.impl.LuceneConstants;

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
   * @param args
   *        are the command-line arguments.
   * @throws Exception
   *         if something goes wrong.
   */
  public static void main(String[] args) throws Exception {

    // command-line options...
    if (args.length != 1) {
      usage();
      System.exit(-1);
    }
    // parse xml from file...
    Element xmlConfigElement;
    try {
      File xmlConfigFile = new File(args[0]);
      Document xmlConfigDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
          xmlConfigFile);
      xmlConfigElement = xmlConfigDoc.getDocumentElement();
    } catch (Exception e) {
      usage();
      System.out.println("Error: Failed to load configuration from file: " + args[0]);
      throw e;
    }
    // parse xml content and create lucene-indexer
    LuceneSearchIndexer luceneIndexer = null;
    NodeList childNodes = xmlConfigElement.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) child;
        if (LuceneConstants.XML_TAG_INDEX.equals(element.getTagName())) {
          String indexPath = element.getAttribute(LuceneConstants.XML_ATR_INDEX_PATH);
          File indexDirectory = new File(indexPath);
          Analyzer analyzer;
          if (element.hasAttribute(LuceneConstants.XML_ATR_INDEX_ANALYZER)) {
            String analyzerClass = element.getAttribute(LuceneConstants.XML_ATR_INDEX_ANALYZER);
            try {
              analyzer = (Analyzer) Class.forName(analyzerClass).newInstance();
            } catch (Exception e) {
              throw new IllegalArgumentException("Illegal analyzer: " + analyzerClass, e);
            }
          } else {
            analyzer = new StandardAnalyzer();
          }
          boolean update = false;
          if (element.hasAttribute(XML_ATR_INDEXER_UPDATE)) {
            String updateFlag = element.getAttribute(XML_ATR_INDEXER_UPDATE);
            if (Boolean.TRUE.toString().equalsIgnoreCase(updateFlag)) {
              update = true;
            } else if (Boolean.FALSE.toString().equalsIgnoreCase(updateFlag)) {
              update = false;
            } else {
              throw new IllegalArgumentException("XML-Attribute " + XML_ATR_INDEXER_UPDATE
                  + " must be either 'true' or 'false'!");
            }
          }
          boolean create;
          if (update) {
            if (indexDirectory.isDirectory()) {
              create = false;
            } else {
              indexDirectory.mkdirs();
              create = true;
            }
          } else {
            indexDirectory.mkdirs();
            create = true;
          }
          IndexModifier indexModifier = new IndexModifier(indexDirectory, analyzer, create);
          luceneIndexer = new LuceneSearchIndexer(indexModifier);
        }
      }
    }
    if (luceneIndexer == null) {
      throw new IllegalArgumentException("Missing XML-element: " + LuceneConstants.XML_TAG_INDEX);
    }
    LuceneDirectorySearchIndexer indexer = new LuceneDirectorySearchIndexer(luceneIndexer);
    indexer.setLogger(LogFactory.getLog(LuceneDirectorySearchIndexer.class));
    indexer.initialize();
    indexer.index(xmlConfigElement);
  }

}
