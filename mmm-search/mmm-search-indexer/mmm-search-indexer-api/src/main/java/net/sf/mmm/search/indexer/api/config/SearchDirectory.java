/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.transformer.api.Transformer;

/**
 * TODO: this class ...
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface SearchDirectory {

  /**
   * This method gets the {@link java.net.URL} of the site that should be part
   * of the search index.
   * 
   * @return the path
   */
  String getUrl();

  /**
   * This method gets the {@link SearchSource Source} associated with this
   * directory.
   * 
   * @return the {@link SearchSource}.
   */
  SearchSource getSource();

  /**
   * @return the encoding
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
   * {@link SearchEntry#getUri() URI}. The typical implementation will create
   * the path relative to {@link #getUrl()}. However the {@link Transformer} can
   * rewrite the URI if it differs from the path to access the file online. E.g.
   * the relevant files in the data directory of a twiki installation have the
   * extension .txt while their URLs do NOT contain this extension.
   * 
   * @return the URI-{@link Transformer}.
   */
  Transformer<String> getUriTransformer();

}
