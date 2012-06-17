/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import javax.inject.Singleton;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;
import net.sf.mmm.util.resource.base.AbstractDataResourceFactory;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.FileResource;
import net.sf.mmm.util.resource.base.UrlResource;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.resource.api.DataResourceFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 * @deprecated use {@link BrowsableResourceFactoryImpl} instead.
 */
@Singleton
@Deprecated
public class DataResourceFactoryImpl extends AbstractDataResourceFactory {

  /**
   * The constructor.
   */
  public DataResourceFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DataResource createDataResource(ResourceUri resourceUri) throws ResourceUriUndefinedException {

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
