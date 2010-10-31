/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the abstract base implementation of the {@link DataResourceFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractDataResourceFactory extends AbstractLoggableComponent implements
    DataResourceFactory {

  /**
   * The constructor.
   */
  public AbstractDataResourceFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public DataResource createDataResource(String resourceUri) throws ResourceUriUndefinedException {

    ResourceUri uri = new ResourceUriImpl(resourceUri);
    if (uri.getSchemePrefix() == null) {
      throw new ResourceUriUndefinedException(resourceUri);
    }
    DataResource resource = createDataResource(uri);
    return new DataResourceAdapter(resource);
  }

  /**
   * This method {@link #createDataResource(String) creates} the actual raw
   * {@link DataResource}.
   * 
   * @param resourceUri is the parsed and qualified {@link ResourceUriImpl}.
   * @return the created {@link DataResource}.
   * @throws ResourceUriUndefinedException if the given <code>resourceUri</code>
   *         is undefined, e.g. the {@link ResourceUriImpl#getSchemePrefix()
   *         scheme-prefix} is NOT supported by this factory.
   */
  protected abstract DataResource createDataResource(ResourceUri resourceUri)
      throws ResourceUriUndefinedException;

  /**
   * This is an implementation of the {@link DataResource} interface, that
   * adapts {@link #getDelegate() another} {@link DataResource} and enhances the
   * {@link #navigate(String)}-method to support switching schemes via the
   * {@link DataResourceFactory} that created this {@link DataResource}.
   * 
   * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
   */
  protected class DataResourceAdapter extends AbstractDataResourceProxy {

    /** @see #getDelegate() */
    private final DataResource delegate;

    /**
     * The constructor.
     * 
     * @param delegate is the {@link DataResource} to adapt.
     */
    public DataResourceAdapter(DataResource delegate) {

      super();
      this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataResource getDelegate() {

      return this.delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataResource navigate(String relativePath) throws ResourceUriUndefinedException {

      ResourceUri resourceUri = new ResourceUriImpl(relativePath);
      DataResource result;
      if (resourceUri.getSchemePrefix() == null) {
        result = super.navigate(relativePath);
      } else {
        result = createDataResource(resourceUri);
      }
      return new DataResourceAdapter(result);
    }

  }

}
