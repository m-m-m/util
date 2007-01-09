/* $Id$ */
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
