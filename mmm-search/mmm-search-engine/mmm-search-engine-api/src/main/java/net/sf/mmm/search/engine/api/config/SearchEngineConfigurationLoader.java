/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

import net.sf.mmm.search.api.config.SearchConfigurationLoader;

/**
 * This is the {@link SearchConfigurationLoader} for the {@link SearchEngineConfiguration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineConfigurationLoader extends SearchConfigurationLoader {

  /** The {@link javax.inject.Named name} of this component. */
  String CDI_NAME = "net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader";

  /**
   * {@inheritDoc}
   */
  @Override
  SearchEngineConfigurationHolder loadConfiguration();

  /**
   * {@inheritDoc}
   */
  @Override
  SearchEngineConfigurationHolder loadConfiguration(String locationUrl);

}
