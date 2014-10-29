/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerBuilder;
import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationHolder;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfigurationLoader;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateHolder;
import net.sf.mmm.search.indexer.api.state.SearchIndexerStateLoader;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategyManager;
import net.sf.mmm.search.indexer.base.config.ConfiguredSearchIndexerOptionsBean;
import net.sf.mmm.search.indexer.base.config.SearchIndexerConfigurationLoaderImpl;
import net.sf.mmm.search.indexer.base.state.SearchIndexerStateLoaderImpl;
import net.sf.mmm.search.indexer.base.strategy.UpdateStrategyArgumentsBean;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.impl.GenericContextFactoryImpl;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the implementation of the {@link ConfiguredSearchIndexer}. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfiguredSearchIndexer extends AbstractLoggableComponent implements
    ConfiguredSearchIndexer {

  /** @see #getSearchIndexerManager() */
  private SearchIndexerBuilder searchIndexerManager;

  /** @see #getSearchIndexerConfigurationLoader() */
  private SearchIndexerConfigurationLoader searchIndexerConfigurationLoader;

  /** @see #getSearchIndexerStateLoader() */
  private SearchIndexerStateLoader searchIndexerStateLoader;

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
  protected SearchIndexerConfigurationLoader getSearchIndexerConfigurationLoader() {

    return this.searchIndexerConfigurationLoader;
  }

  /**
   * @param searchIndexerConfigurationLoader is the
   *        searchIndexerConfigurationReader to set
   */
  @Inject
  public void setSearchIndexerConfigurationLoader(
      SearchIndexerConfigurationLoader searchIndexerConfigurationLoader) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerConfigurationLoader = searchIndexerConfigurationLoader;
  }

  /**
   * @return the searchIndexStateManager
   */
  protected SearchIndexerStateLoader getSearchIndexerStateLoader() {

    return this.searchIndexerStateLoader;
  }

  /**
   * @param searchIndexerStateLoader is the searchIndexStateManager to set
   */
  @Inject
  public void setSearchIndexerStateLoader(SearchIndexerStateLoader searchIndexerStateLoader) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerStateLoader = searchIndexerStateLoader;
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
    if (this.searchIndexerConfigurationLoader == null) {
      SearchIndexerConfigurationLoaderImpl readerImpl = new SearchIndexerConfigurationLoaderImpl();
      readerImpl.initialize();
      this.searchIndexerConfigurationLoader = readerImpl;
    }
    if (this.searchIndexerStateLoader == null) {
      SearchIndexerStateLoaderImpl impl = new SearchIndexerStateLoaderImpl();
      impl.initialize();
      this.searchIndexerStateLoader = impl;
    }
    if (this.genericContextFactory == null) {
      GenericContextFactoryImpl impl = new GenericContextFactoryImpl();
      impl.initialize();
      this.genericContextFactory = impl;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerSource source, ConfiguredSearchIndexerOptions options,
      SearchIndexerStateHolder stateHolder, SearchIndexer searchIndexer) {

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
    SearchIndexerSourceState sourceState = stateHolder.getBean().getSourceState(sourceId);
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
    stateHolder.flush();
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

    SearchIndexerConfigurationHolder configurationHolder = this.searchIndexerConfigurationLoader
        .loadConfiguration(configurationLocation);
    index(configurationHolder, options);
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerConfigurationHolder configurationHolder) {

    index(configurationHolder, new ConfiguredSearchIndexerOptionsBean());
  }

  /**
   * {@inheritDoc}
   */
  public void index(SearchIndexerConfigurationHolder configurationHolder,
      ConfiguredSearchIndexerOptions options) {

    NlsNullPointerException.checkNotNull(SearchIndexerConfigurationHolder.class,
        configurationHolder);
    SearchIndexerConfiguration configuration = configurationHolder.getBean();
    NlsNullPointerException.checkNotNull(SearchIndexerConfiguration.class, configuration);
    this.searchIndexerConfigurationLoader.validateConfiguration(configuration);
    SearchIndexerStateHolder state = this.searchIndexerStateLoader.load(configuration);
    List<String> sourceIds = options.getSourceIds();
    SearchIndexer searchIndexer = this.searchIndexerManager.createIndexer(configurationHolder,
        options);
    if (options.getNonUtfEncoding() == null) {
      String nonUtfEncoding = configuration.getNonUtfEncoding();
      if (nonUtfEncoding != null) {
        ((ConfiguredSearchIndexerOptionsBean) options).setNonUtfEncoding(nonUtfEncoding);
      }
    }
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
