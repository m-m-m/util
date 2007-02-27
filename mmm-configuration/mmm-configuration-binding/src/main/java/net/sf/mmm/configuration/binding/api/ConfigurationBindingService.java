/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.api;

import javax.naming.ConfigurationException;

import net.sf.mmm.configuration.api.Configuration;

/**
 * This is the interface for a service that allows to
 * {@link #configure(Configuration, Object) configure} a POJO (plain old java
 * object) automatically via a given {@link Configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationBindingService {

  /**
   * This method configures the given <code>pojo</code> using the
   * <code>configuration</code>.
   * 
   * @param configuration
   *        is the configuration to inject.
   * @param pojo
   *        is the POJO to configure.
   * @throws ConfigurationException
   *         if a required property is missing.
   */
  void configure(Configuration configuration, Object pojo) throws ConfigurationException;

  /**
   * This method configures the given <code>pojo</code> using the
   * <code>configuration</code> and the <code>builder</code>.
   * 
   * @param configuration
   *        is the configuration to inject.
   * @param pojo
   *        is the POJO to configure.
   * @param builder
   *        is a custom builder used to create complex property arguments
   *        required to configure the given <code>pojo</code>.
   * @throws ConfigurationException
   *         if a required property is missing.
   */
  void configure(Configuration configuration, Object pojo, PojoBuilder builder)
      throws ConfigurationException;

}
