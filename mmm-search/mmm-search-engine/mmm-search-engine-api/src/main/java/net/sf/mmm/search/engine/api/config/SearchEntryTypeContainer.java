/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

/**
 * This is the interface for the container of the configured {@link SearchEntryType types}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEntryTypeContainer extends Iterable<SearchEntryType> {

  /**
   * This method gets the {@link SearchEntryType} with the given {@link SearchEntryType#getId() ID}.
   * 
   * @param id is the {@link SearchEntryType#getId() ID} of the requested {@link SearchEntryType}.
   * @return the {@link SearchEntryType} for the given <code>id</code>. If no such {@link SearchEntryType}
   *         exists the {@link SearchEntryType} with {@link SearchEntryType#getId() ID}
   *         {@link SearchEntryType#ID_ANY} is returned. This has to be defined in a legal configuration.
   */
  SearchEntryType getEntryType(String id);

  /**
   * This method determines if the internal default {@link #iterator() types} shall be extended, when
   * configuring {@link SearchEntryType}s. In this case existing types are replaced if overridden. This is the
   * default. If you want to reduce the number of {@link #iterator() types}, you need to set this to
   * <code>false</code> and define only the types, you like to have.<br/>
   * <b>NOTE:</b><br>
   * This method is only available in the API to document the configuration. It has no benefit for the
   * end-user of the API.
   * 
   * @return <code>true</code> to extend the defaults, <code>false</code> otherwise.
   */
  boolean isExtendDefaults();

}
