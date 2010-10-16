/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API of the Search-Indexer.
 * <a name="documentation"/><h2>Search-Indexer API</h2> 
 * This package contains the API for a 
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer}. Such indexer allows to
 * update information in a search-index so it can by found by the 
 * {@link net.sf.mmm.search.engine.api.SearchEngine}. It acts on a low level of 
 * abstraction and adapts to the underlying search-technology (e.g. apache 
 * lucene).<br/>
 * On a higher level of abstraction there is the 
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer} that adds 
 * advanced features (using the {@link net.sf.mmm.search.indexer.api.SearchIndexer}
 * as underlying technology).<br/>
 * On the highest level of abstraction the implementation of this API offers
 * a fully integrated main-program for performing the indexing (based on
 * {@link net.sf.mmm.search.indexer.base.AbstractSearchIndexerMain}, see for 
 * <code>SearchIndexerMain</code>).
 */
package net.sf.mmm.search.indexer.api;

