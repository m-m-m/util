/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for {@link #readConfiguration(String) reading} the
 * {@link SearchIndexerConfiguration} for a given data location. The
 * implementation shall at least support reading the configuration from the
 * filesystem as XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchIndexerConfigurationReader {

  /**
   * This method reads the configuration from the given <code>location</code>.
   * The configuration data is parsed according to its format (typically XML)
   * and returned as {@link SearchIndexerConfiguration}.
   * 
   * @param locationUrl is the location where the configuration data shall be
   *        read from. This is typically a path into the filesystem (e.g.
   *        "file://~/.mmm/search.xml").
   * @return the parsed {@link SearchIndexerConfiguration}.
   */
  SearchIndexerConfiguration readConfiguration(String locationUrl);

  /**
   * This method validates the given <code>configuration</code> according to
   * logical constraints.
   * 
   * @param configuration is the {@link SearchIndexerConfiguration} to validate.
   * @throws RuntimeException if the configuration is invalid.
   */
  void validateConfiguration(SearchIndexerConfiguration configuration) throws RuntimeException;

}
