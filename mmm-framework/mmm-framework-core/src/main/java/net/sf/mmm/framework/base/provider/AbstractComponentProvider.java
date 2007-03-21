/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.base.descriptor.SimpleComponentDescriptor;

/**
 * This is the abstract base implementation of the {@link ComponentProvider}
 * interface.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentProvider<S> implements ComponentProvider<S> {

  /** @see #getDescriptor() */
  private final ComponentDescriptor<S> descriptor;

  /**
   * The constructor.
   * 
   * @param specification
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification} of
   *        the component.
   */
  public AbstractComponentProvider(Class<S> specification) {

    super();
    this.descriptor = new SimpleComponentDescriptor<S>(specification);
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor
   *        is the {@link #getDescriptor() descriptor} of the component.
   */
  public AbstractComponentProvider(ComponentDescriptor<S> componentDescriptor) {

    super();
    this.descriptor = componentDescriptor;
  }

  /**
   * {@inheritDoc}
   */
  public final ComponentDescriptor<S> getDescriptor() {

    return this.descriptor;
  }

}
