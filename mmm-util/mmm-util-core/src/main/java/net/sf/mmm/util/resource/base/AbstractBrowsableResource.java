/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import net.sf.mmm.util.collection.base.FilteredIterable;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.resource.api.BrowsableResource;

/**
 * This is the abstract base-implementation of {@link BrowsableResource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractBrowsableResource extends AbstractDataResource implements
    BrowsableResource {

  /**
   * The constructor.
   */
  public AbstractBrowsableResource() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Iterable<BrowsableResource> listChildResources(Filter<BrowsableResource> filter) {

    return new FilteredIterable<BrowsableResource>(listChildResources(), filter);
  }

}
