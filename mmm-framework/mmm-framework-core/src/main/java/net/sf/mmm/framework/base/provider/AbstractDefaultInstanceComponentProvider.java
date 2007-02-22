/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;

/**
 * This is an abstract implementation of the
 * {@link net.sf.mmm.framework.api.ComponentProvider} interface that has a
 * {@link #requestDefault(ComponentDescriptor, String, ComponentManager) different treatment}
 * for the {@link ComponentManager#DEFAULT_INSTANCE_ID default instance-id}
 * than the
 * {@link #requestById(String, ComponentDescriptor, String, ComponentManager) handling}
 * for other instance-ids.<br>
 * By default other instance-ids are NOT accepted and cause an
 * {@link InstanceIdNotAvailableException}.
 * 
 * @param <S>
 *        is the
 *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}
 *        of the provided component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDefaultInstanceComponentProvider<S> extends
    AbstractComponentProvider<S> {

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(Class)
   */
  public AbstractDefaultInstanceComponentProvider(Class<S> specification) {

    super(specification);
  }

  /**
   * @see AbstractComponentProvider#AbstractComponentProvider(ComponentDescriptor)
   */
  public AbstractDefaultInstanceComponentProvider(ComponentDescriptor<S> componentDescriptor) {

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

    if (ComponentManager.DEFAULT_INSTANCE_ID.equals(instanceId)) {
      return requestDefault(sourceDescriptor, sourceInstanceId, componentManager);
    } else {
      return requestById(instanceId, sourceDescriptor, sourceInstanceId, componentManager);
    }
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentProvider#request(java.lang.String,
   *      net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String,
   *      net.sf.mmm.framework.api.ComponentManager)
   * 
   * @param sourceDescriptor
   * @param sourceInstanceId
   * @param componentManager
   * @return the requested descriptor.
   * @throws ComponentException
   */
  protected abstract ComponentInstanceContainer<S> requestDefault(
      ComponentDescriptor<?> sourceDescriptor, String sourceInstanceId,
      ComponentManager componentManager) throws ComponentException;

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
  protected ComponentInstanceContainer<S> requestById(String instanceId,
      ComponentDescriptor<?> sourceDescriptor, String sourceInstanceId,
      ComponentManager componentManager) throws ComponentException {

    throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
  }

}
