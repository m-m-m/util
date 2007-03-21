/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.base.provider.InstanceIdNotAvailableException;

/**
 * This is the abstract base implementation of the
 * {@link ComponentInstantiationManager} interface.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptor#getSpecification() specification} of
 *        the component.
 * @param <I>
 *        is the
 *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDefaultComponentInstantiationManager<S, I extends S> extends
    AbstractComponentInstantiationManager<S, I> {

  /**
   * The constructor.
   */
  public AbstractDefaultComponentInstantiationManager() {

    super();
  }

  /**
   * @see AbstractComponentInstantiationManager#AbstractComponentInstantiationManager(ExtendedComponentDescriptor)
   */
  public AbstractDefaultComponentInstantiationManager(
      ExtendedComponentDescriptor<S, I> componentDescriptor) {

    super(componentDescriptor);
  }

  /**
   * {@inheritDoc}
   */
  public final ExtendedComponentInstanceContainerImpl<S, I> request(String instanceId)
      throws InstanceIdNotAvailableException {

    if (ComponentManager.DEFAULT_INSTANCE_ID.equals(instanceId)) {
      return requestDefault();
    } else {
      return requestByInstanceId(instanceId);
    }
  }

  /**
   * @see net.sf.mmm.framework.base.ComponentInstantiationManager#request(String)
   * 
   * @return the container for the
   *         {@link ComponentManager#DEFAULT_INSTANCE_ID default} instance.
   */
  public abstract ExtendedComponentInstanceContainerImpl<S, I> requestDefault();

  /**
   * @see net.sf.mmm.framework.base.ComponentInstantiationManager#request(String)
   * 
   * @param instanceId
   * @return the requested instance-ID
   * @throws InstanceIdNotAvailableException
   *         if the component is NOT available for the requested
   *         <code>instanceId</code>.
   */
  public ExtendedComponentInstanceContainerImpl<S, I> requestByInstanceId(String instanceId)
      throws InstanceIdNotAvailableException {

    throw new InstanceIdNotAvailableException(instanceId, getDescriptor());
  }

}
