/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
 * On top there are higher-level components that use the 
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} and are independent from 
 * the underlying search-technology: 
 * <ol>
 * <li>the {@link net.sf.mmm.search.indexer.api.ResourceSearchIndexer} 
 * allows to index {@link net.sf.mmm.util.resource.api.DataResource}s of 
 * arbitrary formats and extracts texts and metadata for the search index.</li>
 * <li>the {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer} allows 
 * recursive indexing including incremental updates. The actual strategy for 
 * incremental indexing is realized via {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}.</li>
 * <li>the <code>SearchIndexerMain</code> offers a fully integrated main-program 
 * for performing the indexing (based on 
 * {@link net.sf.mmm.search.indexer.base.AbstractSearchIndexerMain}).</li>
 * <ol>
 */
package net.sf.mmm.search.indexer.api;

