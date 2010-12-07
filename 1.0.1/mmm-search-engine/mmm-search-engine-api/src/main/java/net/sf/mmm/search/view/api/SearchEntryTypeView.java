/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import java.util.Collection;

import net.sf.mmm.search.engine.api.config.SearchEntryType;

/**
 * This interface represents the view on a {@link SearchEntryType}.<br/>
 * If you configure multiple {@link SearchEntryType}s with the same
 * {@link #getTitle() title} these are combined to one
 * {@link SearchEntryTypeView}. The first of these that has an
 * {@link #getIcon() icon} set is used to define the {@link #getId() ID} and
 * {@link #getIcon() icon} of this {@link SearchEntryTypeView}. Further only the
 * {@link SearchEntryType}s are represented for which there are actually
 * {@link net.sf.mmm.search.api.SearchEntry}s in the index.
 * 
 * @see net.sf.mmm.search.view.base.SearchEntryTypeViewBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEntryTypeView extends SearchEntryType {

  /**
   * The {@link #getId() IDs} of all {@link SearchEntryType}s that have the same
   * {@link #getTitle() title}. A search filtered to this
   * {@link SearchEntryTypeView} will search for
   * {@link net.sf.mmm.search.api.SearchEntry}s that have a
   * {@link net.sf.mmm.search.api.SearchEntry#getType() type} out of this
   * {@link Collection}.
   * 
   * @return the combined IDs.
   */
  Collection<String> getCombinedIds();

  /**
   * This method gets the
   * {@link net.sf.mmm.search.engine.api.SearchEngine#count(String, String)
   * entry-count} for this {@link SearchEntryTypeView}.
   * 
   * @return the number of search-entries available for this
   *         {@link SearchEntryTypeView}.
   */
  long getEntryCount();

}
