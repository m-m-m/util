/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import java.util.List;

import net.sf.mmm.search.api.config.SearchSource;

/**
 * This is the interface extends {@link SearchSource} with configuration
 * required for the search-indexer.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerSource extends SearchSource {

  /**
   * The {@link #getUpdateStrategy() update-type} for no incremental updates. In
   * this case all entries for this source will always be removed and everything
   * is re-indexed from scratch.
   */
  String UPDATE_STRATEGY_NONE = "none";

  /**
   * The {@link #getUpdateStrategy() update-type} for delta-updating via
   * date/time of the last modification of the resources to index.
   * 
   * @see net.sf.mmm.util.resource.api.BrowsableResource#isModifiedSince(java.util.Date)
   */
  String UPDATE_STRATEGY_LAST_MODIFIED = "last-modified";

  /**
   * The {@link #getUpdateStrategy() update-type} for delta-updating using a
   * version-control-system (VCS).<br/>
   * In case of a VCS, the location(s) should be a local working copy of a
   * checked out VCS-repository. This initial checkout has to be done once
   * outside of the search-indexer in advance. This is done by intention because
   * you potentially have to deal with verification of certificates (e.g. for
   * SSL in https) and provide authentication credentials. You can still use a
   * wrapper shell script for further automation.<br/>
   * <b>ATTENTION:</b><br/>
   * Using a VCS as {@link #getUpdateStrategy() update-strategy} requires that
   * the locations for this source are dedicated to the search-indexer and do
   * not get updated form outside of the search-indexer. The indexer will save
   * the revision (or date of update) of the working copy and can thereby detect
   * if this is the initial indexing or a delta-update should be performed. It
   * may also detect if the working copy was updated externally and a
   * delta-update is NOT possible. If you can not guarantee dedication of the
   * locations, simply use {@link #UPDATE_STRATEGY_LAST_MODIFIED} instead and
   * perform VCS updates in a wrapper script.
   * 
   * See <code>SearchIndexDataLocation.getUpdateStrategyVariant()</code> for
   * further details.
   */
  String UPDATE_STRATEGY_VCS = "vcs";

  /**
   * This method gets the identifier of the strategy used for (incremental)
   * updates of this {@link SearchSource}. This property is of interest for the
   * search-indexer and can be ignored for the search-engine (due to the lack of
   * full generic support in JAXB we decided NOT to move this method to a
   * sub-interface in the search-indexer-api).<br/>
   * The default update-type is {@link #UPDATE_STRATEGY_LAST_MODIFIED} that
   * should work for all locations/resources that have a modification date.
   * Besides {@link #UPDATE_STRATEGY_NONE} there is also
   * {@link #UPDATE_STRATEGY_VCS} available.
   * 
   * @see #UPDATE_STRATEGY_LAST_MODIFIED
   * @see #UPDATE_STRATEGY_VCS
   * @see #UPDATE_STRATEGY_NONE
   * 
   * @return the type of the update mechanism or <code>null</code> if no
   *         delta-update-indexing shall be performed. Use one of the
   *         <code>UPDATE_TYPE_*</code> constants. The default implementation
   *         uses <code>maven-scm</code> as abstraction layer on VCS. In such
   *         case this is the role-hint for the scm-provider.
   */
  String getUpdateStrategy();

  /**
   * This method gets the {@link List} of {@link SearchIndexerDataLocation
   * directories}.
   * 
   * @return the {@link List} of {@link SearchIndexerDataLocation}.
   */
  List<? extends SearchIndexerDataLocation> getLocations();

}
