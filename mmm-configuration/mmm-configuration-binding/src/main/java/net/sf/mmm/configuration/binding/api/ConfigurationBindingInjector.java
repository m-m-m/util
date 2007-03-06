/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.api;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;

/**
 * This is a call-back interface used to create POJOs from {@link Configuration}
 * and inject them.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationBindingInjector {

  /**
   * This method is called from the
   * {@link ConfigurationBindingService binding-service} to create and inject
   * the given <code>configuration</code> into the given <code>pojo</code>
   * via the given <code>accessor</code>. It may call the
   * {@link ConfigurationBindingService binding-service} recursively to
   * configure complex values.
   * 
   * @param configuration
   *        is the configuration to create.
   * @param accessor
   *        is the property-accessor of the value to create.
   * @param pojo TODO
   * @param bindingService
   *        is the binding service.
   * @throws ConfigurationException
   *         if a problem occurred when reading from the given
   *         <code>configuration</code>.
   */
  void inject(Configuration configuration, PojoPropertyAccessor accessor,
      Object pojo, ConfigurationBindingService bindingService) throws ConfigurationException;

}
