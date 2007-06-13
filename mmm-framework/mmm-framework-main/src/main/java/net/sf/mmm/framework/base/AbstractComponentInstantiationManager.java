/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.nls.impl.NlsIllegalArgumentException;

/**
 * This is the abstract base implementation of the
 * {@link ComponentInstantiationManager} interface.
 * 
 * @param <S> is the
 *        {@link ComponentDescriptor#getSpecification() specification} of the
 *        component.
 * @param <I> is the
 *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponentInstantiationManager<S, I extends S> implements
    ComponentInstantiationManager<S, I> {

  /** @see #getDescriptor() */
  private ExtendedComponentDescriptor<S, I> descriptor;

  /**
   * The constructor.
   */
  public AbstractComponentInstantiationManager() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param componentDescriptor is the {@link #getDescriptor() descriptor}.
   */
  public AbstractComponentInstantiationManager(ExtendedComponentDescriptor<S, I> componentDescriptor) {

    super();
    this.descriptor = componentDescriptor;
  }

  /**
   * This method gets the descriptor.
   * 
   * @return the descriptor.
   */
  public ExtendedComponentDescriptor<S, I> getDescriptor() {

    return this.descriptor;
  }

  /**
   * This method sets the descriptor.
   * 
   * @param newDescriptor is the descriptor to set.
   */
  public void setDescriptor(ExtendedComponentDescriptor<S, I> newDescriptor) {

    if (this.descriptor != null) {
      throw new NlsIllegalArgumentException("Descriptor already set!");
    }
    this.descriptor = newDescriptor;
  }

  /**
   * This method creates a new instance container. It should be called from
   * {@link ComponentInstantiationManager#request(String)} if a new instance
   * should be created.
   * 
   * @param instanceId is the
   *        {@link ComponentManager#requestComponent(Class, String) instance-ID}
   *        of the requested component.
   * @return the new instance container for the requested component.
   */
  protected ExtendedComponentInstanceContainerImpl<S, I> createInstanceContainer(String instanceId) {

    ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = new ExtendedComponentInstanceContainerImpl<S, I>(
        this.descriptor);
    instanceContainer.setInstanceId(instanceId);
    return instanceContainer;
  }

}
