/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;

/**
 * This interface bundles the dependencies for the indexer. It contains components required by
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface IndexerDependencies {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.search.indexer.base.IndexerDependencies";

  /**
   * This method gets the {@link BrowsableResourceFactory} used to create resources for
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation#getLocationUri() locations}.
   * 
   * @return the {@link BrowsableResourceFactory}.
   */
  BrowsableResourceFactory getBrowsableResourceFactory();

  /**
   * This method gets the {@link ResourceSearchIndexer} used to update a single
   * {@link net.sf.mmm.util.resource.api.DataResource resource} in the search-index.
   * 
   * @return the {@link ResourceSearchIndexer}.
   */
  ResourceSearchIndexer getResourceSearchIndexer();

}
