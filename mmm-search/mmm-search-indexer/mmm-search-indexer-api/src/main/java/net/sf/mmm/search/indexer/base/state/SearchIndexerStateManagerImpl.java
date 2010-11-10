/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateManager;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the default implementation of the {@link SearchIndexerStateManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerStateManagerImpl extends XmlBeanMapper<SearchIndexerStateBean> implements
    SearchIndexerStateManager {

  /** @see #load(SearchIndexerConfiguration) */
  private static final String CONFIGURATION_FILENAME = "mmm-index-state.xml";

  /**
   * The constructor.
   */
  public SearchIndexerStateManagerImpl() {

    super(SearchIndexerStateBean.class);
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerState load(SearchIndexerConfiguration indexerConfiguration) {

    String indexLocation = indexerConfiguration.getSearchIndex().getLocation();
    if (!indexLocation.endsWith("/")) {
      indexLocation = indexLocation + "/";
    }
    String configurationLocation = indexLocation + CONFIGURATION_FILENAME;
    SearchIndexerStateBean state;
    try {
      state = loadXml(configurationLocation);
    } catch (ResourceNotAvailableException e) {
      state = new SearchIndexerStateBean();
    }
    state.setConfigurationLocation(configurationLocation);
    for (SearchIndexerSource source : indexerConfiguration.getSources()) {
      SearchIndexerSourceStateBean sourceState = state.getOrCreateSourceState(source.getId());
      for (SearchIndexerDataLocation dataLocation : source.getLocations()) {
        sourceState.getOrCreateLocationState(dataLocation.getLocationUri());
      }
    }
    return state;
  }

  /**
   * {@inheritDoc}
   */
  public void save(SearchIndexerState searchIndexStatus) {

    SearchIndexerStateBean stateBean = (SearchIndexerStateBean) searchIndexStatus;
    stateBean.setIndexingDate(new Date());
    String configurationLocation = stateBean.getConfigurationLocation();
    saveXml(stateBean, configurationLocation);
  }
}
