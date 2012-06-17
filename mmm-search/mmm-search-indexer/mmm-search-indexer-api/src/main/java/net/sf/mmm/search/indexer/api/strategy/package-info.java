/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the API for the available (delta-)indexing strategies.
 * <a name="documentation"/><h2>Search-Indexer Strategy API</h2> 
 * This package contains the API for (delta-)indexing strategies. This API is 
 * not intended for end-users.<br/>
 * If you have a lot of content that changes over the time and you want to have 
 * a full-text search available that is reasonable up-to-date all the time, you
 * need to re-index this content frequently. Now if you have tons of files in
 * formats like PDF, etc. you do not re-index them from scratch all the time.
 * Extracting their texts even if most of them did not change might waste a lot 
 * of performance and time that reduces the actuality of your search.<br/>
 * Therefore the {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy} 
 * abstracts from the implementation of the indexing strategy. Multiple 
 * implementations are available from full re-indexing every time up to 
 * strategies that figure out which files have been added, modified and deleted
 * since the last indexing and perform according delta-indexing.<br/>
 * The end-user can select a dedicated strategy via the
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerSource#getUpdateStrategy() 
 * update-strategy} in the {@link net.sf.mmm.search.api.config.SearchConfiguration}.
 */
package net.sf.mmm.search.indexer.api.strategy;

