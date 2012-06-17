/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

/**
 * is the interface for the configuration of the actual search-index as database.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchIndexConfiguration {

  /**
   * This method gets the location of the search-index. This is typically a path to a directory in the
   * filesystem containing the index as database.
   * 
   * @return the location where the search-index is or should be stored.
   */
  String getLocation();

}
