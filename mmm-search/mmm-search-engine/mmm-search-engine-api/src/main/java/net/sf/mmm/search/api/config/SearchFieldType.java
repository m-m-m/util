/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import java.util.Date;

/**
 * This enum contains the available types of a
 * {@link net.sf.mmm.search.api.SearchEntry#getFieldAsString(String) field} for indexing and searching.
 * 
 * @see SearchFieldConfiguration#getType()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum SearchFieldType {

  /**
   * The type of a {@link String} field with arbitrary text. The content of the field will be normalized for
   * searches in the search-index as well as in {@link net.sf.mmm.search.engine.api.SearchQuery queries}. The
   * normalization may contain things like:
   * <ul>
   * <li>normalizing the {@link String#toLowerCase() case} of the text</li>
   * <li>removal of common words (like "a" and "the")</li>
   * <li>stemming (reducing words with their root form, e.g. "stemmer", "stemming", "stemmed" are all reduced
   * to "stem" and therefore match each other)</li>
   * </ul>
   */
  TEXT(String.class),

  /**
   * The type of a {@link String} field containing an identifier. Unlike {@link #TEXT} it will NOT be
   * normalized and can be searched as it is.
   */
  STRING(String.class),

  /**
   * The type of a field containing an {@link Integer} value. For values like identifiers, file-size, etc. it
   * is better to use {@link #LONG} however this will consume more space in your search-index. So if you want
   * to save space, it might be an option to use {@link #INTEGER} (at least for values where you know that the
   * range/precision is sufficient).
   */
  INTEGER(Integer.class),

  /**
   * The type of a field containing a {@link Long} value.
   */
  LONG(Long.class),

  /**
   * The type of a field containing a {@link Float} value. For advanced precision it is better to use
   * {@link #DOUBLE} instead. However this will consume more space in your search-index.
   */
  FLOAT(Float.class),

  /**
   * The type of a field containing a {@link Double} value.
   */
  DOUBLE(Double.class),

  /**
   * The type of a field containing a {@link Date} value.
   */
  DATE(Date.class);

  /** @see #getFieldClass() */
  private final Class<?> fieldClass;

  /**
   * The constructor.
   * 
   * @param type is the {@link #getFieldClass() type}.
   */
  SearchFieldType(Class<?> type) {

    this.fieldClass = type;
  }

  /**
   * This method gets the {@link Class} reflecting the values that can be stored in a field of this type.
   * 
   * @return the value type.
   */
  public Class<?> getFieldClass() {

    return this.fieldClass;
  }

  /**
   * This method determines if fields of this type are normalized.
   * 
   * @see #TEXT
   * 
   * @return <code>true</code> if normalized, <code>false</code> otherwise (if the field is a keyword).
   */
  public boolean isNormalized() {

    return (this == TEXT);
  }

}
