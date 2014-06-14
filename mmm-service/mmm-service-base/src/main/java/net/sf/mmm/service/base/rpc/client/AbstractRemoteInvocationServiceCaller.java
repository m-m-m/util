/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc.client;

import java.util.List;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.client.RemoteInvocationQueueSettings;
import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationCaller;
import net.sf.mmm.service.base.client.RemoteInvocationCallData;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcTransactionalCalls;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.lang.api.function.Consumer;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

/**
 * This is the abstract base-implementation of {@link RemoteInvocationServiceCaller}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCaller
    extends
    AbstractRemoteInvocationCaller<RemoteInvocationServiceQueue, GenericRemoteInvocationRpcCall, GenericRemoteInvocationRpcTransactionalCalls, //
    GenericRemoteInvocationRpcRequest> implements RemoteInvocationServiceCaller {

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCaller() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationServiceQueue createQueue(RemoteInvocationQueueSettings settings,
      AbstractRemoteInvocationQueue parentQueue) {

    return new RemoteInvocationServiceQueueImpl(settings, (RemoteInvocationServiceQueueImpl) parentQueue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationRpcTransactionalCalls createRemoteInvocationTransactionalCalls(
      List<GenericRemoteInvocationRpcCall> calls) {

    return new GenericRemoteInvocationRpcTransactionalCalls(calls);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationRpcRequest createRequest(int requestId, CsrfToken token,
      List<GenericRemoteInvocationRpcTransactionalCalls> transactionalCalls) {

    return new GenericRemoteInvocationRpcRequest(requestId, token, transactionalCalls);
  }

  /**
   * This method is called from the service-client stub to add a {@link GenericRemoteInvocationRpcCall}.
   *
   * @param call is the {@link GenericRemoteInvocationRpcCall} to add.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
   *        {@link java.lang.reflect.Method}.
   */
  protected void addCall(GenericRemoteInvocationRpcCall call, Class<?> returnType) {

    ((RemoteInvocationServiceQueueImpl) requireCurrentQueue()).addCall(call, returnType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback) {

    return getServiceClient(serviceInterface, returnType, successCallback, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

    RemoteInvocationServiceQueue queue = newQueueForAutoCommit();
    return queue.getServiceClient(serviceInterface, returnType, successCallback, failureCallback);
  }

  /**
   * @see net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue#getServiceClient(Class, Class,
   *      Consumer, Consumer)
   *
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @return the according client-service stub.
   */
  protected abstract <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(Class<SERVICE> serviceInterface);

  /**
   * This inner class is the implementation of {@link RemoteInvocationServiceQueue}.
   */
  protected class RemoteInvocationServiceQueueImpl extends AbstractRemoteInvocationQueue implements
      RemoteInvocationServiceQueue {

    /** The current {@link AbstractRemoteInvocationServiceCaller.ServiceCallData} or <code>null</code>. */
    private ServiceCallData<?> currentCall;

    /**
     * The constructor.
     *
     * @param settings - see {@link #getSettings()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationQueueSettings settings) {

      this(settings, null);
    }

    /**
     * The constructor.
     *
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationQueueSettings settings,
        RemoteInvocationServiceQueueImpl parentQueue) {

      super(settings, parentQueue);
    }

    /**
     * This method adds the given {@link GenericRemoteInvocationRpcCall} to this queue.
     *
     * @param call is the {@link GenericRemoteInvocationRpcCall} to add.
     * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
     *        {@link java.lang.reflect.Method}.
     */
    protected void addCall(GenericRemoteInvocationRpcCall call, Class<?> returnType) {

      if (this.currentCall == null) {
        throw new IllegalStateException(
            "No current service-client invocation prepared - only call one method per service-client!");
      }
      if (this.currentCall.returnType != returnType) {
        ReflectionUtilLimited reflectionUtil = ReflectionUtilLimitedImpl.getInstance();
        if (reflectionUtil.getNonPrimitiveType(this.currentCall.returnType) != reflectionUtil
            .getNonPrimitiveType(returnType)) {
          throw new ObjectMismatchException(returnType, this.currentCall.returnType, null, "returnType");
        }
      }
      if (!this.currentCall.serviceInterface.getName().equals(call.getServiceInterfaceName())) {
        throw new ObjectMismatchException(call.getServiceInterfaceName(), this.currentCall.serviceInterface, null,
            "service-interface");
      }
      ServiceCallData<?> callData = this.currentCall;
      this.currentCall = null;
      callData.setCall(call);
      addCall(callData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, Consumer<? extends RESULT> successCallback) {

      return getServiceClient(serviceInterface, returnType, successCallback, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

      requireOpen();
      requireNoCurrentCall();
      Consumer<Throwable> actualFailureCallback = failureCallback;
      if (actualFailureCallback == null) {
        actualFailureCallback = getDefaultFailureCallback();
        if (actualFailureCallback == null) {
          throw new NlsNullPointerException("failureCallback");
        }
      }

      SERVICE serviceClient = AbstractRemoteInvocationServiceCaller.this.getServiceClient(serviceInterface);
      this.currentCall = new ServiceCallData<RESULT>(serviceInterface, returnType, successCallback,
          actualFailureCallback);
      return serviceClient;
    }

  }

  /**
   * This inner class is a simple container for the data of an invocation of a {@link RemoteInvocationService}
   * method.
   *
   * @see GenericRemoteInvocationRpcCall
   *
   * @param <RESULT> is the generic type of the method return-type;
   */
  protected static class ServiceCallData<RESULT> extends
      RemoteInvocationCallData<RESULT, GenericRemoteInvocationRpcCall> {

    /** The current return-type. */
    private final Class<RESULT> returnType;

    /** The current {@link RemoteInvocationService} interface. */
    private final Class<?> serviceInterface;

    /**
     * The constructor.
     *
     * @param serviceInterface is the {@link RemoteInvocationService} interface.
     * @param returnType is the return type.
     * @param successCallback is the
     *        {@link RemoteInvocationServiceCaller#getServiceClient(Class, Class, Consumer, Consumer) success
     *        callback}.
     * @param failureCallback is the
     *        {@link RemoteInvocationServiceCaller#getServiceClient(Class, Class, Consumer, Consumer) failure
     *        callback}.
     */
    public ServiceCallData(Class<?> serviceInterface, Class<RESULT> returnType,
        Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

      super(successCallback, failureCallback);
      this.returnType = returnType;
      this.serviceInterface = serviceInterface;
    }

  }

}
