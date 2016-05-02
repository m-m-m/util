/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for a search.
 * <a name="documentation"></a><h2>Search-Util API</h2>
 * This package contains the basic API for search related objects (
 * {@link net.sf.mmm.util.search.api.SearchCriteria} and {@link net.sf.mmm.util.search.api.SearchResult}). It aims to
 * standardize the API without requiring specific dependencies (e.g. to JPA or Lucene). The advanced support is
 * therefore located in separate modules like {@code mmm-persistence}. Instead, this API here may also be used by
 * transfer objects for clients and other stuff where the technology specific dependency should be avoided.
 */
package net.sf.mmm.util.search.api;

