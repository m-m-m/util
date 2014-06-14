/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.Collection;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationLoader;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the implementation of {@link SearchIndexerConfigurationLoader}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(SearchIndexerConfigurationLoader.CDI_NAME)
public class SearchIndexerConfigurationLoaderImpl extends XmlBeanMapper<SearchIndexerConfigurationBean> implements
    SearchIndexerConfigurationLoader {

  /**
   * The constructor.
   */
  public SearchIndexerConfigurationLoaderImpl() {

    super(SearchIndexerConfigurationBean.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchIndexerConfigurationHolder loadConfiguration() {

    return loadConfiguration(SearchConfiguration.DEFAULT_CONFIGURATION_URL);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchIndexerConfigurationHolder loadConfiguration(String locationUrl) {

    DataResource resource = getResourceFactory().createDataResource(locationUrl);
    SearchIndexerConfigurationBean configuration = loadXml(resource);
    return new SearchIndexerConfigurationHolderImpl(configuration, resource, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void validate(SearchIndexerConfigurationBean jaxbBean) {

    super.validate(jaxbBean);
    validateConfiguration(jaxbBean);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validateConfiguration(SearchIndexerConfiguration configuration) throws SearchException {

    NlsNullPointerException.checkNotNull(SearchIndexerConfiguration.class, configuration);
    Collection<? extends SearchIndexerSource> sourceList = configuration.getSources();
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
