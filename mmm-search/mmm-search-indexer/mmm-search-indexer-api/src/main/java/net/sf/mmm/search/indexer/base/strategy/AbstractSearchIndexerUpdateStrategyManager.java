/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategyManager;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of the
 * {@link SearchIndexerUpdateStrategyManager} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerUpdateStrategyManager extends AbstractLoggableComponent
    implements SearchIndexerUpdateStrategyManager {

  /** @see #getSearchIndexerUpdateStrategies() */
  private List<? extends SearchIndexerUpdateStrategy> searchIndexerUpdateStrategies;

  /**
   * The constructor.
   */
  public AbstractSearchIndexerUpdateStrategyManager() {

    super();
  }

  /**
   * This method gets the {@link List} of {@link SearchIndexerUpdateStrategy
   * strategies}.
   * 
   * @return the {@link List} of all supported
   *         {@link SearchIndexerUpdateStrategy strategies}.
   */
  protected List<? extends SearchIndexerUpdateStrategy> getSearchIndexerUpdateStrategies() {

    return this.searchIndexerUpdateStrategies;
  }

  /**
   * @param searchIndexerUpdateStrategies is the searchIndexerUpdateStrategies
   *        to set
   */
  @Inject
  public void setSearchIndexerUpdateStrategies(
      List<SearchIndexerUpdateStrategy> searchIndexerUpdateStrategies) {

    getInitializationState().requireNotInitilized();
    this.searchIndexerUpdateStrategies = searchIndexerUpdateStrategies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.searchIndexerUpdateStrategies == null) {
      this.searchIndexerUpdateStrategies = createSearchIndexerUpdateStrategies();
    }
  }

  /**
   * This method creates the {@link List} of {@link SearchIndexerUpdateStrategy
   * strategies}. It is called on {@link #initialize() initialization} if the
   * strategies have NOT been {@link #setSearchIndexerUpdateStrategies(List)
   * set} (injected).
   * 
   * @return the {@link List} of {@link SearchIndexerUpdateStrategy strategies}.
   */
  protected List<? extends SearchIndexerUpdateStrategy> createSearchIndexerUpdateStrategies() {

    throw new ResourceMissingException("searchIndexerUpdateStrategies");
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerUpdateStrategy getStrategy(SearchIndexerSource source) {

    for (SearchIndexerUpdateStrategy strategy : this.searchIndexerUpdateStrategies) {
      if (strategy.isResponsible(source)) {
        return strategy;
      }
    }
    throw new ObjectNotFoundException(SearchIndexerUpdateStrategy.class.getSimpleName(),
        source.getUpdateStrategy());
  }
}
