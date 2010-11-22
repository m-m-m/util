/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base.spi;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.spi.DataResourceProvider;

/**
 * This is the abstract base-implementation of the {@link DataResourceProvider}
 * interface. Please extend this class if possible to improve compatibility so
 * if a new method may be added to {@link DataResourceProvider}, the default
 * implementation can be added here so third-party code will NOT break.
 * 
 * @param <R> is the generic {@link #getResourceType() type of the managed
 *        resources}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractDataResourceProvider<R extends DataResource> extends AbstractLoggableComponent
    implements DataResourceProvider<R> {

  /**
   * The constructor.
   */
  public AbstractDataResourceProvider() {

    super();
  }

}
