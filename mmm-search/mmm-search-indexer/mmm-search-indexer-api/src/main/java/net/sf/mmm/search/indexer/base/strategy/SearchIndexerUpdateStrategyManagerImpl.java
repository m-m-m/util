/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategyManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerUpdateStrategyManagerImpl extends
    AbstractSearchIndexerUpdateStrategyManager {

  /**
   * The constructor.
   */
  public SearchIndexerUpdateStrategyManagerImpl() {

    super();
  }

  // everything straight-forward and inherited from abstract base-implementation

}
