/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl.strategy;

import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.SearchEngine;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.engine.api.SearchQuery;
import net.sf.mmm.search.engine.api.SearchResultPage;
import net.sf.mmm.search.indexer.api.CountingEntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;
import net.sf.mmm.search.indexer.base.CountingEntryUpdateVisitorCollector;
import net.sf.mmm.search.indexer.base.strategy.AbstractCrawlingDeltaSearchIndexer;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.resource.api.BrowsableResource;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy}
 * for {@link SearchIndexerSource#UPDATE_STRATEGY_VCS}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchIndexerUpdateStrategyLastModified extends AbstractCrawlingDeltaSearchIndexer {

  /**
   * The constructor.
   */
  public SearchIndexerUpdateStrategyLastModified() {

    super(SearchIndexerSource.UPDATE_STRATEGY_LAST_MODIFIED);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CountingEntryUpdateVisitor createEntryUpdateVisitor() {

    return new CountingEntryUpdateVisitorCollector();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeType getChangeType(BrowsableResource resource, UpdateStrategyArguments arguments) {

    ChangeType changeType;
    Boolean modified = resource.isModifiedSince(arguments.getSourceState().getIndexingDate());
    if (Boolean.FALSE.equals(modified)) {
      changeType = null;
    } else {
      // if modified we do not know if it was created or updated since
      // index-date...
      changeType = ChangeType.UPDATE;
    }
    return changeType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void postIndex(UpdateStrategyArguments arguments) {

    if (!isFullIndexing(arguments)) {
      removeNotVisitedResourceEntries(arguments);
    }
    super.postIndex(arguments);
  }

  /**
   * This method removes all entries from the search-indexer that have NOT been
   * {@link CountingEntryUpdateVisitor#visitIndexedEntryUri(String, net.sf.mmm.util.event.api.ChangeType)
   * visited} during the indexing/crawling.
   * 
   * @see #createEntryUpdateVisitor()
   * 
   * @param arguments are the {@link UpdateStrategyArguments}.
   */
  protected void removeNotVisitedResourceEntries(UpdateStrategyArguments arguments) {

    // delta-indexing: delete entries that have NOT been visited again
    SearchIndexer indexer = arguments.getIndexer();
    SearchEngine searchEngine = indexer.getSearchEngine();
    String sourceId = arguments.getSource().getId();
    SearchQuery query = searchEngine.getQueryBuilder().createTermQuery(SearchEntry.PROPERTY_SOURCE,
        sourceId);
    int hitsPerPage = Integer.MAX_VALUE;
    // get all hits... (TODO: use paging...?)
    SearchResultPage page = searchEngine.search(query, hitsPerPage);
    CountingEntryUpdateVisitorCollector visitor = (CountingEntryUpdateVisitorCollector) arguments
        .getContext().getVariable(CONTEXT_VARIABLE_ENTRY_UPDATE_VISITOR);
    Set<String> entryUriSet = visitor.getEntryUriSet();
    for (int i = 0; i < page.getPageHitCount(); i++) {
      SearchHit hit = page.getPageHit(i);
      String uri = hit.getUri();
      if (!entryUriSet.contains(uri)) {
        indexer.remove(hit);
      }
    }

  }

}
