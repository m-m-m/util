/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.strategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.indexer.api.CountingEntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.search.indexer.api.ResourceSearchIndexer;
import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.search.indexer.api.strategy.SearchIndexerUpdateStrategy;
import net.sf.mmm.search.indexer.api.strategy.UpdateStrategyArguments;
import net.sf.mmm.search.indexer.base.CountingEntryUpdateVisitorDefault;
import net.sf.mmm.search.indexer.base.IndexerDependencies;
import net.sf.mmm.search.indexer.base.IndexerDependenciesImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.resource.api.BrowsableResource;

/**
 * This is the abstract base-implementation of the
 * {@link SearchIndexerUpdateStrategy} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerUpdateStrategy extends AbstractLoggableComponent
    implements SearchIndexerUpdateStrategy {

  /**
   * The key for the {@link Set} of {@link BrowsableResource#getUri()
   * resource-URIs}.
   */
  protected static final String CONTEXT_VARIABLE_RESOURCE_URI_SET = "resourceUriSet";

  /**
   * The key for the {@link CountingEntryUpdateVisitor}.
   */
  protected static final String CONTEXT_VARIABLE_ENTRY_UPDATE_VISITOR = CountingEntryUpdateVisitor.class
      .getSimpleName();

  /** @see #getIndexerDependencies() */
  private IndexerDependencies indexerDependencies;

  /**
   * The constructor.
   */
  public AbstractSearchIndexerUpdateStrategy() {

    super();
  }

  /**
   * @return the deltaIndexerDependencies
   */
  protected IndexerDependencies getIndexerDependencies() {

    return this.indexerDependencies;
  }

  /**
   * @param indexerDependencies is the deltaIndexerDependencies to set
   */
  @Inject
  public void setIndexerDependencies(IndexerDependencies indexerDependencies) {

    getInitializationState().requireNotInitilized();
    this.indexerDependencies = indexerDependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.indexerDependencies == null) {
      IndexerDependenciesImpl impl = new IndexerDependenciesImpl();
      impl.initialize();
      this.indexerDependencies = impl;
    }
  }

  /**
   * This method determines if full indexing from scratch shall be performed.
   * 
   * @param arguments are the {@link UpdateStrategyArgumentsBean}.
   * @return <code>true</code> if all resources shall be added to the indexed,
   *         <code>false</code> if only modified resources shall be updated in
   *         the index.
   */
  protected boolean isFullIndexing(UpdateStrategyArguments arguments) {

    // full indexing active by options or first indexing at all...
    return arguments.getOptions().isOverwriteEntries()
        || (arguments.getSourceState().getIndexingDate() == null);
  }

  /**
   * This method is called at the beginning of
   * {@link #index(UpdateStrategyArguments)} to setup the indexing.
   * 
   * @param arguments are the {@link UpdateStrategyArgumentsBean}.
   */
  protected void preIndex(UpdateStrategyArguments arguments) {

    if (isFullIndexing(arguments)) {
      String sourceId = arguments.getSource().getId();
      // skip this if this is the first indexing or the complete index has been
      // re-created from scratch.
      if ((arguments.getSourceState().getIndexingDate() != null)
          && (arguments.getOptions().isOverwriteIndex())) {
        getLogger().debug("Removing all search-entries for source: " + sourceId);
        int removeCount = arguments.getIndexer().remove(SearchEntry.FIELD_SOURCE, sourceId);
        getLogger().debug("Removed " + removeCount + " entries/entry.");
      }
    }
  }

  /**
   * This method is called at the end of {@link #index(UpdateStrategyArguments)}
   * to complete the indexing.
   * 
   * @param arguments are the {@link UpdateStrategyArgumentsBean}.
   */
  protected void postIndex(UpdateStrategyArguments arguments) {

  }

  /**
   * {@inheritDoc}
   */
  public void index(UpdateStrategyArguments arguments) {

    Date indexingDate = new Date();
    boolean fullIndexing = isFullIndexing(arguments);
    CountingEntryUpdateVisitor entryUpdateVisitor = getEntryUpdateVisitor(arguments);
    preIndex(arguments);
    for (SearchIndexerDataLocation location : arguments.getSource().getLocations()) {
      if (fullIndexing) {
        // if overwrite is active, then we will always crawl recursively
        indexRecursive(arguments, location, entryUpdateVisitor);
      } else {
        index(arguments, location, entryUpdateVisitor);
      }
    }
    postIndex(arguments);
    arguments.getSourceState().setIndexingDate(indexingDate);
    // statistics
    StringBuilder sb = new StringBuilder("Indexing statistics for source '");
    sb.append(arguments.getSource().getId());
    sb.append("': Unmodified=");
    sb.append(entryUpdateVisitor.getChangeCount(null));
    for (ChangeType type : ChangeType.values()) {
      sb.append(", ");
      sb.append(type);
      sb.append("=");
      sb.append(entryUpdateVisitor.getChangeCount(type));
    }
    getLogger().info(sb.toString());
    arguments.getContext().removeVariable(CONTEXT_VARIABLE_ENTRY_UPDATE_VISITOR);
  }

  /**
   * This method indexes as single {@link SearchIndexerDataLocation location}.
   * 
   * @param arguments are the {@link UpdateStrategyArgumentsBean}.
   * @param location is the {@link SearchIndexerDataLocation} to index.
   * @param entryUpdateVisitor is the {@link EntryUpdateVisitor}.
   */
  public abstract void index(UpdateStrategyArguments arguments, SearchIndexerDataLocation location,
      EntryUpdateVisitor entryUpdateVisitor);

  /**
   * This method indexes as single {@link SearchIndexerDataLocation location} by
   * recursively crawling the {@link BrowsableResource resources}.
   * 
   * @param arguments are the {@link UpdateStrategyArgumentsBean}.
   * @param location is the {@link SearchIndexerDataLocation} to index.
   * @param entryUpdateVisitor is the {@link EntryUpdateVisitor}.
   */
  @SuppressWarnings("unchecked")
  public final void indexRecursive(UpdateStrategyArguments arguments,
      SearchIndexerDataLocation location, EntryUpdateVisitor entryUpdateVisitor) {

    String locationUri = location.getLocationUri();
    BrowsableResource resource = getIndexerDependencies().getBrowsableResourceFactory()
        .createBrowsableResource(locationUri);
    Set<String> entryUriSet = arguments.getContext().getVariable(CONTEXT_VARIABLE_RESOURCE_URI_SET,
        Set.class);
    if (entryUriSet == null) {
      entryUriSet = new HashSet<String>();
      arguments.getContext().setVariable(CONTEXT_VARIABLE_RESOURCE_URI_SET, entryUriSet);
    }
    indexRecursive(arguments, location, resource, entryUriSet, entryUpdateVisitor, resource);
  }

  /**
   * This method creates the {@link CountingEntryUpdateVisitor} to use.
   * 
   * @see ResourceSearchIndexer#index(net.sf.mmm.search.indexer.api.SearchIndexer,
   *      net.sf.mmm.util.resource.api.DataResource, ChangeType,
   *      SearchIndexerDataLocation, EntryUpdateVisitor)
   * 
   * @return the {@link CountingEntryUpdateVisitor}.
   */
  protected CountingEntryUpdateVisitor createEntryUpdateVisitor() {

    return new CountingEntryUpdateVisitorDefault();
  }

  /**
   * This method gets the {@link CountingEntryUpdateVisitor} to use.
   * 
   * @see #createEntryUpdateVisitor()
   * 
   * @param arguments are the {@link UpdateStrategyArguments}.
   * @return the {@link CountingEntryUpdateVisitor}.
   */
  protected final CountingEntryUpdateVisitor getEntryUpdateVisitor(UpdateStrategyArguments arguments) {

    CountingEntryUpdateVisitor visitor = arguments.getContext().getVariable(
        CONTEXT_VARIABLE_ENTRY_UPDATE_VISITOR, CountingEntryUpdateVisitor.class);
    if (visitor == null) {
      visitor = createEntryUpdateVisitor();
      arguments.getContext().setVariable(CONTEXT_VARIABLE_ENTRY_UPDATE_VISITOR, visitor);
    }
    return visitor;
  }

  /**
   * This method determines the {@link ChangeType} of the given
   * <code>resource</code>.
   * 
   * @see net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState#getIndexingDate()
   * 
   * @param resource is the {@link BrowsableResource} for which to determine the
   *        {@link ChangeType}.
   * @param arguments are the {@link UpdateStrategyArguments}.
   * @return the {@link ChangeType} indicating how the given
   *         <code>resource</code> shall be updated or <code>null</code> to
   *         indicate that the <code>resource</code> shall remain unchanged.
   */
  protected ChangeType getChangeType(BrowsableResource resource, UpdateStrategyArguments arguments) {

    return ChangeType.ADD;
  }

  /**
   * This method starts the indexing from the given <code>directory</code>
   * adding the given <code>source</code> as metadata.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   * 
   * @param arguments are the {@link UpdateStrategyArguments}.
   * @param location is the {@link SearchIndexerDataLocation}.
   * @param resource is the {@link BrowsableResource resource} (directory) to
   *        index recursively.
   * @param visitedResources is the {@link Set} with the
   *        {@link net.sf.mmm.util.resource.api.DataResource#getUri() URIs} of
   *        the {@link BrowsableResource resources} that have already been
   *        crawled.
   * @param entryUpdateVisitor is the {@link EntryUpdateVisitor}.
   * @param locationResource is the {@link BrowsableResource resource} for
   *        {@link SearchIndexerDataLocation#getLocationUri()} of the given
   *        <code>location</code>.
   */
  public void indexRecursive(UpdateStrategyArguments arguments, SearchIndexerDataLocation location,
      BrowsableResource resource, Set<String> visitedResources,
      EntryUpdateVisitor entryUpdateVisitor, BrowsableResource locationResource) {

    String uri = resource.getUri();
    if (!visitedResources.contains(uri)) {
      visitedResources.add(uri);
      if (resource.isData()) {
        ChangeType changeType;
        if (isFullIndexing(arguments)) {
          changeType = ChangeType.ADD;
        } else {
          changeType = getChangeType(resource, arguments);
        }
        ResourceSearchIndexer resourceSearchIndexer = getIndexerDependencies()
            .getResourceSearchIndexer();
        resourceSearchIndexer.index(arguments.getIndexer(), resource, changeType, location,
            entryUpdateVisitor, locationResource, arguments.getOptions().getNonUtfEncoding());
      }
      for (BrowsableResource child : resource.getChildResources()) {
        String childUri = child.getUri().replace('\\', '/');
        if (location.getFilter().accept(childUri)) {
          indexRecursive(arguments, location, child, visitedResources, entryUpdateVisitor,
              locationResource);
        } else {
          if (getLogger().isTraceEnabled()) {
            getLogger().trace("Filtered " + childUri);
          }
        }
      }
    }
  }
}
