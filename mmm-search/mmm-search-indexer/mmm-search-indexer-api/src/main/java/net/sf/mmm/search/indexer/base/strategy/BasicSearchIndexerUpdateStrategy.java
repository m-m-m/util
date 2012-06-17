/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;

/**
 * This class extends {@link AbstractSearchIndexerUpdateStrategy} with a simple
 * implementation for {@link #isResponsible(SearchIndexerSource)} using a fixed
 * set of {@link SearchIndexerSource#getUpdateStrategy() update-strategies}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class BasicSearchIndexerUpdateStrategy extends AbstractSearchIndexerUpdateStrategy {

  /** @see #isResponsible(SearchIndexerSource) */
  private final String[] responsibleStrategies;

  /**
   * The constructor.
   * 
   * @param responsibleStrategies are the
   *        {@link SearchIndexerSource#getUpdateStrategy() update-strategies}
   *        this
   *        {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
   *        {@link #isResponsible(SearchIndexerSource) is responsible} for.
   */
  public BasicSearchIndexerUpdateStrategy(String... responsibleStrategies) {

    super();
    this.responsibleStrategies = responsibleStrategies;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResponsible(SearchIndexerSource source) {

    String updateStrategy = source.getUpdateStrategy();
    for (String strategy : this.responsibleStrategies) {
      if (strategy.equals(updateStrategy)) {
        return true;
      }
    }
    return false;
  }

}
