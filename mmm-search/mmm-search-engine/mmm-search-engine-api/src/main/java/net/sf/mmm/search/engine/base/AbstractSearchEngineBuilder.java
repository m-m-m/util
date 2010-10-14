/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.sf.mmm.search.engine.api.SearchEngineBuilder;
import net.sf.mmm.search.engine.api.SearchQueryBuilder;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base-implementation of the {@link SearchEngineBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchEngineBuilder extends AbstractLoggable implements
    SearchEngineBuilder {

  /** @see #getSearchQueryBuilder() */
  private SearchQueryBuilder searchQueryBuilder;

  /** @see #getSearchEngineRefresher() */
  private SearchEngineRefresher searchEngineRefresher;

  /**
   * The constructor.
   */
  public AbstractSearchEngineBuilder() {

    super();
  }

  /**
   * @return the searchQueryBuilder
   */
  protected SearchQueryBuilder getSearchQueryBuilder() {

    return this.searchQueryBuilder;
  }

  /**
   * @param searchQueryBuilder is the {@link SearchQueryBuilder} to set.
   */
  @Inject
  public void setSearchQueryBuilder(SearchQueryBuilder searchQueryBuilder) {

    this.searchQueryBuilder = searchQueryBuilder;
  }

  /**
   * @return the searchEngineRefresher
   */
  protected SearchEngineRefresher getSearchEngineRefresher() {

    return this.searchEngineRefresher;
  }

  /**
   * @param searchEngineRefresher is the searchEngineRefresher to set
   */
  @Inject
  public void setSearchEngineRefresher(SearchEngineRefresher searchEngineRefresher) {

    this.searchEngineRefresher = searchEngineRefresher;
  }

  /**
   * This method is called when this component shall be destroyed.
   */
  @PreDestroy
  public void dispose() {

    if (this.searchEngineRefresher != null) {
      this.searchEngineRefresher.shutdown();
    }
  }

}
