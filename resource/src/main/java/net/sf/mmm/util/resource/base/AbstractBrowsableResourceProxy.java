/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.OutputStream;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.ResourceNotWritableException;

/**
 * This is an abstract implementation of the {@link BrowsableResource} interface that {@link #getDelegate() delegates to
 * another} {@link BrowsableResource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResourceProxy extends AbstractDataResourceProxy
    implements BrowsableResource {

  /**
   * The constructor.
   *
   */
  public AbstractBrowsableResourceProxy() {

    super();
  }

  @Override
  protected abstract BrowsableResource getDelegate();

  @Override
  public Iterable<? extends BrowsableResource> getChildResources() {

    return getDelegate().getChildResources();
  }

  @Override
  public Iterable<? extends BrowsableResource> getChildResources(Filter<? super BrowsableResource> filter) {

    return getDelegate().getChildResources(filter);
  }

  @Override
  public boolean isFolder() {

    return getDelegate().isFolder();
  }

  @Override
  public BrowsableResource cd(String path) {

    return getDelegate().cd(path);
  }

  @Override
  public OutputStream openOutputStream() throws ResourceNotWritableException {

    return getDelegate().openOutputStream();
  }

}
