/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the abstract base implementation of the
 * {@link BrowsableResourceFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResourceFactory extends AbstractDataResourceFactory
    implements BrowsableResourceFactory {

  /**
   * The constructor.
   */
  public AbstractBrowsableResourceFactory() {

    super();
  }

  /**
   * This method {@link #createBrowsableResource(String) creates} the actual raw
   * {@link BrowsableResource}.
   * 
   * @param resourceUri is the parsed and qualified {@link ResourceUri}.
   * @return the created {@link BrowsableResource}.
   * @throws ResourceUriUndefinedException if the given <code>resourceUri</code>
   *         is undefined, e.g. the {@link ResourceUri#getSchemePrefix()
   *         scheme-prefix} is NOT supported by this factory.
   */
  protected abstract BrowsableResource createBrowsableResource(ResourceUri resourceUri)
      throws ResourceUriUndefinedException;

  /**
   * {@inheritDoc}
   */
  public BrowsableResource createBrowsableResource(String resourceUri)
      throws ResourceUriUndefinedException {

    ResourceUri uri = new ResourceUri(resourceUri);
    if (uri.getSchemePrefix() == null) {
      throw new ResourceUriUndefinedException(resourceUri);
    }
    BrowsableResource resource = createBrowsableResource(uri);
    return new BrowsableResourceAdapter(resource);
  }

  /**
   * This is an implementation of the {@link BrowsableResource} interface, that
   * adapts {@link #getDelegate() another} {@link BrowsableResource} and
   * enhances the {@link #navigate(String)}-method to support switching schemes
   * via the {@link BrowsableResourceFactory} that created this
   * {@link BrowsableResource}.
   * 
   * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
   */
  protected class BrowsableResourceAdapter extends AbstractBrowsableResourceProxy {

    /** @see #getDelegate() */
    private final BrowsableResource delegate;

    /**
     * The constructor.
     * 
     * @param delegate is the {@link BrowsableResource} to adapt.
     */
    public BrowsableResourceAdapter(BrowsableResource delegate) {

      super();
      this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BrowsableResource getDelegate() {

      return this.delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrowsableResource navigate(String relativePath) throws ResourceUriUndefinedException {

      ResourceUri resourceUri = new ResourceUri(relativePath);
      BrowsableResource result;
      if (resourceUri.getSchemePrefix() == null) {
        result = super.navigate(relativePath);
      } else {
        result = createBrowsableResource(resourceUri);
      }
      return new BrowsableResourceAdapter(result);
    }

  }

}
