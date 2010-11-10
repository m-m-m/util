/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
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

    SearchIndexerConfigurationBean configuration = loadXml(locationUrl);
    configuration.setConfigurationLocation(locationUrl);
    return configuration;
  }

  /**
   * {@inheritDoc}
   */
  public void validateConfiguration(SearchIndexerConfiguration configuration)
      throws SearchException {

    NlsNullPointerException.checkNotNull(SearchIndexerConfiguration.class, configuration);
    List<? extends SearchIndexerSource> sourceList = configuration.getSources();
    if (sourceList == null) {
      throw new NlsNullPointerException("sources");
    }
    for (SearchIndexerSource source : sourceList) {
      NlsNullPointerException.checkNotNull(SearchSource.class, source);
      String sourceId = source.getId();
      if (sourceId == null) {
        throw new NlsNullPointerException("source@id");
      }
      List<? extends SearchIndexerDataLocation> locationList = source.getLocations();
      if (locationList == null) {
        throw new NlsNullPointerException("locations");
      }
      for (SearchIndexerDataLocation location : locationList) {
        NlsNullPointerException.checkNotNull(SearchIndexerDataLocation.class, location);
        if (source != location.getSource()) {
          throw new NlsIllegalStateException();
        }
        if (location.getLocationUri() == null) {
          throw new NlsNullPointerException("location@location-uri");
        }
      }
    }
  }
}
