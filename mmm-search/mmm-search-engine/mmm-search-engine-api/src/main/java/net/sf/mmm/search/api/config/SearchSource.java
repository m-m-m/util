/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for the configuration of a source of information for the search-engine (e.g. some
 * VCS-instance, a wiki or some website).
 * 
 * @see #getId()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchSource extends AttributeReadId<String> {

  // /**
  // * The {@link #getUpdateStrategy() update-type} for no incremental updates.
  // In
  // * this case all entries for this source will always be removed and
  // everything
  // * is re-indexed from scratch.
  // */
  // String UPDATE_STRATEGY_NONE = "none";
  //
  // /**
  // * The {@link #getUpdateStrategy() update-type} for delta-updating via
  // * date/time of the last modification of the resources to index.
  // *
  // * @see
  // net.sf.mmm.util.resource.api.BrowsableResource#isModifiedSince(java.util.Date)
  // */
  // String UPDATE_STRATEGY_LAST_MODIFIED = "last-modified";
  //
  // /**
  // * The {@link #getUpdateStrategy() update-type} for delta-updating using a
  // * version-control-system (VCS).<br/>
  // * In case of a VCS, the location(s) should be a local working copy of a
  // * checked out VCS-repository. This initial checkout has to be done once
  // * outside of the search-indexer in advance. This is done by intention
  // because
  // * you potentially have to deal with verification of certificates (e.g. for
  // * SSL in https) and provide authentication credentials. You can still use a
  // * wrapper shell script for further automation.<br/>
  // * <b>ATTENTION:</b><br/>
  // * Using a VCS as {@link #getUpdateStrategy() update-strategy} requires that
  // * the locations for this source are dedicated to the search-indexer and do
  // * not get updated form outside of the search-indexer. The indexer will save
  // * the revision (or date of update) of the working copy and can thereby
  // detect
  // * if this is the initial indexing or a delta-update should be performed. It
  // * may also detect if the working copy was updated externally and a
  // * delta-update is NOT possible. If you can not guarantee dedication of the
  // * locations, simply use {@link #UPDATE_STRATEGY_LAST_MODIFIED} instead and
  // * perform VCS updates in a wrapper script.
  // *
  // * See <code>SearchIndexDataLocation.getUpdateStrategyVariant()</code> for
  // * further details.
  // */
  // String UPDATE_STRATEGY_VCS = "vcs";

  /**
   * The {@link #getId() ID} of the {@link SearchSource} that represents any source. In a search it can be
   * used as wildcard to match all sources.
   */
  String ID_ANY = "";

  /**
   * This method gets the ID of this source. The ID should be a short, technical and unique name that
   * identifies the source within your list of sources (e.g. "SVN", "Wiki", "MyDocuments", "apache.org"). This
   * ID will be added to the search-index for each according {@link net.sf.mmm.search.api.SearchEntry entry}.
   * A {@link net.sf.mmm.search.engine.api.SearchQuery} can be restricted to specific sources via their IDs.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   * 
   * @return the ID of this source.
   */
  @Override
  String getId();

  /**
   * This method gets the display-name of this source. This title can be displayed to end-users in the
   * application where a {@link net.sf.mmm.search.engine.api.SearchQuery} is performed so the user can
   * restrict his search to specific sources.<br/>
   * For localization see {@link net.sf.mmm.util.nls.api}.
   * 
   * @return the title of this source.
   */
  String getTitle();

  /**
   * This method gets the prefix for the URL pointing to the content of a
   * {@link net.sf.mmm.search.api.SearchEntry}. The URL that links to this content is build by appending the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of the {@link net.sf.mmm.search.api.SearchEntry}
   * to this prefix.<br>
   * Typical examples are "http://foo.org/svn/trunk" or "http://foo.org/wiki/".
   * 
   * @return the URL-prefix of this source.
   */
  String getUrlPrefix();

  // /**
  // * This method gets the identifier of the strategy used for (incremental)
  // * updates of this {@link SearchSource}. This property is of interest for
  // the
  // * search-indexer and can be ignored for the search-engine (due to the lack
  // of
  // * full generic support in JAXB we decided NOT to move this method to a
  // * sub-interface in the search-indexer-api).<br/>
  // * The default update-type is {@link #UPDATE_STRATEGY_LAST_MODIFIED} that
  // * should work for all locations/resources that have a modification date.
  // * Besides {@link #UPDATE_STRATEGY_NONE} there is also
  // * {@link #UPDATE_STRATEGY_VCS} available.
  // *
  // * @see #UPDATE_STRATEGY_LAST_MODIFIED
  // * @see #UPDATE_STRATEGY_VCS
  // * @see #UPDATE_STRATEGY_NONE
  // *
  // * @return the type of the update mechanism or <code>null</code> if no
  // * delta-update-indexing shall be performed. Use one of the
  // * <code>UPDATE_TYPE_*</code> constants. The default implementation
  // * uses <code>maven-scm</code> as abstraction layer on VCS. In such
  // * case this is the role-hint for the scm-provider.
  // */
  // String getUpdateStrategy();

}
