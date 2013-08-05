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
import net.sf.mmm.service.api.RemoteInvocationServiceCallFailedException;
import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalCalls;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalResults;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsThrowable;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the server-side default implementation of {@link RemoteInvocationGenericService}. You can extend
 * this class to add custom logic. E.g. you could add
 * {@link #doSecurityCheck(RemoteInvocationGenericServiceRequest) custom security checks} or override the
 * {@link #handleFailure(RemoteInvocationServiceCall, Throwable) error handling}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationGenericServiceImpl extends AbstractLoggableComponent implements
    RemoteInvocationGenericService {

  /** @see #registerService(RemoteInvocationService) */
  private final Map<String, RemoteInvocationServiceMethod<?>> serviceMap;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationGenericServiceImpl() {

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

    RemoteInvocationServiceTransactionalCalls[] transactionalCalls = request.getTransactionalCalls();
    RemoteInvocationServiceTransactionalResults[] transactionalResults = new RemoteInvocationServiceTransactionalResults[transactionalCalls.length];
    int i = 0;
    for (RemoteInvocationServiceTransactionalCalls txCall : transactionalCalls) {
      RemoteInvocationServiceTransactionalResults txResult;
      try {
        txResult = callServicesInTransaction(txCall);
      } catch (RemoteInvocationServiceCallFailedException e) {
        txResult = new RemoteInvocationServiceTransactionalResults(e.getCause());
      } catch (Throwable e) {
        logFailure(txCall, e);
        // TODO hohwille add a hook to allow users to prevent stacktrace or technical errors from being set to
        // client.
        txResult = new RemoteInvocationServiceTransactionalResults(e);
      }
      transactionalResults[i++] = txResult;
    }
    RemoteInvocationGenericServiceResponse response = new RemoteInvocationGenericServiceResponse(
        request.getRequestId(), transactionalResults);
    getLogger().debug("end processing request {}.", request);
    return response;
  }

  /**
   * This method calls {@link #callServicesTransactionalCalls(RemoteInvocationServiceTransactionalCalls)} in a
   * new transaction.<br/>
   * <b>ATTENTION:</b><br/>
   * If you want to implement this using spring-aop and <code>@Transactional</code> you need to be aware that
   * have to keep the annotated method out of this class as spring-aop will only support transactions when a
   * class is called from outside and not for method calls within a class. This is a typical pitfall and one
   * of the reasons why we are using <code>mmm-transaction</code> by default.
   * 
   * @param transactionalCalls is the {@link RemoteInvocationServiceTransactionalCalls}.
   * @return the {@link RemoteInvocationServiceTransactionalResults}.
   */
  protected abstract RemoteInvocationServiceTransactionalResults callServicesInTransaction(
      RemoteInvocationServiceTransactionalCalls transactionalCalls);

  /**
   * This method processes the given {@link RemoteInvocationServiceTransactionalCalls}.
   * 
   * @param transactionalCalls is the {@link RemoteInvocationServiceTransactionalCalls}.
   * @return the {@link RemoteInvocationServiceTransactionalResults}.
   */
  protected RemoteInvocationServiceTransactionalResults callServicesTransactionalCalls(
      RemoteInvocationServiceTransactionalCalls transactionalCalls) {

    NlsNullPointerException.checkNotNull(RemoteInvocationServiceTransactionalCalls.class, transactionalCalls);
    RemoteInvocationServiceCall[] calls = transactionalCalls.getCalls();
    Serializable[] results = new Serializable[calls.length];

    int i = 0;
    for (RemoteInvocationServiceCall call : calls) {
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("start processing call {}.", call.toString(true));
      }
      String id = RemoteInvocationServiceMethod.getId(call);
      RemoteInvocationServiceMethod<?> serviceMethod = this.serviceMap.get(id);
      if (serviceMethod == null) {
        throw new ObjectNotFoundException(RemoteInvocationServiceMethod.class.getSimpleName(), id);
      }
      // method found, invoke it and add result to response...
      try {
        Serializable result = serviceMethod.invoke(call.getArguments());
        results[i++] = result;
      } catch (Throwable e) {
        RemoteInvocationServiceCallFailedException callFailedException = handleFailure(call, e);
        throw callFailedException;
      }
      if (getLogger().isDebugEnabled()) {
        getLogger().debug("end processing call {}.", call.toString(true));
      }
    }
    return new RemoteInvocationServiceTransactionalResults(results);
  }

  /**
   * This method contains the failure handling if a {@link Throwable} occurred while processing a
   * {@link RemoteInvocationServiceCall}.
   * 
   * @param call is the {@link RemoteInvocationServiceCall}.
   * @param error is the {@link Throwable} that occurred.
   * @return the {@link RemoteInvocationServiceCallFailedException} to throw.
   */
  protected RemoteInvocationServiceCallFailedException handleFailure(RemoteInvocationServiceCall call, Throwable error) {

    logFailure(call, error);
    return new RemoteInvocationServiceCallFailedException(error, call.getServiceInterfaceName(), call.getMethodName());
  }

  /**
   * Logs a given <code>error</code> that occurred while processing the given <code>source</code>.
   * 
   * @param source is the source object. We do not use {@link Object#toString()} here as this is for debug
   *        output and might contain confidential information such as passwords or whatever that we do not
   *        want to log.
   * @param error is the {@link Throwable} to log.
   */
  protected void logFailure(AttributeReadTitle<String> source, Throwable error) {

    if (getLogger().isInfoEnabled()) {
      if ((error instanceof NlsThrowable) && (!((NlsThrowable) error).isTechnical())) {
        // user failure
        getLogger().info("error while processing {}: {}", source.getTitle(), error.getMessage());
      } else {
        // technical error
        getLogger().error("error while processing {}.", source.getTitle(), error);
      }
    }
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
