/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spi;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.resource.api.spi.ResourceUri;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.spi.AbstractDataResourceProvider;

/**
 * This is the implementation of
 * {@link net.sf.mmm.util.resource.api.spi.DataResourceProvider} for
 * {@link ClasspathResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Singleton
@Named
public class ClasspathResourceProvider extends AbstractDataResourceProvider<ClasspathResource> {

  /**
   * The constructor.
   */
  public ClasspathResourceProvider() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String[] getSchemePrefixes() {

    return new String[] { ClasspathResource.SCHEME_PREFIX };
  }

  /**
   * {@inheritDoc}
   */
  public Class<ClasspathResource> getResourceType() {

    return ClasspathResource.class;
  }

  /**
   * {@inheritDoc}
   */
  public ClasspathResource createResource(ResourceUri resourceUri) {

    return new ClasspathResource(resourceUri.getPath());
  }

}
