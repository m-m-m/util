/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;

/**
 * This is the abstract base-implementation of {@link BrowsableResource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResource extends AbstractDataResource implements BrowsableResource {

  /**
   * The constructor.
   */
  public AbstractBrowsableResource() {

    super();
  }

  @Override
  public Iterable<? extends BrowsableResource> getChildResources(Filter<? super BrowsableResource> filter) {

    return getResources(getChildResources(), filter);
  }

  protected static Iterable<? extends BrowsableResource> getResources(Iterable<? extends BrowsableResource> iterable,
      Filter<? super BrowsableResource> filter) {

    return new FilteredIterable<>(iterable, filter);
  }

}
