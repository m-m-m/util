/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.base.SimpleComponentInstanceContainer;

/**
 * This is a very simple implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProvider} interface that provides
 * a singleton instance.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptor#getSpecification() specification}
 *        of the provided component.
 * 
 * @see #dispose(ComponentInstanceContainer, ComponentManager)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleSingletonProvider<S> extends AbstractStaticSingletonComponentProvider<S> {

  /**
   * The constructor.
   * 
   * @param specification
   *        is the
   *        {@link ComponentDescriptor#getSpecification() specification}.
   * @param instance
   *        is the singleton instance.
   */
  public SimpleSingletonProvider(Class<S> specification, S instance) {

    super(specification);
    setInstanceContainer(new SimpleComponentInstanceContainer<S>(instance));
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor
   *        is the {@link ComponentDescriptor descriptor} of the provided
   *        component.
   * @param instance
   *        is the singleton instance.
   */
  public SimpleSingletonProvider(ComponentDescriptor<S> componentDescriptor, S instance) {

    this(componentDescriptor, new SimpleComponentInstanceContainer<S>(instance));
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor
   *        is the {@link ComponentDescriptor descriptor} of the provided
   *        component.
   * @param componentInstanceContainer
   *        is the container with the singleton instance.
   */
  public SimpleSingletonProvider(ComponentDescriptor<S> componentDescriptor,
      ComponentInstanceContainer<S> componentInstanceContainer) {

    super(componentDescriptor);
    setInstanceContainer(componentInstanceContainer);
  }

}
