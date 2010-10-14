/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api.spi;

import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is the interface for a provider of {@link DataResource}.
 * 
 * @param <R> is the generic {@link #getResourceType() type of the managed
 *        resources}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface DataResourceProvider<R extends DataResource> {

  /**
   * This method gets the scheme-prefix of the {@link DataResource resources}
   * managed by this provider.
   * 
   * @see ResourceUri#getSchemePrefix()
   * 
   * @return the scheme-prefix.
   */
  String[] getSchemePrefixes();

  /**
   * This method gets the type of the resource managed by this provider. This is
   * e.g. used to determine if the managed resources implement
   * {@link net.sf.mmm.util.resource.api.BrowsableResource}.
   * 
   * @return the class reflecting the type of the resources.
   */
  Class<R> getResourceType();

  /**
   * This method creates a new {@link DataResource resource} for the given
   * <code>resourceUri</code>.
   * 
   * @param resourceUri is the {@link ResourceUri}.
   * @return the {@link DataResource resource} for the given
   *         <code>resourceUri</code>.
   */
  R createResource(ResourceUri resourceUri);

}
