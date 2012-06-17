/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import javax.inject.Inject;

import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.base.SearchDependenciesImpl;
import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchQueryBuilderFactory;
import net.sf.mmm.util.component.api.PeriodicRefresher;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.component.impl.PeriodicRefresherImpl;

/**
 * This is the abstract base-implementation of the {@link SearchEngineBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchEngineBuilder extends AbstractLoggableComponent implements SearchEngineBuilder {

  /** @see #getSearchQueryBuilderFactory() */
  private SearchQueryBuilderFactory searchQueryBuilderFactory;

  /** @see #getPeriodicRefresher() */
  private PeriodicRefresher periodicRefresher;

  /** @see #getSearchDependencies() */
  private SearchDependencies searchDependencies;

  /**
   * The constructor.
   */
  public AbstractSearchEngineBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.periodicRefresher == null) {
      PeriodicRefresherImpl impl = new PeriodicRefresherImpl();
      impl.initialize();
      this.periodicRefresher = impl;
    }
    if (this.searchDependencies == null) {
      SearchDependenciesImpl impl = new SearchDependenciesImpl();
      impl.initialize();
      this.searchDependencies = impl;
    }
  }

  /**
   * @return the searchQueryBuilder
   */
  protected SearchQueryBuilderFactory getSearchQueryBuilderFactory() {

    return this.searchQueryBuilderFactory;
  }

  /**
   * @param searchQueryBuilderFactory is the {@link SearchQueryBuilderFactory} to set.
   */
  @Inject
  public void setSearchQueryBuilderFactory(SearchQueryBuilderFactory searchQueryBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.searchQueryBuilderFactory = searchQueryBuilderFactory;
  }

  /**
   * @return the searchEngineRefresher
   */
  protected PeriodicRefresher getPeriodicRefresher() {

    return this.periodicRefresher;
  }

  /**
   * @param periodicRefresher is the searchEngineRefresher to set
   */
  @Inject
  public void setPeriodicRefresher(PeriodicRefresher periodicRefresher) {

    this.periodicRefresher = periodicRefresher;
  }

  /**
   * This method gets the {@link SearchDependencies}.
   * 
   * @return the {@link SearchDependencies}.
   */
  protected SearchDependencies getSearchDependencies() {

    return this.searchDependencies;
  }

  /**
   * @param searchDependencies is the searchDependencies to set
   */
  @Inject
  public void setSearchDependencies(SearchDependencies searchDependencies) {

    getInitializationState().requireNotInitilized();
    this.searchDependencies = searchDependencies;
  }
  //
  // /**
  // * {@inheritDoc}
  // */
  // public ManagedSearchEngine createSearchEngine(SearchIndexConfiguration
  // configuration) {
  //
  // SearchEngineConfigurationBean conf = new SearchEngineConfigurationBean();
  // conf.setSearchIndex((SearchIndexConfigurationBean) configuration);
  // return createSearchEngine(conf);
  // }

}
