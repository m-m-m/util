/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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

  /**
   * This method indexes a single {@link DataResource}.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case
   *        of {@link ChangeType#ADD add} it should be
   *        {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be
   *        {@link SearchIndexer#update(MutableSearchEntry) updated} and in case
   *        of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, String) removed}. The
   *        <code>changeType</code> can also be <code>null</code> to indicate
   *        that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the
   *        <code>resource</code> comes from.
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location);

  /**
   * This method indexes a single {@link DataResource}.
   * 
   * @param indexer is the {@link SearchIndexer} used for indexing.
   * @param resource is the {@link DataResource} to index.
   * @param changeType indicates the change for <code>resource</code>. In case
   *        of {@link ChangeType#ADD add} it should be
   *        {@link SearchIndexer#add(MutableSearchEntry) added}, in case of
   *        {@link ChangeType#UPDATE update} it should be
   *        {@link SearchIndexer#update(MutableSearchEntry) updated} and in case
   *        of {@link ChangeType#REMOVE remove} it should be
   *        {@link SearchIndexer#remove(String, String) removed}. The
   *        <code>changeType</code> can also be <code>null</code> to indicate
   *        that the <code>resource</code> was already indexed and remains
   *        untouched as it is not modified.
   * @param location is the {@link SearchIndexerDataLocation} where the
   *        <code>resource</code> comes from.
   * @param uriVisitor is the {@link EntryUpdateVisitor}.
   */
  void index(SearchIndexer indexer, DataResource resource, ChangeType changeType,
      SearchIndexerDataLocation location, EntryUpdateVisitor uriVisitor);

}
