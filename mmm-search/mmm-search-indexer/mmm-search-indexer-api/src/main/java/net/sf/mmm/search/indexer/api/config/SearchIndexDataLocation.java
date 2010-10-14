/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the interface for the configuration of a {@link #getLocation()
 * data-location} that should be indexed by the indexer and is then available
 * for search via the {@link net.sf.mmm.search.engine.api.SearchEngine}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexDataLocation {

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://www.cvshome.org/">Concurrent Version System</a>.
   */
  String UPDATE_TYPE_CVS = "cvs";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://subversion.apache.org">Subversion</a>.
   */
  String UPDATE_TYPE_SVN = "svn";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://git-scm.com/">Git</a>.
   */
  String UPDATE_TYPE_GIT = "git";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://mercurial.selenic.com/">Mercurial</a>.
   */
  String UPDATE_TYPE_HG = "hg";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://bazaar.canonical.com/">Bazaar</a>.
   */
  String UPDATE_TYPE_BAZAAR = "bazaar";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://www.perforce.com/">Perforce</a>.
   */
  String UPDATE_TYPE_PERFORCE = "perforce";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://msdn.microsoft.com/en-us/vstudio/aa718670.aspx">Visual Source
   * Safe</a>.
   */
  String UPDATE_TYPE_VSS = "vss";

  /**
   * The {@link #getUpdateType() update-type} for <a
   * href="http://msdn.microsoft.com/en-us/vstudio/ff637362.aspx">Team
   * Foundation Server</a>.
   */
  String UPDATE_TYPE_TFS = "tfs";

  /**
   * This method gets the location (typically an URL) with the data that should
   * be part of the search index. This location will be indexed recursively
   * according to the {@link #getFilter() filter}.<br>
   * <ul>
   * <li>File-URLs should point to a directory to index (e.g.
   * "file:///data/documents")</li>
   * <li>HTTP(S)-URLs should point to a web-site to index via a crawler.</li>
   * </ul>
   * 
   * @return the URI to index.
   */
  String getLocation();

  /**
   * This method gets the optional type used for incremental updates of this
   * {@link #getLocation() location}.<br/>
   * This is typically done by some version-control-system (VCS) but also other
   * approaches like <code>rsync</code> are possible. In case of a VCS, the
   * {@link #getLocation() location} should be a local working copy of a checked
   * out VCS-repository. This initial checkout has to be done once outside of
   * the search-indexer in advance. This is done by intention because you
   * potentially have to deal with verification of certificates (e.g. for SSL in
   * https) and provide authentication credentials. You can still use a wrapper
   * shell script for further automation.<br/>
   * <br/>
   * If this option is set (NOT <code>null</code>), then the search-indexer will
   * do incremental indexing by performing an update on this working copy and
   * modify the index accordingly. If you do NOT want delta-indexing simply omit
   * this attribute and do updates in a wrapper script before calling the
   * search-indexer.<br/>
   * <b>ATTENTION:</b><br/>
   * This feature requires that the {@link #getLocation() location} is dedicated
   * to the search-indexer and does not get updated form outside of the
   * search-indexer. The indexer will save the revision (or date of update) of
   * the working copy in the index-database and can thereby detect if this is
   * the initial indexing or a delta-update should be performed. In case of a
   * VCS repository it can also detect if the working copy was updated
   * externally and a delta-update is NOT possible.
   * 
   * @see #UPDATE_TYPE_SVN
   * 
   * @return the type of the update mechanism or <code>null</code> if no
   *         delta-update-indexing shall be performed. Use one of the
   *         <code>UPDATE_TYPE_*</code> constants. The default implementation
   *         uses <code>maven-scm</code> as abstraction layer on VCS. In such
   *         case this is the role-hint for the scm-provider.
   */
  String getUpdateType();

  /**
   * This method gets the base-URI used to build the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() search-entry-URI} for the
   * {@link #getLocation() location} itself.<br>
   * This will typically be the empty string. However if you want to index
   * multiple {@link SearchIndexDataLocation sub-directories} of the same
   * {@link SearchSource source} you can use this attribute. E.g. when
   * {@link SearchSource#getId() source} is <code>"svn"</code> and
   * {@link #getLocation()} points to where you checked out a subversion
   * repository then this {@link #getBaseUri() base-URI} may be
   * <code>"documents"</code> for one {@link SearchIndexDataLocation location}
   * and <code>"development/code"</code> for another. You could also use
   * <code>"http://svn.foo.bar/trunk/documents"</code> as {@link #getBaseUri()
   * base-URI} but this would cause a lot of unnecessary redundancies in your
   * index. So please use {@link SearchSource#getUrlPrefix()} for the general
   * prefix.
   * 
   * @return the base-URI for this location. If the base-URI is NOT empty, it
   *         should end with a slash ("/").
   */
  String getBaseUri();

  /**
   * This method determines if
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() search-entry-URIs} should
   * be set to the absolute {@link #getLocation() location}. This can make sense
   * if you want to index a web-site via an HTTP-{@link #getLocation() location}
   * but also some related sites linked from there. In all other cases this
   * should be <code>false</code>.
   * 
   * @see #getBaseUri()
   * 
   * @return <code>true</code> if the absolute URIs should be stored in the
   *         search-index, <code>false</code> if the URIs should be relative to
   *         {@link #getLocation() location} with respect to the
   *         {@link #getBaseUri() base-URI}.
   */
  boolean isAbsoluteUris();

  /**
   * This method gets the {@link SearchSource Source} associated with this
   * directory.
   * 
   * @return the {@link SearchSource}.
   */
  SearchSource getSource();

  /**
   * This method gets the default encoding used for resources where no encoding
   * is available. If the encoding is specified (e.g. via content formats as XML
   * or HTML) or autodetected (e.g. Unicode) that specific encoding is used -
   * otherwise the default encoding returned by this method is used.
   * 
   * @return the encoding used as fallback.
   */
  String getEncoding();

  /**
   * This method gets the {@link Filter} that decides which resource-URIs should
   * be {@link Filter#accept(Object) accepted} for indexing.<br/>
   * <b>ATTENTION:</b><br>
   * This filter is applied to {@link net.sf.mmm.util.resource.api.DataResource
   * files} AND {@link net.sf.mmm.util.resource.api.BrowsableResource folders}.
   * If a folder is NOT {@link Filter#accept(Object) accepted} then this entire
   * folder is NOT crawled any further and therefore its
   * {@link net.sf.mmm.util.resource.api.BrowsableResource#getChildResources()
   * children} are NOT indexed. All
   * {@link net.sf.mmm.util.resource.api.DataResource files} that are crawled
   * and {@link Filter#accept(Object) accepted} will be
   * {@link net.sf.mmm.search.indexer.api.SearchIndexer#add(net.sf.mmm.search.indexer.api.MutableSearchEntry)
   * added to the search-index}.
   * 
   * @return the {@link Filter}.
   */
  Filter<String> getFilter();

  /**
   * This method gets the {@link Transformer} used to
   * {@link Transformer#transform(Object) convert} the (relative) URI of a
   * resource to the {@link net.sf.mmm.search.api.SearchEntry#getUri() indexed
   * URI}. By default the transformer will keep the URI untouched. However the
   * {@link Transformer} can rewrite the URI if it differs from the path to
   * access the file online. E.g. the relevant files in the data directory of a
   * twiki/foswiki installation have the extension .txt while their URLs do NOT
   * contain this extension.
   * 
   * @return the URI-{@link Transformer}.
   */
  Transformer<String> getUriTransformer();

}
