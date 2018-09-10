/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

/**
 * This type is a collection of spring XML configuration files for the entire project.
 *
 * @deprecated Spring XML config is dead. Migrate to code configuration (and spring-boot).
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Deprecated
public interface SpringConfigs {

  /** The Spring XML configuration of {@code mmm-util-core}. */
  String SPRING_XML_UTIL_CORE = "/net/sf/mmm/util/beans-util-core.xml";

  /** The Spring XML configuration of {@code mmm-search} (and {@code mmm-search-engine}). */
  String SPRING_XML_SEARCH = "/net/sf/mmm/search/beans-search.xml";

  /** The Spring XML configuration of {@code mmm-search-indexer}. */
  String SPRING_XML_SEARCH_INDEXER = "/net/sf/mmm/search/indexer/beans-search-indexer.xml";

  /** The Spring XML configuration of {@code mmm-content-parser}. */
  String SPRING_XML_CONTENT_PARSER = "/net/sf/mmm/content/parser/beans-content-parser.xml";

}
