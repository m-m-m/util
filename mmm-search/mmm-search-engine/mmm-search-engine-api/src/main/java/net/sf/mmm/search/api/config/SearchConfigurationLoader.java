/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api.config;

import net.sf.mmm.util.xml.base.jaxb.JaxbBeanHolder;

/**
 * This is the abstract interface for {@link #loadConfiguration(String) reading}
 * the {@link SearchConfiguration} for a given data location. The implementation
 * shall at least support reading the configuration from the filesystem as XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface SearchConfigurationLoader {

  /**
   * This method reads the configuration from the given <code>location</code>.
   * The configuration data is parsed according to its format (typically XML)
   * and returned as {@link JaxbBeanHolder}.
   * 
   * @param locationUrl is the location where the {@link SearchConfiguration}
   *        data shall be read from. This is typically a path into the
   *        filesystem (e.g. "file://~/.mmm/search.xml"). Please note that
   *        "file://" can be omitted.
   * @return the {@link JaxbBeanHolder}.
   */
  JaxbBeanHolder<? extends SearchConfiguration> loadConfiguration(String locationUrl);

}
