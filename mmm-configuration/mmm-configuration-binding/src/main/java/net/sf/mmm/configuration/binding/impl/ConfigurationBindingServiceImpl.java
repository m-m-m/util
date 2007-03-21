/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.impl;

import javax.annotation.Resource;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.binding.api.ConfigurationBindingInjector;
import net.sf.mmm.configuration.binding.base.AbstractConfigurationBindingService;
import net.sf.mmm.nls.impl.ResourceMissingException;

/**
 * This is the regular implementation of the
 * {@link net.sf.mmm.configuration.binding.api.ConfigurationBindingService}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationBindingServiceImpl extends AbstractConfigurationBindingService {

  /** @see #setInjector(ConfigurationBindingInjector) */
  private ConfigurationBindingInjector injector;

  /**
   * The constructor
   */
  public ConfigurationBindingServiceImpl() {

    super();
  }

  /**
   * This method sets the injector to use.
   * 
   * @param configurationInjector
   *        the injector to set
   */
  @Resource()
  public void setInjector(ConfigurationBindingInjector configurationInjector) {

    this.injector = configurationInjector;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() throws ResourceMissingException {

    super.initialize();
    if (this.injector == null) {
      this.injector = new DefaultConfigurationBindingInjector();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void configure(Configuration configuration, Object pojo) throws ConfigurationException {

    configure(configuration, pojo, this.injector);
  }

}
