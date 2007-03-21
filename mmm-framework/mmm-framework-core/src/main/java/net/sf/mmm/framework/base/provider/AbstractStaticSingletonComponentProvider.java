/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;

/**
 * This is the abstract implementation of a {@link ComponentProvider} for
 * singleton components.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractStaticSingletonComponentProvider<S> extends
    AbstractDefaultInstanceComponentProvider<S> {

  /** the singleton instance container */
  private ComponentInstanceContainer<S> singletonInstanceContainer;

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(Class)
   */
  public AbstractStaticSingletonComponentProvider(Class<S> specification) {

    super(specification);
  }

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptor)
   */
  public AbstractStaticSingletonComponentProvider(ComponentDescriptor<S> componentDescriptor) {

    super(componentDescriptor);
  }

  /**
   * This method gets the container with the singleton
   * {@link ComponentInstanceContainer#getInstance() instance}.
   * 
   * @see #request(String, ComponentDescriptor, String, ComponentManager)
   * 
   * @return the instance-container or <code>null</code> if it has been
   *         {@link #dispose(ComponentInstanceContainer, ComponentManager) disposed}.
   */
  public final ComponentInstanceContainer<S> getInstanceContainer() {

    return this.singletonInstanceContainer;
  }

  /**
   * 
   * @param instanceContainer
   */
  public final void setInstanceContainer(ComponentInstanceContainer<S> instanceContainer) {

    this.singletonInstanceContainer = instanceContainer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComponentInstanceContainer<S> requestDefault(ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, ComponentManager componentManager) throws ComponentException {

    if (this.singletonInstanceContainer == null) {
      synchronized (this) {
        if (this.singletonInstanceContainer == null) {
          this.singletonInstanceContainer = requestSingleton(sourceDescriptor, sourceInstanceId,
              componentManager);
        }
      }
    }
    return this.singletonInstanceContainer;
  }

  /**
   * This method is called if the instance is requested but is NOT available.
   * 
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return the requested instance-container.
   */
  protected ComponentInstanceContainer<S> requestSingleton(ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, ComponentManager componentManager) {

    throw new InstanceIdNotAvailableException(ComponentManager.DEFAULT_INSTANCE_ID, getDescriptor());
  }

  /**
   * {@inheritDoc}
   */
  public final boolean release(ComponentInstanceContainer<S> instanceContainer,
      ComponentManager componentManager) {

    // nothing to do...
    return false;
  }

  /**
   * {@inheritDoc}
   * ATTENTION: this implementation does nothing but setting the singleton
   * {@link #getInstanceContainer() instance-container} to <code>null</code>.
   * Please override if your component has to be shut-down.
   */
  public void dispose(ComponentInstanceContainer<S> instanceContainer,
      ComponentManager componentManager) {

    if (this.singletonInstanceContainer == instanceContainer) {
      this.singletonInstanceContainer = null;
    }
  }

}
