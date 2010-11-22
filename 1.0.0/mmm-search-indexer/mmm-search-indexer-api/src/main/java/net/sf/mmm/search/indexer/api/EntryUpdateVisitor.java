/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the interface for a visitor that is
 * {@link #visitIndexedEntryUri(String, ChangeType) notified} about updates of
 * {@link net.sf.mmm.search.api.SearchEntry entries}.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface EntryUpdateVisitor {

  /**
   * This method is called for each
   * {@link net.sf.mmm.util.resource.api.DataResource resource} during indexing
   * of a {@link net.sf.mmm.search.api.config.SearchSource}. It allows to
   * collect information for delta-indexing.
   * 
   * @see ResourceSearchIndexer#index(SearchIndexer,
   *      net.sf.mmm.util.resource.api.DataResource, ChangeType,
   *      net.sf.mmm.search.indexer.api.config.SearchIndexerDataLocation,
   *      EntryUpdateVisitor)
   * 
   * @param uri is the {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of
   *        of the {@link net.sf.mmm.search.api.SearchEntry} that is updated or
   *        remains untouched during indexing.
   * @param changeType indicates the change for the
   *        {@link net.sf.mmm.search.api.SearchEntry}. Can also be
   *        <code>null</code> for no change.
   */
  void visitIndexedEntryUri(String uri, ChangeType changeType);

}
