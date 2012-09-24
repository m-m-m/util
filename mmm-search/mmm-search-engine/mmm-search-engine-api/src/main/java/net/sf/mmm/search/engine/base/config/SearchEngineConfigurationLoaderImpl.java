/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationHolder;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationLoader;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the implementation of the {@link SearchEngineConfigurationLoader} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(SearchEngineConfigurationLoader.CDI_NAME)
public class SearchEngineConfigurationLoaderImpl extends XmlBeanMapper<SearchEngineConfigurationBean> implements
    SearchEngineConfigurationLoader {

  /**
   * The constructor.
   */
  public SearchEngineConfigurationLoaderImpl() {

    super(SearchEngineConfigurationBean.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchEngineConfigurationHolder loadConfiguration() {

    return loadConfiguration(SearchConfiguration.DEFAULT_CONFIGURATION_URL);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchEngineConfigurationHolder loadConfiguration(String locationUrl) {

    DataResource resource = getResourceFactory().createDataResource(locationUrl);
    SearchEngineConfigurationBean configuration = loadXml(resource);
    return new SearchEngineConfigurationHolderImpl(configuration, resource, this);
  }

}
