/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

/**
 * This is the interface for the configuration of a source of information for
 * the search-engine (e.g. some VCS-instance, a wiki or some website).
 * 
 * @see #getId()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchSource {

  /**
   * This method gets the ID of this source. The ID should be a short, technical
   * and unique name that identifies the source within your list of sources
   * (e.g. "SVN", "Wiki", "MyDocuments", "apache.org"). This ID will be added to
   * the search-index for each according
   * {@link net.sf.mmm.search.api.SearchEntry entry}. A
   * {@link net.sf.mmm.search.engine.api.SearchQuery} can be restricted to
   * specific sources via their IDs.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getSource()
   * 
   * @return the ID of this source.
   */
  String getId();

  /**
   * This method gets the display-name of this source. This title can be
   * displayed to end-users in the application where a
   * {@link net.sf.mmm.search.engine.api.SearchQuery} is performed so the user
   * can restrict his search to specific sources.
   * 
   * @return the title of this source.
   */
  String getTitle();

  /**
   * This method gets the prefix for the URL pointing to the content of a
   * {@link net.sf.mmm.search.api.SearchEntry}. The URL that links to this
   * content is build by appending the
   * {@link net.sf.mmm.search.api.SearchEntry#getUri() URI} of the
   * {@link net.sf.mmm.search.api.SearchEntry} to this prefix.<br>
   * Typical examples are "http://foo.org/svn/trunk" or "http://foo.org/wiki/".
   * 
   * @return the URL-prefix of this source.
   */
  String getUrlPrefix();

}
