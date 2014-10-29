/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.strategy;

import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for the actual strategy used for the (delta-)indexing
 * of content. <br>
 * There are multiple implementations of this interface with different
 * strategies for incremental indexing as well as for indexing from scratch
 * every time. Implementations of this interface are typically stateful and
 * therefore NOT thread-safe.
 * 
 * @see SearchIndexerUpdateStrategyManager
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification(plugin = true)
public interface SearchIndexerUpdateStrategy {

  /**
   * This method determines if this {@link SearchIndexerUpdateStrategy} is
   * responsible for {@link #index(UpdateStrategyArguments) indexing} the given
   * <code>source</code>. <br>
   * Typical implementations will check if the
   * {@link SearchIndexerSource#getUpdateStrategy() update-strategy} matches a
   * specific value.
   * @param source is the {@link SearchIndexerSource} to index (incremental)
   *        according to its {@link SearchIndexerSource#getUpdateStrategy()
   *        update-type}.
   * 
   * @see SearchIndexerUpdateStrategyManager#getStrategy(SearchIndexerSource)
   * 
   * @return <code>true</code>
   */
  boolean isResponsible(SearchIndexerSource source);

  /**
   * This method performs the actual indexing.
   * 
   * @param arguments are the {@link UpdateStrategyArguments} for the indexing.
   */
  void index(UpdateStrategyArguments arguments);

}
