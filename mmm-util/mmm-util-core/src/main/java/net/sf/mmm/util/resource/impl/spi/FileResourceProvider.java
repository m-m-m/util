/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spi;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.resource.api.spi.ResourceUri;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.base.spi.AbstractDataResourceProvider;

/**
 * This is the implementation of
 * {@link net.sf.mmm.util.resource.api.spi.DataResourceProvider} for
 * {@link FileResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class FileResourceProvider extends AbstractDataResourceProvider<FileResource> {

  /**
   * The constructor.
   */
  public FileResourceProvider() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String[] getSchemePrefixes() {

    return new String[] { FileResource.SCHEME_PREFIX };
  }

  /**
   * {@inheritDoc}
   */
  public Class<FileResource> getResourceType() {

    return FileResource.class;
  }

  /**
   * {@inheritDoc}
   */
  public FileResource createResource(ResourceUri resourceUri) {

    return new FileResource(resourceUri.getPath());
  }
}
