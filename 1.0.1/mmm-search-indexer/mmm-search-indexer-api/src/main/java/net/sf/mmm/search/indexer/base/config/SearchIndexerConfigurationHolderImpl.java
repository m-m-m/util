/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolderImpl;

/**
 * This is the implementation of {@link SearchIndexerConfigurationHolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerConfigurationHolderImpl extends
    JaxbBeanHolderImpl<SearchIndexerConfiguration, SearchIndexerConfigurationBean> implements
    SearchIndexerConfigurationHolder {

  /**
   * The constructor.
   * 
   * @param configuration is the {@link SearchIndexerConfigurationBean} for
   *        {@link #getBean()}.
   * @param resource is the {@link DataResource}.
   * @param reader is the {@link SearchIndexerConfigurationLoaderImpl} for
   *        {@link #refresh()}.
   */
  public SearchIndexerConfigurationHolderImpl(SearchIndexerConfigurationBean configuration,
      DataResource resource, SearchIndexerConfigurationLoaderImpl reader) {

    super(configuration, resource, reader, false);
  }

}
