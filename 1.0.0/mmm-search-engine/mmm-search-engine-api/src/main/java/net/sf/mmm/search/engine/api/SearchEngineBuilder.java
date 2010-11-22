/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the component that allows to create a
 * {@link ManagedSearchEngine}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface SearchEngineBuilder {

  /**
   * This method creates a {@link ManagedSearchEngine} for the given
   * <code>configuration</code>.
   * 
   * @see net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader
   * 
   * @param configurationHolder is the {@link SearchEngineConfigurationHolder}.
   * @return the created {@link ManagedSearchEngine}.
   */
  ManagedSearchEngine createSearchEngine(SearchEngineConfigurationHolder configurationHolder);

  // /**
  // * This method creates a {@link ManagedSearchEngine} for the given
  // * <code>configuration</code>.
  // *
  // * @param configuration is the {@link SearchIndexConfiguration}.
  // * @return the created {@link ManagedSearchEngine}.
  // */
  // ManagedSearchEngine createSearchEngine(SearchIndexConfiguration
  // configuration);

}
