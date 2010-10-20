/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.state.SearchIndexState;
import net.sf.mmm.search.indexer.api.state.SearchIndexStateManager;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the default implementation of the {@link SearchIndexStateManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexStateManagerImpl extends XmlBeanMapper<SearchIndexStateBean> implements
    SearchIndexStateManager {

  /** @see #load(SearchIndexerConfiguration) */
  private static final String CONFIGURATION_FILENAME = "mmm-index-state.xml";

  /**
   * The constructor.
   */
  public SearchIndexStateManagerImpl() {

    super(SearchIndexStateBean.class);
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexState load(SearchIndexerConfiguration indexerConfiguration) {

    String indexLocation = indexerConfiguration.getSearchIndex().getLocation();
    if (!indexLocation.endsWith("/")) {
      indexLocation = indexLocation + "/";
    }
    String configurationLocation = indexLocation + CONFIGURATION_FILENAME;
    SearchIndexStateBean state;
    try {
      state = loadXml(configurationLocation);
    } catch (ResourceNotAvailableException e) {
      state = new SearchIndexStateBean();
    }
    state.setConfigurationLocation(configurationLocation);
    for (SearchSource source : indexerConfiguration.getSources()) {
      state.getOrCreateSourceState(source.getId());
    }
    for (SearchIndexDataLocation dataLocation : indexerConfiguration.getLocations()) {
      state.getOrCreateLocationState(dataLocation.getLocation());
    }
    return state;
  }

  /**
   * {@inheritDoc}
   */
  public void save(SearchIndexState searchIndexStatus) {

    SearchIndexStateBean stateBean = (SearchIndexStateBean) searchIndexStatus;
    stateBean.setIndexingDate(new Date());
    String configurationLocation = stateBean.getConfigurationLocation();
    saveXml(stateBean, configurationLocation);
  }
}
