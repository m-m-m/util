/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolderImpl;

/**
 * This is the implementation of {@link SearchEngineConfigurationHolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchEngineConfigurationHolderImpl extends
    JaxbBeanHolderImpl<SearchEngineConfiguration, SearchEngineConfigurationBean> implements
    SearchEngineConfigurationHolder {

  /**
   * The dummy constructor.
   * 
   * @param configuration is the {@link SearchEngineConfigurationBean} for {@link #getBean()}.
   */
  public SearchEngineConfigurationHolderImpl(SearchEngineConfigurationBean configuration) {

    this(configuration, null, null);
  }

  /**
   * The constructor.
   * 
   * @param configuration is the {@link SearchEngineConfigurationBean} for {@link #getBean()}.
   * @param resource is the {@link DataResource}.
   * @param loader is the {@link SearchEngineConfigurationLoaderImpl} for {@link #refresh()}.
   */
  public SearchEngineConfigurationHolderImpl(SearchEngineConfigurationBean configuration, DataResource resource,
      SearchEngineConfigurationLoaderImpl loader) {

    super(configuration, resource, loader, false);
  }

}
