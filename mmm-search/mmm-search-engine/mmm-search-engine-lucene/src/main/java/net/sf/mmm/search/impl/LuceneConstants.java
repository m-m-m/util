/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl;

/**
 * This interface is a collection of constants for lucene.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface LuceneConstants {

  /**
   * The name of the XML attribute that contains the full-qualified class-name
   * of the {@link org.apache.lucene.analysis.Analyzer analyzer} to use.
   */
  String XML_ATR_ANALYZER = "analyzer";

  /**
   * The name of the XML attribute that contains the <code>path</code> to the
   * directory with the search-index.
   */
  String XML_ATR_INDEX_PATH = "index-path";

}
