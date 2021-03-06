/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl.spi;

import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.base.UrlResource;
import net.sf.mmm.util.resource.base.spi.AbstractDataResourceProvider;

/**
 * This is the implementation of {@link net.sf.mmm.util.resource.api.spi.DataResourceProvider} for
 * {@link UrlResource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class UrlResourceProvider extends AbstractDataResourceProvider<UrlResource> {

  /**
   * The constructor.
   */
  public UrlResourceProvider() {

    super();
  }

  @Override
  public String[] getSchemePrefixes() {

    return new String[] { UrlResource.SCHEME_PREFIX_HTTP, UrlResource.SCHEME_PREFIX_HTTPS,
        UrlResource.SCHEME_PREFIX_FTP };
  }

  @Override
  public Class<UrlResource> getResourceType() {

    return UrlResource.class;
  }

  @Override
  public UrlResource createResource(ResourceUri resourceUri) {

    return new UrlResource(resourceUri.getUri());
  }
}
