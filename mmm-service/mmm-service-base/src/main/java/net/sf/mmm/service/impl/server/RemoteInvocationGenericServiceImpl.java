/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the server-side default implementation of the {@link RemoteInvocationGenericService} interface. You
 * can extend this class to add custom logik. E.g. you could add
 * {@link #doSecurityCheck(RemoteInvocationGenericServiceRequest) custom security checks} or override
 * {@link #createResultOnSuccess(Serializable)} and {@link #createResultOnFailure(Throwable)} in order to add
 * context information to the result (e.g. request or session scoped) - see
 * {@link RemoteInvocationServiceQueue#getServiceClient(Class, Class, net.sf.mmm.service.api.client.RemoteInvocationServiceCallback)}
 * .
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
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
  @Override
  public RemoteInvocationGenericServiceResponse callServices(RemoteInvocationGenericServiceRequest request) {

    NlsNullPointerException.checkNotNull(RemoteInvocationGenericServiceRequest.class, request);
    getLogger().debug("start processing request {}.", request);
    doSecurityCheck(request);

    RemoteInvocationServiceCall[] calls = request.getCalls();
    RemoteInvocationServiceResult<?>[] results = new RemoteInvocationServiceResult<?>[calls.length];
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
      RemoteInvocationServiceResult<?> serviceResult;
      try {
        Serializable result = serviceMethod.invoke(call.getArguments());
        serviceResult = createResultOnSuccess(result);
      } catch (Throwable e) {
        serviceResult = createResultOnFailure(e);
      }
      results[i] = serviceResult;
      getLogger().debug("end processing call {}.", call.toString(true));
    }
    getLogger().debug("end processing request {}.", request);
    return response;
  }

  /**
   * This method creates the {@link RemoteInvocationServiceResult} in case the service-method successfully
   * returned a result.
   * 
   * @see net.sf.mmm.service.api.client.RemoteInvocationServiceCallback#onSuccess(Serializable, boolean)
   * 
   * @param result is the {@link RemoteInvocationServiceResult#getResult() result} of the service-method.
   * @return the {@link RemoteInvocationServiceResult} with the result.
   */
  protected <RESULT extends Serializable> RemoteInvocationServiceResult<RESULT> createResultOnSuccess(RESULT result) {

    return new RemoteInvocationServiceResult<RESULT>(result);
  }

  /**
   * This method creates the {@link RemoteInvocationServiceResult} in case the service-method threw an
   * exception.
   * 
   * @see net.sf.mmm.service.api.client.RemoteInvocationServiceCallback#onFailure(Throwable, boolean)
   * 
   * @param failure is the {@link RemoteInvocationServiceResult#getFailure() failure} that occurred when
   *        invoking the service-method.
   * @return the {@link RemoteInvocationServiceResult} with the failure.
   */
  protected RemoteInvocationServiceResult<Serializable> createResultOnFailure(Throwable failure) {

    return new RemoteInvocationServiceResult<Serializable>(failure);
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
    Class<?> currentServiceClass = serviceClass;
    while (RemoteInvocationService.class.isAssignableFrom(currentServiceClass)) {
      for (Class<?> serviceInterface : currentServiceClass.getInterfaces()) {
        if ((RemoteInvocationService.class.isAssignableFrom(serviceInterface))
            && (serviceInterface != RemoteInvocationService.class)) {
          boolean empty = true;
          for (Method method : serviceInterface.getMethods()) {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            RemoteInvocationServiceMethod<?> serviceMethod = new RemoteInvocationServiceMethod(serviceInterface,
                service, method);
            RemoteInvocationServiceMethod<?> old = this.serviceMap.put(serviceMethod.getId(), serviceMethod);
            if (old != null) {
              throw new DuplicateObjectException(serviceMethod, serviceMethod.getId(), old);
            }
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
