/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import net.sf.mmm.search.api.config.SearchSource;

/**
 * This interface represents the view on a {@link SearchSource}. It extends {@link SearchSource} with
 * view-specific attributes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface SearchSourceView extends SearchSource {

  /**
   * This method gets the {@link net.sf.mmm.search.engine.api.SearchEngine#count(String, String) entry-count}
   * for this {@link SearchSourceView}.
   * 
   * @return the number of search-entries available for this {@link SearchSourceView}.
   */
  long getEntryCount();

}
