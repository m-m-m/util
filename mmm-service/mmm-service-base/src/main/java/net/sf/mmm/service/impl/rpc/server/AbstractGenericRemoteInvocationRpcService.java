/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.rpc.server;

import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

/**
 * This is the server-side default implementation of {@link GenericRemoteInvocationRpcService}. You can extend
 * this class to add custom logic.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractGenericRemoteInvocationRpcService extends
    AbstractGenericRemoteInvocationService<GenericRemoteInvocationRpcCall, GenericRemoteInvocationRpcRequest, //
    GenericRemoteInvocationRpcResponse, GenericRemoteInvocationRpcCallHandler<?>> implements
    GenericRemoteInvocationRpcService {

  /**
   * The constructor.
   */
  public AbstractGenericRemoteInvocationRpcService() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericRemoteInvocationRpcResponse callServices(GenericRemoteInvocationRpcRequest request) {

    return processRequest(request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getHandlerId(GenericRemoteInvocationRpcCall call) {

    return GenericRemoteInvocationRpcCallHandler.getId(call);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationRpcResponse createResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults[] transactionalResults) {

    return new GenericRemoteInvocationRpcResponse(requestId, xsrfToken, transactionalResults);
  }

  /**
   * This method sets the {@link List} with all available {@link RemoteInvocationService} implementations. It
   * is supposed to be invoked automatically for {@link Inject dependency-injection}.
   *
   * @param services is the {@link List} with all available {@link RemoteInvocationService} implementations.
   */
  @Inject
  public void setServices(List<RemoteInvocationService> services) {

    if (getHandlerCount() > 0) {
      throw new AlreadyInitializedException();
    }
    for (RemoteInvocationService service : services) {
      registerService(service);
    }
  }

  /**
   * This method registers the given {@link RemoteInvocationService}.
   *
   * @param service is the {@link RemoteInvocationService} to register.
   */
  protected void registerService(RemoteInvocationService service) {

    boolean registered = false;
    Class<?> serviceClass = service.getClass();
    Class<?> currentServiceClass = serviceClass;
    while (RemoteInvocationService.class.isAssignableFrom(currentServiceClass)) {
      for (Class<?> serviceInterface : currentServiceClass.getInterfaces()) {
        if ((RemoteInvocationService.class.isAssignableFrom(serviceInterface))
            && (serviceInterface != RemoteInvocationService.class)) {
          boolean empty = true;
          for (Method method : serviceInterface.getMethods()) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            GenericRemoteInvocationRpcCallHandler<?> handler = new GenericRemoteInvocationRpcCallHandler(
                serviceInterface, service, this, method);
            registerHandler(handler);
            registered = true;
            empty = false;
          }
          if (!empty) {
            getLogger().debug(
                "Bound service interface '" + serviceInterface.getName() + "' to '" + serviceClass.getName() + "'");
          }
        }
      }
      currentServiceClass = currentServiceClass.getSuperclass();
    }
    if (!registered) {
      throw new ObjectMismatchException(service, RemoteInvocationService.class);
    }
  }
}
