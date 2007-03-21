/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.descriptor;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.framework.api.ComponentDescriptor} interface.
 * 
 * @param <S>
 *        is the {@link #getSpecification() specification} of the provided
 *        component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleComponentDescriptor<S> extends AbstractComponentDescriptor<S> {

  /** @see #getSpecification() */
  private final Class<S> specification;

  /**
   * The constructor.
   * 
   * @param componentSpecification
   *        is the {@link #getSpecification() specification} of the component.
   */
  public SimpleComponentDescriptor(Class<S> componentSpecification) {

    super();
    this.specification = componentSpecification;
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  public Class<S> getSpecification() {

    return this.specification;
  }

}
