/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.base.AbstractBrowsableResourceFactory;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.base.ResourceUri;
import net.sf.mmm.util.resource.base.UrlResource;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.resource.api.BrowsableResourceFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class BrowsableResourceFactoryImpl extends AbstractBrowsableResourceFactory {

  /**
   * The constructor.
   */
  public BrowsableResourceFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected BrowsableResource createBrowsableResource(ResourceUri resourceUri)
      throws ResourceUriUndefinedException {

    String schemePrefix = resourceUri.getSchemePrefix();
    if (FileResource.SCHEME_PREFIX.equals(schemePrefix)) {
      return new FileResource(resourceUri.getPath());
    } else {
      throw new ResourceUriUndefinedException(resourceUri.getUri());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DataResource createDataResource(ResourceUri resourceUri)
      throws ResourceUriUndefinedException {

    String schemePrefix = resourceUri.getSchemePrefix();
    if (ClasspathResource.SCHEME_PREFIX.equals(schemePrefix)) {
      return new ClasspathResource(resourceUri.getPath());
    } else if (FileResource.SCHEME_PREFIX.equals(schemePrefix)) {
      return new FileResource(resourceUri.getPath());
    } else {
      return new UrlResource(resourceUri.getUri());
    }
  }

}
