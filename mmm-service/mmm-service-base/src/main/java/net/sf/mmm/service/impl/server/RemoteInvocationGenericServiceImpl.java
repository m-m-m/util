/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceContext;
import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.RemoteInvocationServiceResult;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the server-side default implementation of the {@link RemoteInvocationGenericService} interface.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class RemoteInvocationGenericServiceImpl extends AbstractLoggableComponent implements
    RemoteInvocationGenericService {

  /** @see #registerService(RemoteInvocationService) */
  private final Map<String, RemoteInvocationServiceMethod<?>> serviceMap;

  /**
   * The constructor.
   */
  public RemoteInvocationGenericServiceImpl() {

    super();
    this.serviceMap = new HashMap<String, RemoteInvocationServiceMethod<?>>();
  }

  /**
   * {@inheritDoc}
   */
  public RemoteInvocationGenericServiceResponse callServices(RemoteInvocationGenericServiceRequest request) {

    NlsNullPointerException.checkNotNull(RemoteInvocationGenericServiceRequest.class, request);
    getLogger().debug("start processing request {}.", request);
    doSecurityCheck(request);

    RemoteInvocationServiceCall[] calls = request.getCalls();
    RemoteInvocationServiceResult[] results = new RemoteInvocationServiceResult[calls.length];
    RemoteInvocationGenericServiceResponse response = new RemoteInvocationGenericServiceResponse(
        request.getRequestId(), results);

    for (int i = 0; i < calls.length; i++) {
      RemoteInvocationServiceCall call = calls[i];
      getLogger().debug("start processing call {}.", call.toString(true));
      String id = RemoteInvocationServiceMethod.getId(call);
      RemoteInvocationServiceMethod<?> serviceMethod = this.serviceMap.get(id);
      if (serviceMethod == null) {
        throw new ObjectNotFoundException(RemoteInvocationServiceMethod.class.getSimpleName(), id);
      }
      // method found, invoke it and add result to response...
      RemoteInvocationServiceContext context = null;
      RemoteInvocationServiceResult serviceResult;
      try {
        Serializable result = serviceMethod.invoke(call.getArguments());
        serviceResult = new RemoteInvocationServiceResult(result, context);
      } catch (Throwable e) {
        serviceResult = createFailureResult(e, context);
      }
      results[i] = serviceResult;
      getLogger().debug("end processing call {}.", call.toString(true));
    }
    getLogger().debug("end processing request {}.", request);
    return response;
  }

  /**
   * This method creates the {@link RemoteInvocationServiceResult} in case the service-method threw an
   * exception.
   * 
   * @param failure is the {@link Throwable} that occurred when invoking the service-method.
   * @param context is the {@link RemoteInvocationServiceContext}.
   * @return the {@link RemoteInvocationServiceResult} with the failure.
   */
  private RemoteInvocationServiceResult createFailureResult(Throwable failure, RemoteInvocationServiceContext context) {

    return new RemoteInvocationServiceResult(failure, context);
  }

  /**
   * This method performs a security check for the given request.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} to check.
   */
  protected void doSecurityCheck(RemoteInvocationGenericServiceRequest request) {

    // nothing to do...
  }

  /**
   * This method sets the {@link List} with all available {@link RemoteInvocationService} implementations. It
   * is supposed to be invoked automatically for {@link Inject dependency-injection}.
   * 
   * @param services is the {@link List} with all available {@link RemoteInvocationService} implementations.
   */
  @Inject
  public void setServices(List<RemoteInvocationService> services) {

    if (!this.serviceMap.isEmpty()) {
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
    while (RemoteInvocationService.class.isAssignableFrom(serviceClass)) {
      for (Class<?> serviceInterface : serviceClass.getInterfaces()) {
        if ((RemoteInvocationService.class.isAssignableFrom(serviceInterface))
            && (serviceInterface != RemoteInvocationService.class)) {
          for (Method method : serviceInterface.getMethods()) {
            // if (RemoteInvocationService.class.isAssignableFrom(method.getDeclaringClass())) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            RemoteInvocationServiceMethod<?> serviceMethod = new RemoteInvocationServiceMethod(serviceInterface,
                service, method);
            RemoteInvocationServiceMethod<?> old = this.serviceMap.put(serviceMethod.getId(), serviceMethod);
            if (old != null) {
              throw new DuplicateObjectException(serviceMethod, serviceMethod.getId(), old);
            }
            registered = true;
            // }
          }
        }
      }
      serviceClass = serviceClass.getSuperclass();
    }
    if (!registered) {
      throw new ObjectMismatchException(service, RemoteInvocationService.class);
    }
  }
}
