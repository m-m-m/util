/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api.config;

import java.util.List;

/**
 * This is the interface for the configuration of the
 * {@link net.sf.mmm.search.engine.api.SearchEngine} and
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer}.<br>
 * You will typically provide your configuration as XML. The base-implementation
 * comes with an according (un)marshaler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchConfiguration {

  /**
   * This method gets the {@link List} of {@link SearchSource sources}.
   * 
   * @return the {@link List} of {@link SearchSource}s.
   */
  List<? extends SearchSource> getSources();

  /**
   * This method gets the {@link List} of {@link SearchDirectory directories}.
   * 
   * @return the {@link List} of {@link SearchDirectory}.
   */
  List<? extends SearchDirectory> getDirectories();

}
