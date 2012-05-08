/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.OutputStream;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ResourceNotWritableException;

/**
 * This is an abstract implementation of the {@link BrowsableResource} interface that {@link #getDelegate()
 * delegates to another} {@link BrowsableResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResourceProxy extends AbstractDataResourceProxy implements BrowsableResource {

  /**
   * The constructor.
   * 
   */
  public AbstractBrowsableResourceProxy() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract BrowsableResource getDelegate();

  /**
   * {@inheritDoc}
   */
  public Iterable<BrowsableResource> getChildResources() {

    return getDelegate().getChildResources();
  }

  /**
   * {@inheritDoc}
   */
  public Iterable<BrowsableResource> getChildResources(Filter<BrowsableResource> filter) {

    return getDelegate().getChildResources(filter);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isFolder() {

    return getDelegate().isFolder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutputStream openOutputStream() throws ResourceNotWritableException {

    return getDelegate().openOutputStream();
  }

}
