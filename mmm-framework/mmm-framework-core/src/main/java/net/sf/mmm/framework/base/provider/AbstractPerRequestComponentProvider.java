/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.base.SimpleComponentInstanceContainer;

/**
 * This is an abstract implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProvider} interface for custom
 * providers.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPerRequestComponentProvider<S> extends AbstractComponentProvider<S> {

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(Class)
   */
  public AbstractPerRequestComponentProvider(Class<S> specification) {

    super(specification);
  }

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptor)
   */
  public AbstractPerRequestComponentProvider(ComponentDescriptor<S> componentDescriptor) {

    super(componentDescriptor);
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentProvider#request(java.lang.String,
   *      net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String,
   *      net.sf.mmm.framework.api.ComponentManager)
   */
  public ComponentInstanceContainer<S> request(String instanceId,
      ComponentDescriptor<?> sourceDescriptor, String sourceInstanceId,
      ComponentManager componentManager) throws ComponentException {

    S instance;
    if (ComponentManager.DEFAULT_INSTANCE_ID.equals(instanceId)) {
      instance = requestDefault(sourceDescriptor, sourceInstanceId, componentManager);
    } else {
      instance = requestById(instanceId, sourceDescriptor, sourceInstanceId, componentManager);
    }
    return new SimpleComponentInstanceContainer<S>(instance);
  }

  /**
   * This method requests the
   * {@link ComponentManager#DEFAULT_INSTANCE_ID default} instance of the
   * component.
   * 
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return a new instance of the component.
   * @throws ComponentException
   */
  protected abstract S requestDefault(ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, ComponentManager componentManager) throws ComponentException;

  /**
   * @see net.sf.mmm.framework.api.ComponentProvider#request(java.lang.String,
   *      net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String,
   *      net.sf.mmm.framework.api.ComponentManager)
   * 
   * @param instanceId
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return the requested descriptor.
   * @throws ComponentException
   */
  protected S requestById(String instanceId, ComponentDescriptor<?> sourceDescriptor,
      String sourceInstanceId, ComponentManager componentManager) throws ComponentException {

    throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentProvider#release(net.sf.mmm.framework.api.ComponentInstanceContainer,
   *      net.sf.mmm.framework.api.ComponentManager)
   */
  public boolean release(ComponentInstanceContainer<S> instanceContainer,
      ComponentManager componentManager) {

    dispose(instanceContainer, componentManager);
    return true;
  }

  /**
   * ATTENTION: this implementation does nothing. Please override if your
   * component has to be shut-down.
   * 
   * @see net.sf.mmm.framework.api.ComponentProvider#dispose(net.sf.mmm.framework.api.ComponentInstanceContainer,
   *      net.sf.mmm.framework.api.ComponentManager)
   */
  public void dispose(ComponentInstanceContainer<S> instanceContainer,
      ComponentManager componentManager) {

  }

}
