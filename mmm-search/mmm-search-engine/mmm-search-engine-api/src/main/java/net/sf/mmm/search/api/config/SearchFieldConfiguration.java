/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

/**
 * This is the interface for the configuration of a {@link net.sf.mmm.search.api.SearchEntry#getField(String)
 * field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchFieldConfiguration {

  /**
   * This method gets the name of the {@link net.sf.mmm.search.api.SearchEntry#getField(String) field}. It is
   * used as key to identify the field.
   * 
   * @return the field-name.
   */
  String getName();

  /**
   * This method gets the {@link SearchFieldType type} of the according
   * {@link net.sf.mmm.search.api.SearchEntry#getField(String) field}.
   * 
   * @see net.sf.mmm.search.api.SearchEntry#getField(String, Class)
   * 
   * @return the field-type.
   */
  SearchFieldType getType();

  /**
   * This method gets the {@link SearchFieldMode mode} of the
   * {@link net.sf.mmm.search.api.SearchEntry#getField(String) field}.
   * 
   * @return the {@link SearchFieldMode}.
   */
  SearchFieldMode getMode();

  /**
   * This method determines if the represented {@link net.sf.mmm.search.api.SearchEntry#getField(String)
   * field} shall be hidden in the view. This means it is NOT displayed in the details of a
   * {@link net.sf.mmm.search.engine.api.SearchHit}. The default is false.
   * 
   * @return <code>true</code> if hidden, <code>false</code> otherwise.
   */
  boolean isHidden();

}
