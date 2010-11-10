/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerBuilder;
import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationReader;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateManager;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategyManager;
import net.sf.mmm.search.indexer.base.config.ConfiguredSearchIndexerOptionsBean;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationReaderImpl;
import net.sf.mmm.search.indexer.base.state.SearchIndexerStateManagerImpl;
import net.sf.mmm.search.indexer.base.strategy.UpdateStrategyArgumentsBean;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.impl.GenericContextFactoryImpl;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the implementation of the {@link ConfiguredSearchIndexer}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfiguredSearchIndexer extends AbstractLoggableComponent implements
    ConfiguredSearchIndexer {

  /** @see #getSearchIndexerManager() */
  private SearchIndexerBuilder searchIndexerManager;

  /** @see #getSearchIndexerConfigurationReader() */
  private SearchIndexerConfigurationReader searchIndexerConfigurationReader;

  /** @see #getSearchIndexerStateManager() */
  private SearchIndexerStateManager searchIndexerStateManager;

  /** @see #getSearchIndexerUpdateStrategyManager() */
  private SearchIndexerUpdateStrategyManager searchIndexerUpdateStrategyManager;

  /** @see #getGenericContextFactory() */
  private GenericContextFactory genericContextFactory;

  /**
   * The constructor.
   */
  public AbstractConfiguredSearchIndexer() {

    super();
  }

  /**
   * @return the searchIndexerManager
   */
  protected SearchIndexerBuilder getSearchIndexerManager() {

    return this.searchIndexerManager;
  }

  /**
   * @param searchIndexerManager is the searchIndexerManager to set
   */
  @Inject
  public void setSearchIndexerManager(SearchIndexerBuilder searchIndexerManager) {

    this.searchIndexerManager = searchIndexerManager;
  }

  /**
   * @return the searchIndexerConfigurationReader
   */
  protected SearchIndexerConfigurationReader getSearchIndexerConfigurationReader() {

    return this.searchIndexerConfigurationReader;
  }

  /**
   * @param searchIndexerConfigurationReader is the
   *        searchIndexerConfigurationReader to set
   */
  @Inject
  public void setSearchIndexerConfigurationReader(
      SearchIndexerConfigurationReader searchIndexerConfigurationReader) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerConfigurationReader = searchIndexerConfigurationReader;
  }

  /**
   * @return the searchIndexStateManager
   */
  protected SearchIndexerStateManager getSearchIndexerStateManager() {

    return this.searchIndexerStateManager;
  }

  /**
   * @param searchIndexerStateManager is the searchIndexStateManager to set
   */
  @Inject
  public void setSearchIndexerStateManager(SearchIndexerStateManager searchIndexerStateManager) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerStateManager = searchIndexerStateManager;
  }

  /**
   * @return the deltaSearchIndexerBuilder
   */
  protected SearchIndexerUpdateStrategyManager getSearchIndexerUpdateStrategyManager() {

    return this.searchIndexerUpdateStrategyManager;
  }

  /**
   * @param searchIndexerUpdateStrategyManager is the deltaSearchIndexerBuilder
   *        to set
   */
  @Inject
  public void setSearchIndexerUpdateStrategyManager(
      SearchIndexerUpdateStrategyManager searchIndexerUpdateStrategyManager) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerUpdateStrategyManager = searchIndexerUpdateStrategyManager;
  }

  /**
   * This method gets the {@link GenericContextFactory}.
   * 
   * @return the {@link GenericContextFactory}.
   */
  protected GenericContextFactory getGenericContextFactory() {

    return this.genericContextFactory;
  }

  /**
   * @param genericContextFactory is the genericContextFactory to set
   */
  @Inject
  public void setGenericContextFactory(GenericContextFactory genericContextFactory) {

    getInitializationState().requireNotInitilized();
    this.genericContextFactory = genericContextFactory;
  }

  /**
   * This method initializes this object.
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
    if (this.searchIndexerUpdateStrategyManager == null) {
      throw new ResourceMissingException(SearchIndexerUpdateStrategyManager.class.getSimpleName());
    }
    if (this.searchIndexerManager == null) {
      throw new ResourceMissingException("searchIndexerManager");
    }
    if (this.searchIndexerConfigurationReader == null) {
      SearchIndexerConfigurationReaderImpl readerImpl = new SearchIndexerConfigurationReaderImpl();
      readerImpl.initialize();
      this.searchIndexerConfigurationReader = readerImpl;
    }
    if (this.searchIndexerStateManager == null) {
      SearchIndexerStateManagerImpl impl = new SearchIndexerStateManagerImpl();
      impl.initialize();
      this.searchIndexerStateManager = impl;
    }
    if (this.genericContextFactory == null) {
      GenericContextFactoryImpl impl = new GenericContextFactoryImpl();
      impl.initialize();
      this.genericContextFactory = impl;
    }
  }

  // /**
  // * {@inheritDoc}
  // */
  // public void index(SearchIndexDataLocation location, SearchIndexState state,
  // SearchIndexer searchIndexer) {
  //
  // index(searchIndexer, state, location);
  // }

  // /**
  // * This method indexes the given {@link SearchIndexDataLocation
  // location}.<br/>
  // * <b>NOTE:</b><br>
  // * Unlike
  // * {@link #index(SearchIndexerConfiguration, ConfiguredSearchIndexerOptions,
  // SearchIndexState, SearchIndexer, SearchSource)}
  // * this method will NOT delete search-entries from the index in advance.
  // *
  // * @param searchIndexer is the {@link SearchIndexer} used for modifying the
  // * index.
  // * @param state is the {@link SearchIndexState}.
  // * @param forceFullIndexing - <code>true</code> if the <code>location</code>
  // * should be entirely indexed again from scratch, <code>false</code> if
  // * delta-indexing should be enabled so a potentially existing index is
  // * updated incremental.
  // * @param location is the {@link SearchIndexDataLocation} to crawl and add
  // to
  // * the index.
  // * @param entryUriSet is the {@link Set} with the
  // * {@link net.sf.mmm.search.api.SearchEntry#getUri() URIs} of all
  // * {@link net.sf.mmm.search.api.SearchEntry entries} that have been
  // * indexed or remain untouched in delta-indexing.
  // */
  // protected abstract void index(SearchIndexer searchIndexer, SearchIndexState
  // state,
  // boolean forceFullIndexing, SearchIndexDataLocation location, Set<String>
  // entryUriSet);

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerSource source, ConfiguredSearchIndexerOptions options,
      SearchIndexerState state, SearchIndexer searchIndexer) {

    if (source.getLocations().isEmpty()) {
      if (!source.getId().equals(SearchSource.ID_ANY)) {
        getLogger().info(
            "source '" + source.getId() + "' has not locations and will NOT be indexed.");
      }
      // nothing to index...
      return;
    }

    getLogger().debug("Indexing source '" + source.getId() + "' ...");
    String sourceId = source.getId();
    SearchIndexerSourceState sourceState = state.getSourceState(sourceId);
    if (sourceState == null) {
      throw new ObjectNotFoundException(SearchIndexerSourceState.class.getSimpleName(), sourceId);
    }

    // get strategy...
    SearchIndexerUpdateStrategy strategy = this.searchIndexerUpdateStrategyManager
        .getStrategy(source);

    // create arguments (state object)...
    MutableGenericContext context = this.genericContextFactory.createContext();
    UpdateStrategyArgumentsBean arguments = new UpdateStrategyArgumentsBean(context);
    arguments.setIndexer(searchIndexer);
    arguments.setOptions(options);
    arguments.setSource(source);
    arguments.setSourceState(sourceState);

    // delegate the actual indexing to according strategy...
    strategy.index(arguments);

    // complete indexing...
    searchIndexer.flush();
    this.searchIndexerStateManager.save(state);
    getLogger().debug("Indexed source '" + source.getId() + "' ...");
  }

  /**
   * {@inheritDoc}
   */
  public void index(String configurationLocation) {

    index(configurationLocation, new ConfiguredSearchIndexerOptionsBean());
  }

  /**
   * {@inheritDoc}
   */
  public void index(String configurationLocation, ConfiguredSearchIndexerOptions options) {

    SearchIndexerConfiguration configuration = this.searchIndexerConfigurationReader
        .readConfiguration(configurationLocation);
    index(configuration, options);
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerConfiguration configuration) {

    index(configuration, new ConfiguredSearchIndexerOptionsBean());
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerConfiguration configuration, ConfiguredSearchIndexerOptions options) {

    this.searchIndexerConfigurationReader.validateConfiguration(configuration);
    SearchIndexerState state = this.searchIndexerStateManager.load(configuration);
    SearchIndexConfiguration searchIndexConfiguration = configuration.getSearchIndex();
    List<String> sourceIds = options.getSourceIds();
    SearchIndexer searchIndexer = this.searchIndexerManager.createIndexer(searchIndexConfiguration,
        options);
    getLogger().debug("Start to index...");
    try {
      if (sourceIds == null) {
        for (SearchIndexerSource source : configuration.getSources()) {
          index(source, options, state, searchIndexer);
        }
      } else {
        // verify sourceIds...
        for (String sourceId : sourceIds) {
          SearchSource source = configuration.getSource(sourceId);
          if (source == null) {
            throw new ObjectNotFoundException(SearchSource.class.getName(), sourceId);
          }
        }
        for (String sourceId : sourceIds) {
          SearchIndexerSource source = configuration.getSource(sourceId);
          index(source, options, state, searchIndexer);
        }
      }
      if (options.isOptimize()) {
        getLogger().debug("Optimizing index...");
        searchIndexer.optimize();
      }
    } finally {
      getLogger().debug("Closing index...");
      searchIndexer.close();
    }
  }

}
