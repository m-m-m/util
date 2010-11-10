/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

import java.util.List;

import net.sf.mmm.search.api.config.SearchConfiguration;

/**
 * This is the interface for the configuration of the
 * {@link net.sf.mmm.search.engine.api.SearchEngine}.<br>
 * You will typically provide your configuration as XML. The base-implementation
 * comes with an according (un)marshaler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineConfiguration extends SearchConfiguration {

  /**
   * This method gets the {@link SearchEntryType} with the given
   * {@link SearchEntryType#getId() ID}.
   * 
   * @param id is the {@link SearchEntryType#getId() ID} of the requested
   *        {@link SearchEntryType}.
   * @return the {@link SearchEntryType} for the given <code>id</code>. If no
   *         such {@link SearchEntryType} exists the {@link SearchEntryType}
   *         with {@link SearchEntryType#getId() ID}
   *         {@link SearchEntryType#ID_DEFAULT} is returned. This has to be
   *         defined in a legal configuration.
   */
  SearchEntryType getEntryType(String id);

  /**
   * This method gets the {@link List} of all {@link SearchEntryType}s.
   * 
   * @return all configured {@link SearchEntryType}s.
   */
  List<? extends SearchEntryType> getEntryTypes();

  /**
   * This method gets the options for the
   * {@link net.sf.mmm.search.engine.api.SearchEngine}.
   * 
   * @return the {@link SearchEngineProperties}.
   */
  SearchEngineProperties getSearchProperties();

}
