/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.api;

import javax.naming.ConfigurationException;

import net.sf.mmm.configuration.api.Configuration;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationBindingService {

  public void inject(Configuration config, Object pojo) throws ConfigurationException;

}
