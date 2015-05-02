/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

/**
 * This type is a collection of spring XML configuration files for the entire project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface SpringConfigs {

  /** The Spring XML configuration of <code>mmm-util-core</code>. */
  String SPRING_XML_UTIL_CORE = "/net/sf/mmm/util/beans-util-core.xml";

  /** The Spring XML configuration of <code>mmm-search</code> (and <code>mmm-search-engine</code>). */
  String SPRING_XML_SEARCH = "/net/sf/mmm/search/beans-search.xml";

  /** The Spring XML configuration of <code>mmm-search-indexer</code>. */
  String SPRING_XML_SEARCH_INDEXER = "/net/sf/mmm/search/indexer/beans-search-indexer.xml";

  /** The Spring XML configuration of <code>mmm-content-parser</code>. */
  String SPRING_XML_CONTENT_PARSER = "/net/sf/mmm/content/parser/beans-content-parser.xml";

}
