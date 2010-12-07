/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the interface for the configuration of a {@link #getLocationUri()
 * data-location} that should be indexed by the indexer and is then available
 * for search via the {@link net.sf.mmm.search.engine.api.SearchEngine}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexerDataLocation {

  // /**
  // * The {@link #getUpdateStrategyVariant() variant} of
  // * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
  // * href="http://www.samba.org/rsync/">rsync</a>.<br/>
  // * Please be aware that rsync is actually no VCS. However it can provide us
  // * with the required change-set for delta-indexing just like a VCS.
  // */
  // String UPDATE_STRATEGY_VCS_VARIANT_RSYNC = "rsync";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://www.cvshome.org/">Concurrent Version System</a>.
   */
  String UPDATE_STRATEGY_VCS_VARIANT_CVS = "cvs";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://subversion.apache.org">Subversion</a> (svn).
   */
  String UPDATE_STRATEGY_VCS_VARIANT_SVN = "svn";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://git-scm.com/">Git</a>.
   */
  String UPDATE_STRATEGY_VCS_VARIANT_GIT = "git";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://mercurial.selenic.com/">Mercurial</a> (hg).
   */
  String UPDATE_STRATEGY_VCS_VARIANT_MERCURIAL = "hg";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://bazaar.canonical.com/">Bazaar</a> (bzr).
   */
  String UPDATE_STRATEGY_VCS_VARIANT_BAZAAR = "bzr";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://www.perforce.com/">Perforce</a> (p4).
   */
  String UPDATE_STRATEGY_VCS_VARIANT_PERFORCE = "perforce";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://msdn.microsoft.com/en-us/vstudio/aa718670.aspx">Visual Source
   * Safe</a>.
   */
  String UPDATE_STRATEGY_VCS_VARIANT_VSS = "vss";

  /**
   * The {@link #getUpdateStrategyVariant() variant} of
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS} for <a
   * href="http://msdn.microsoft.com/en-us/vstudio/ff637362.aspx">Team
   * Foundation Server</a>.
   */
  String UPDATE_STRATEGY_VCS_VARIANT_TFS = "tfs";

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
  String getLocationUri();

  /**
   * This method gets the base-URI used to build the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() search-entry-URI} for the
   * {@link #getLocationUri() location} itself.<br>
   * This will typically be the empty string. However if you want to index
   * multiple {@link SearchIndexerDataLocation locations} of the same
   * {@link SearchIndexerSource source} you can use this attribute. E.g. when
   * {@link SearchIndexerSource#getId() source} is <code>"svn"</code> and you
   * have one {@link SearchIndexerDataLocation location} that
   * {@link #getLocationUri() points} to where you checked out "trunk/documents"
   * of a subversion repository then the {@link #getBaseUri() base-URI} may be
   * <code>"documents"</code>. Analog you may also have another
   * {@link SearchIndexerDataLocation location} with a {@link #getBaseUri()
   * base-URI} of <code>"development/code"</code>. Therefore
   * {@link SearchIndexerSource#getUrlPrefix()} may be something like
   * <code>"http://svn.foo.bar/trunk/"</code>.
   * 
   * @return the base-URI for this location. If the base-URI is NOT empty, it
   *         should end with a slash ("/").
   */
  String getBaseUri();

  /**
   * This method determines if
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() search-entry-URIs} should
   * be set to the absolute {@link #getLocationUri() location}. This can make
   * sense if you want to index a web-site via an HTTP-{@link #getLocationUri()
   * location} but also some related sites linked from there. In all other cases
   * this should be <code>false</code>.
   * 
   * @see #getBaseUri()
   * 
   * @return <code>true</code> if the absolute URIs should be stored in the
   *         search-index, <code>false</code> if the URIs should be relative to
   *         {@link #getLocationUri() location} with respect to the
   *         {@link #getBaseUri() base-URI}.
   */
  boolean isAbsoluteUris();

  /**
   * This method gets the {@link SearchIndexerSource Source} associated with
   * this directory.
   * 
   * @return the {@link SearchIndexerSource}.
   */
  SearchIndexerSource getSource();

  /**
   * This method gets the encoding of the resources in this location. If the
   * encoding is specified (e.g. via content formats as XML or HTML) that
   * specific encoding is used. - otherwise the default encoding returned by
   * this method is used. Therefore you should typically not choose an UTF
   * encoding.
   * 
   * @return the encoding used as fallback or <code>null</code> for automatic
   *         unicode detection.
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

  /**
   * This method determines the optional variant of the
   * {@link SearchIndexerSource#getUpdateStrategy() update-strategy}.<br/>
   * E.g. if the {@link SearchIndexerSource#getUpdateStrategy() update-strategy}
   * of the {@link #getSource() according} {@link SearchIndexerSource} is
   * {@link SearchIndexerSource#UPDATE_STRATEGY_VCS}, then this variant can
   * determine the actual VCS to use (see
   * <code>UPDATE_STRATEGY_VCS_VARIANT_*</code> constants like
   * {@link #UPDATE_STRATEGY_VCS_VARIANT_SVN}). However if not set (
   * <code>null</code>) the VCS will be auto detected (by looking for folders
   * like <code>.svn</code>, <code>.hg</code>, <code>.git</code>, etc.)
   * 
   * @return the variant for the {@link SearchIndexerSource#getUpdateStrategy()
   *         update-strategy} or <code>null</code> for default.
   */
  String getUpdateStrategyVariant();

}
