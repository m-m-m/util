/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation;
import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the interface for a higher-level {@link SearchIndexer}. It can index
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface ResourceSearchIndexer {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.search.indexer.api.ResourceSearchIndexer";

  /**
   * This method creates a {@link MutableSearchEntry} for a single {@link DataResource}. It may be used for
   * specific situations when the {@link ConfiguredSearchIndexer} is NOT suitable. You can
   * {@link MutableSearchEntry#setField(String, Object) add} additional fields before
   * {@link SearchIndexer#add(MutableSearchEntry) adding} it to the index.<br/>
   * <b>ATTENTION:</b><br>
   * This method will NOT {@link SearchIndexer#add(MutableSearchEntry) index} the given <code>resource</code>.
   * You have to do that manually after calling this method.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param resourceUri is the {@link MutableSearchEntry#getUri() URI} for the {@link MutableSearchEntry
   *        entry}.
   * @return the created {@link MutableSearchEntry}.
   */
  MutableSearchEntry createEntry(SearchIndexer indexer, DataResource resource, String resourceUri);

  /**
   * This method indexes a single {@link DataResource}.<br/>
   * <b>ATTENTION:</b><br>
   * If you are indexing many {@link DataResource resources}, you should use
   * {@link #index(SearchIndexer, DataResource, ChangeType, SearchIndexerDataLocation, EntryUpdateVisitor, DataResource)}
   * to avoid performance-overhead.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case of {@link ChangeType#ADD add}
   *        it should be {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be {@link SearchIndexer#update(MutableSearchEntry)
   *        updated} and in case of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, Object) removed}. The <code>changeType</code> can also be
   *        <code>null</code> to indicate that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the <code>resource</code> comes from.
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType, SearchIndexerDataLocation location);

  /**
   * This method indexes a single {@link DataResource}.<br/>
   * <b>ATTENTION:</b><br/>
   * If you are indexing many {@link DataResource resources}, you should use
   * {@link #index(SearchIndexer, DataResource, ChangeType, SearchIndexerDataLocation, EntryUpdateVisitor, DataResource)}
   * to avoid performance-overhead.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case of {@link ChangeType#ADD add}
   *        it should be {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be {@link SearchIndexer#update(MutableSearchEntry)
   *        updated} and in case of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, Object) removed}. The <code>changeType</code> can also be
   *        <code>null</code> to indicate that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the <code>resource</code> comes from.
   * @param uriVisitor is the {@link EntryUpdateVisitor}.
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType, SearchIndexerDataLocation location,
      EntryUpdateVisitor uriVisitor);

  /**
   * This method indexes a single {@link DataResource}.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case of {@link ChangeType#ADD add}
   *        it should be {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be {@link SearchIndexer#update(MutableSearchEntry)
   *        updated} and in case of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, Object) removed}. The <code>changeType</code> can also be
   *        <code>null</code> to indicate that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the <code>resource</code> comes from.
   * @param uriVisitor is the {@link EntryUpdateVisitor}.
   * @param locationFolder is the {@link DataResource} for {@link SearchIndexerDataLocation#getLocationUri()}.
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType, SearchIndexerDataLocation location,
      EntryUpdateVisitor uriVisitor, DataResource locationFolder);

  /**
   * This method indexes a single {@link DataResource}.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case of {@link ChangeType#ADD add}
   *        it should be {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be {@link SearchIndexer#update(MutableSearchEntry)
   *        updated} and in case of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, Object) removed}. The <code>changeType</code> can also be
   *        <code>null</code> to indicate that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the <code>resource</code> comes from.
   * @param uriVisitor is the {@link EntryUpdateVisitor}.
   * @param locationFolder is the {@link DataResource} for {@link SearchIndexerDataLocation#getLocationUri()}.
   * @param nonUtfEncoding is the
   *        {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration#getNonUtfEncoding() non-UTF
   *        encoding}.
   * @since 1.0.1
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType, SearchIndexerDataLocation location,
      EntryUpdateVisitor uriVisitor, DataResource locationFolder, String nonUtfEncoding);

}
