/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.format.xml.dom;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.access.AbstractConfigurationFactory;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactory} interface
 * using {@link org.w3c.dom XML DOM} as representation for the configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlFactory extends AbstractConfigurationFactory {

  /**
   * this is the default
   * {@link ConfigurationDocument#NAME_INCLUDE_FORMAT format} name for this
   * implementation.
   * 
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "file";

  /**
   * The constructor.
   */
  public XmlFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#configure(String,
   *      Context, Configuration) 
   */
  public void configure(String prefix, Context environment, Configuration include)
      throws ConfigurationException {

  // TODO: parser factory configuration
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#create(net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      net.sf.mmm.context.api.MutableContext) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccess access, MutableContext env)
      throws ConfigurationException {

    return new XmlDocument(access, env);
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#create(net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      net.sf.mmm.configuration.base.AbstractConfiguration) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccess access,
      AbstractConfiguration parentConfiguration) throws ConfigurationException {

    return new XmlDocument(access, parentConfiguration);
  }
}
