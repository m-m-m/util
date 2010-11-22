/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateHolder;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateLoader;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the default implementation of the {@link SearchIndexerStateLoader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerStateLoaderImpl extends XmlBeanMapper<SearchIndexerStateBean> implements
    SearchIndexerStateLoader {

  /** @see #load(SearchIndexerConfiguration) */
  private static final String CONFIGURATION_FILENAME = "mmm-index-state.xml";

  /**
   * The constructor.
   */
  public SearchIndexerStateLoaderImpl() {

    super(SearchIndexerStateBean.class);
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerStateHolder load(SearchIndexerConfiguration indexerConfiguration) {

    String indexLocation = indexerConfiguration.getSearchIndex().getLocation();
    if (!indexLocation.endsWith("/")) {
      indexLocation = indexLocation + "/";
    }
    String configurationLocation = indexLocation + CONFIGURATION_FILENAME;
    SearchIndexerStateBean state;
    DataResource resource = getResourceFactory().createDataResource(configurationLocation);
    if (resource.isAvailable()) {
      state = loadXml(resource);
    } else {
      state = new SearchIndexerStateBean();
    }
    for (SearchIndexerSource source : indexerConfiguration.getSources()) {
      SearchIndexerSourceStateBean sourceState = state.getOrCreateSourceState(source.getId());
      for (SearchIndexerDataLocation dataLocation : source.getLocations()) {
        sourceState.getOrCreateLocationState(dataLocation.getLocationUri());
      }
    }
    return new SearchIndexerStateHolderImpl(state, resource, this);
  }

}
