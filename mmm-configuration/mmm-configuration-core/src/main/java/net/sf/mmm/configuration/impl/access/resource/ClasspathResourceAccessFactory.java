/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.access.resource;

import java.net.URL;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.impl.access.url.UrlAccess;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactory} for
 * {@link ClassLoader#getResource(java.lang.String) classpath-resource} access.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClasspathResourceAccessFactory implements ConfigurationAccessFactory {

  /** the file accessors */
  private UrlAccess[] accessors;

  /**
   * The constructor.
   */
  public ClasspathResourceAccessFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration)
   */
  public void configure(String prefix, Context environment, Configuration include)
      throws ConfigurationException, ValueException {

    String resourceName = include.getDescendant(ConfigurationDocument.NAME_INCLUDE_HREF)
        .getValue().getString();
    URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
    if (url == null) {
      throw new ConfigurationReadException(resourceName);
    }
    UrlAccess access = new UrlAccess(url);
    this.accessors = new UrlAccess[] {access};
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#getAccessors()
   */
  public ConfigurationAccess[] getAccessors() {

    return this.accessors;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#isSingleAccess()
   */
  public boolean isSingleAccess() {

    return true;
  }

}
