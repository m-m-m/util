/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.state;

/**
 * This is the interface for the state of the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer} for a
 * particular {@link #getLocation() location}. It stores the
 * {@link #getRevision() revision} of that location if available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexDataLocationState {

  /**
   * This method gets the
   * {@link net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation#getLocation()
   * location} this state is associated with.
   * 
   * @return the location.
   */
  String getLocation();

  /**
   * This method gets the revision of the {@link #getLocation() location} that
   * has been indexed.
   * 
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation#getUpdateType()
   * 
   * @return the revision or <code>null</code> if no revision is set.
   */
  String getRevision();

  /**
   * This method sets the {@link #getRevision() revision}.
   * 
   * @param revision is the new revision.
   */
  void setRevision(String revision);

}
