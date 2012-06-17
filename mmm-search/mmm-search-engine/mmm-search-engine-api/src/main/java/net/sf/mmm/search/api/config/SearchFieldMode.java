/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

/**
 * This enum contains the available modes for indexing and storing
 * {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) fields}.
 * 
 * @see SearchFieldConfiguration#getMode()
 */
public enum SearchFieldMode {

  /**
   * Indicates that the field will be {@link #isSearchable() searchable} and {@link #isRetrievable()
   * retrievable}. This is the default.
   */
  SEARCHABLE_AND_RETRIEVABLE,

  /**
   * Indicates that the field will be {@link #isSearchable() searchable} but NOT {@link #isRetrievable()
   * retrievable}. This is useful for indexing very large texts. Please note that
   * {@link net.sf.mmm.search.engine.api.SearchHit#getHighlightedText() highlighting} only works for
   * {@link #isRetrievable() retrievable} fields.
   */
  SEARCHABLE,

  /**
   * Indicates that the field will NOT be indexed but only stored. Such field is hidden for the
   * {@link net.sf.mmm.search.engine.api.SearchEngine} but can be
   * {@link net.sf.mmm.search.api.SearchEntry#getField(String) retrieved} from the result.
   */
  RETRIEVABLE;

  /**
   * This method determines if the represented
   * {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) field} will be searchable so it can be
   * found via the {@link net.sf.mmm.search.engine.api.SearchEngine}.
   * 
   * @return <code>true</code> if searchable, <code>false</code> otherwise (hidden for searches).
   */
  public boolean isSearchable() {

    return ((this == SEARCHABLE) || (this == SEARCHABLE_AND_RETRIEVABLE));
  }

  /**
   * This method determines if the represented field can be
   * {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) retrieved} from a
   * {@link net.sf.mmm.search.api.SearchEntry}.
   * 
   * @return <code>true</code> if retrievable, <code>false</code> otherwise (can be found but NOT retrieved).
   */
  public boolean isRetrievable() {

    return ((this == RETRIEVABLE) || (this == SEARCHABLE_AND_RETRIEVABLE));
  }

}
