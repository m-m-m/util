/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base-implementation of
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCaller} using a map of
 * {@link #registerService(Class, RemoteInvocationService) pre-registered} service clients.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCallerWithClientMap extends AbstractRemoteInvocationServiceCaller {

  /** @see #getServiceClient(Class) */
  private final Map<Class<? extends RemoteInvocationService>, RemoteInvocationService> serviceClientMap;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCallerWithClientMap() {

    super();
    this.serviceClientMap = new HashMap<Class<? extends RemoteInvocationService>, RemoteInvocationService>();
  }

  /**
   * This method registers a {@link #getServiceClient(Class) service-client}.
   * 
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @param serviceClient is the client stub for the given <code>serviceInterface</code>.
   */
  protected <SERVICE extends RemoteInvocationService> void registerService(Class<SERVICE> serviceInterface,
      SERVICE serviceClient) {

    RemoteInvocationService old = this.serviceClientMap.put(serviceInterface, serviceClient);
    if (old != null) {
      throw new DuplicateObjectException(serviceClient, serviceInterface, old);
    }
    ((AbstractRemoteInvocationServiceClient) serviceClient).setRemoteInvocationSerivceCaller(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(Class<SERVICE> serviceInterface) {

    SERVICE serviceClient = (SERVICE) this.serviceClientMap.get(serviceInterface);
    if (serviceClient == null) {
      throw new ObjectNotFoundException(RemoteInvocationService.class.getSimpleName(), serviceInterface);
    }
    return serviceClient;
  }

}
