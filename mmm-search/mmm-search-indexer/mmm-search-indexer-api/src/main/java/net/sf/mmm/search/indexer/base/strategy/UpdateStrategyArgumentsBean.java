/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.ConfiguredSearchIndexerOptions;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;
import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.impl.MutableGenericContextImpl;

/**
 * This is the implementation of {@link UpdateStrategyArguments} as simple
 * java-bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UpdateStrategyArgumentsBean implements UpdateStrategyArguments {

  /** @see #getContext() */
  private final MutableGenericContext context;

  /** @see #getIndexer() */
  private SearchIndexer indexer;

  /** @see #getOptions() */
  private ConfiguredSearchIndexerOptions options;

  /** @see #getSource() */
  private SearchIndexerSource source;

  /** @see #getSourceState() */
  private SearchIndexerSourceState sourceState;

  /**
   * The constructor.
   */
  public UpdateStrategyArgumentsBean() {

    this(new MutableGenericContextImpl(HashMapFactory.INSTANCE));
  }

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UpdateStrategyArgumentsBean(MutableGenericContext context) {

    super();
    this.context = context;
  }

  /**
   * {@inheritDoc}
   */
  public MutableGenericContext getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexer getIndexer() {

    return this.indexer;
  }

  /**
   * @param indexer is the {@link SearchIndexer} to set.
   */
  public void setIndexer(SearchIndexer indexer) {

    this.indexer = indexer;
  }

  /**
   * {@inheritDoc}
   */
  public ConfiguredSearchIndexerOptions getOptions() {

    return this.options;
  }

  /**
   * @param options is the options to set
   */
  public void setOptions(ConfiguredSearchIndexerOptions options) {

    this.options = options;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerSource getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  public void setSource(SearchIndexerSource source) {

    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerSourceState getSourceState() {

    return this.sourceState;
  }

  /**
   * @param sourceState is the sourceState to set
   */
  public void setSourceState(SearchIndexerSourceState sourceState) {

    this.sourceState = sourceState;
  }
}
