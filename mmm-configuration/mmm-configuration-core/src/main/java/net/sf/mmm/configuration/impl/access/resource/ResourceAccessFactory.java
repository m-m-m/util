/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.impl.access.resource;

import java.io.File;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactory;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory;
import net.sf.mmm.context.api.Context;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactory}
 * interface for {@link java.net.URL URL} access.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ResourceAccessFactory extends AbstractConfigurationAccessFactory {

  /**
   * this is the default
   * {@link ConfigurationDocument#NAME_INCLUDE_ACCESS access} name for this
   * implementation.
   * 
   * @see ConfigurationAccessFactory#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "resource";

  /**
   * This is the suffix for a context variable containg the root path where
   * resources are looked up in the filesystem.
   */
  public static final String CONTEXT_VARIABLE_SUFFIX_ROOT_PATH = ".root";

  /**
   * The constructor.
   */
  public ResourceAccessFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration,
   *      net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfigurationAccess[] configure(String prefix, Context context,
      Configuration include, ConfigurationAccess parent, String href)
      throws ConfigurationException {

    String fileRootPath = context.getValue(prefix + CONTEXT_VARIABLE_SUFFIX_ROOT_PATH).getString(
        null);
    if (fileRootPath == null) {
      fileRootPath = new File("").getAbsolutePath();
    }
    String absoluteHref = href;
    if (!href.startsWith("/")) {
      if (parent == null) {
        // TODO: NLS
        throw new ConfigurationException(
            "The parent access is required but NOT available - please set the contextPrefix properly!");
      } else {
        ResourceAccess parentAccess = (ResourceAccess) parent;
        String path = new File(parentAccess.getPath()).getParent();
        if (path == null) {
          path = "/";
        }
        if (!path.endsWith("/")) {
          absoluteHref = path + "/" + href;
        } else {
          absoluteHref = path + href;
        }
      }
    }
    return new ResourceAccess[] {new ResourceAccess(fileRootPath, absoluteHref)};
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#isSingleAccess()
   */
  public boolean isSingleAccess() {

    return true;
  }

}
