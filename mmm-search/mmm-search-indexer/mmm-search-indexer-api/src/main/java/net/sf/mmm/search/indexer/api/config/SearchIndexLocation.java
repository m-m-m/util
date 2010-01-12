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
public interface SearchIndexLocation {

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
   * This method gets the {@link Filter} that decides which resource-URLs should
   * be {@link Filter#accept(Object) accepted} for indexing.
   * 
   * @return the {@link Filter}.
   */
  Filter<String> getFilter();

  /**
   * This method gets the {@link Transformer} used to
   * {@link Transformer#transform(Object) convert} the URL of a resource to its
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() URI}. The typical
   * implementation will create the path relative to {@link #getLocation()}.
   * However the {@link Transformer} can rewrite the URI if it differs from the
   * path to access the file online. E.g. the relevant files in the data
   * directory of a twiki installation have the extension .txt while their URLs
   * do NOT contain this extension.
   * 
   * @return the URI-{@link Transformer}.
   */
  Transformer<String> getUriTransformer();

}
