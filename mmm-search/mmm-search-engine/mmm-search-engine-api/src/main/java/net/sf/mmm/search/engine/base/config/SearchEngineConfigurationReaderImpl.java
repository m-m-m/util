/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineConfigurationReader;
import net.sf.mmm.util.xml.base.jaxb.XmlBeanMapper;

/**
 * This is the implementation of the {@link SearchEngineConfigurationReader}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchEngineConfigurationReaderImpl extends
    XmlBeanMapper<SearchEngineConfigurationBean> implements SearchEngineConfigurationReader {

  /**
   * The constructor.
   */
  public SearchEngineConfigurationReaderImpl() {

    super(SearchEngineConfigurationBean.class);
  }

  /**
   * {@inheritDoc}
   */
  public SearchEngineConfiguration readConfiguration(String locationUrl) {

    return loadXml(locationUrl);
  }

}
