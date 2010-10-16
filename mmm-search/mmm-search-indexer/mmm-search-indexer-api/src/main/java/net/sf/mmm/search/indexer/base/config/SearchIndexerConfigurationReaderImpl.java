/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the implementation of the {@link SearchIndexerConfigurationReader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerConfigurationReaderImpl extends
    XmlBeanMapper<SearchIndexerConfigurationBean> implements SearchIndexerConfigurationReader {

  /**
   * The constructor.
   */
  public SearchIndexerConfigurationReaderImpl() {

    super(SearchIndexerConfigurationBean.class);
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerConfiguration readConfiguration(String locationUrl) {

    return loadXml(locationUrl);
  }

}
