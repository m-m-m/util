/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueSettings;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

/**
 * This is the abstract base-implementation of {@link RemoteInvocationServiceCaller}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCaller implements RemoteInvocationServiceCaller {

  /** @see #getServiceClient(Class) */
  private final Map<Class<? extends RemoteInvocationService>, RemoteInvocationService> serviceClientMap;

  /** @see #nextRequestId() */
  private int requestCount;

  /** The current {@link RemoteInvocationServiceQueueImpl queue} or <code>null</code> for none. */
  private RemoteInvocationServiceQueueImpl currentQueue;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCaller() {

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
  public RemoteInvocationServiceQueueImpl newQueue() {

    return newQueue(new RemoteInvocationServiceQueueSettings());
  }

  /**
   * {@inheritDoc}
   */
  public RemoteInvocationServiceQueueImpl newQueue(RemoteInvocationServiceQueueSettings context) {

    if (this.currentQueue == null) {
      this.currentQueue = new RemoteInvocationServiceQueueImpl(context);
    } else {
      this.currentQueue = new RemoteInvocationServiceQueueImpl(context, this.currentQueue);
    }
    return this.currentQueue;
  }

  /**
   * @return the next {@link RemoteInvocationGenericServiceRequest#getRequestId() request ID}.
   */
  protected int nextRequestId() {

    return this.requestCount++;
  }

  /**
   * @return the current {@link RemoteInvocationServiceQueueImpl}.
   */
  protected RemoteInvocationServiceQueueImpl requireCurrentQueue() {

    if (this.currentQueue == null) {
      throw new IllegalStateException("No open queue!");
    }
    return this.currentQueue;
  }

  /**
   * This method is called from the service-client stub to add a {@link RemoteInvocationServiceCall}.
   * 
   * @param call is the {@link RemoteInvocationServiceCall} to add.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
   *        {@link java.lang.reflect.Method}.
   */
  protected void addCall(RemoteInvocationServiceCall call, Class<?> returnType) {

    requireCurrentQueue().addCall(call, returnType);
  }

  /**
   * @see RemoteInvocationServiceQueue#getServiceClient(Class, Class, RemoteInvocationServiceCallback)
   * 
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @return the according client-service stub.
   */
  protected <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(Class<SERVICE> serviceInterface) {

    SERVICE serviceClient = (SERVICE) this.serviceClientMap.get(serviceInterface);
    if (serviceClient == null) {
      throw new ObjectNotFoundException(RemoteInvocationService.class.getSimpleName(), serviceInterface);
    }
    return serviceClient;
  }

  /**
   * This method finally performs a request with the invocations collected by the given <code>queue</code>.
   * 
   * @param queue is the {@link RemoteInvocationServiceQueueImpl}.
   */
  protected void performRequest(RemoteInvocationServiceQueueImpl queue) {

    assert (queue == this.currentQueue);
    List<RemoteInvocationServiceCall> callQueue = queue.getCallQueue();
    if (callQueue.isEmpty()) {
      return;
    }
    RemoteInvocationServiceCall[] calls = callQueue.toArray(new RemoteInvocationServiceCall[callQueue.size()]);
    RemoteInvocationGenericServiceRequest request = new RemoteInvocationGenericServiceRequest(nextRequestId(), calls);
    List<RemoteInvocationServiceCallback<?>> callbackQueue = queue.getCallbackQueue();
    int size = callbackQueue.size();
    if (size != calls.length) {
      throw new IllegalStateException("Length of service calls and callbacks does NOT match!");
    }
    RemoteInvocationServiceCallback<?>[] callbacks = callbackQueue
        .toArray(new RemoteInvocationServiceCallback<?>[callbackQueue.size()]);
    performRequest(request, callbacks);
    this.currentQueue = null;
  }

  /**
   * This method finally performs the given <code>request</code>.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} to perform.
   * @param callbacks is the array of {@link RemoteInvocationServiceCallback}s according to
   *        {@link RemoteInvocationGenericServiceRequest#getCalls()}.
   */
  protected abstract void performRequest(RemoteInvocationGenericServiceRequest request,
      RemoteInvocationServiceCallback<?>[] callbacks);

  /**
   * This inner class is the implementation of {@link RemoteInvocationServiceQueue}.
   */
  protected class RemoteInvocationServiceQueueImpl implements RemoteInvocationServiceQueue {

    /** @see #getParentQueue() */
    private final RemoteInvocationServiceQueueImpl parentQueue;

    /** @see #getSettings() */
    private final RemoteInvocationServiceQueueSettings settings;

    /** @see #getCallQueue() */
    private final List<RemoteInvocationServiceCall> callQueue;

    /** @see #getCallbackQueue() */
    private final List<RemoteInvocationServiceCallback<?>> callbackQueue;

    /** The current {@link ServiceCallData} or <code>null</code>. */
    private ServiceCallData<?> currentCall;

    /** @see #isOpen() */
    private boolean open;

    /**
     * The constructor.
     * 
     * @param context - see {@link #getSettings()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationServiceQueueSettings context) {

      this(context, null);
    }

    /**
     * This method adds the given {@link RemoteInvocationServiceCall} to this queue.
     * 
     * @param call is the {@link RemoteInvocationServiceCall} to add.
     * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
     *        {@link java.lang.reflect.Method}.
     */
    protected void addCall(RemoteInvocationServiceCall call, Class<?> returnType) {

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
      this.callQueue.add(call);
      this.callbackQueue.add(this.currentCall.callback);
      this.currentCall = null;
    }

    /**
     * The constructor.
     * 
     * @param context - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationServiceQueueSettings context,
        RemoteInvocationServiceQueueImpl parentQueue) {

      super();
      this.settings = context;
      this.parentQueue = parentQueue;
      this.callQueue = new ArrayList<RemoteInvocationServiceCall>();
      this.callbackQueue = new ArrayList<RemoteInvocationServiceCallback<?>>();
      this.open = true;
    }

    /**
     * @return the {@link RemoteInvocationServiceQueueSettings} given when this queue has been
     *         {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) created}.
     */
    public RemoteInvocationServiceQueueSettings getSettings() {

      return this.settings;
    }

    /**
     * @return the parentQueue
     */
    public RemoteInvocationServiceQueueImpl getParentQueue() {

      return this.parentQueue;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOpen() {

      return this.open;
    }

    /**
     * Internal method to ensure this queue is still {@link #isOpen() open}.
     */
    protected void requireOpen() {

      if (!this.open) {
        throw new IllegalStateException("Queue not open!");
      }
    }

    /**
     * @return the queue
     */
    public List<RemoteInvocationServiceCall> getCallQueue() {

      return this.callQueue;
    }

    /**
     * @return the callbackQueue
     */
    public List<RemoteInvocationServiceCallback<?>> getCallbackQueue() {

      return this.callbackQueue;
    }

    /**
     * {@inheritDoc}
     */
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, RemoteInvocationServiceCallback<? extends RESULT> callback) {

      requireOpen();
      requireNoCurrentCall();
      SERVICE serviceClient = AbstractRemoteInvocationServiceCaller.this.getServiceClient(serviceInterface);
      this.currentCall = new ServiceCallData<RESULT>(serviceInterface, returnType, callback);
      return serviceClient;
    }

    /**
     * Internal method to ensure that no current call is pending.
     */
    protected void requireNoCurrentCall() {

      if (this.currentCall != null) {
        throw new IllegalStateException("Pending call " + this.currentCall.serviceInterface.getSimpleName()
            + " for return-type " + this.currentCall.returnType.getSimpleName() + " not completed.");
      }
    }

    /**
     * {@inheritDoc}
     */
    public void commit() {

      requireOpen();
      requireNoCurrentCall();
      if (this.parentQueue == null) {
        performRequest(this);
      } else {
        this.parentQueue.callQueue.addAll(this.callQueue);
      }
      this.callQueue.clear();
      this.open = false;
      AbstractRemoteInvocationServiceCaller.this.currentQueue = this.parentQueue;
    }

    /**
     * {@inheritDoc}
     */
    public void cancel() {

      requireOpen();
      this.callQueue.clear();
      this.currentCall = null;
      this.open = false;
      AbstractRemoteInvocationServiceCaller.this.currentQueue = this.parentQueue;
    }

  }

  /**
   * This inner class is a simple container for the data of an invocation of a {@link RemoteInvocationService}
   * method.
   * 
   * @see RemoteInvocationServiceQueueImpl#getServiceClient(Class, Class, RemoteInvocationServiceCallback)
   * @see RemoteInvocationServiceCall
   * 
   * @param <RESULT> is the generic type of the method return-type;
   */
  private static class ServiceCallData<RESULT> {

    /** The current {@link RemoteInvocationServiceCallback}. */
    private final RemoteInvocationServiceCallback<? extends RESULT> callback;

    /** The current return-type. */
    private final Class<RESULT> returnType;

    /** The current {@link RemoteInvocationService} interface. */
    private final Class<?> serviceInterface;

    /**
     * The constructor.
     * 
     * @param serviceInterface is the {@link RemoteInvocationService} interface.
     * @param returnType is the return type.
     * @param callback is the {@link RemoteInvocationServiceCallback}.
     */
    public ServiceCallData(Class<?> serviceInterface, Class<RESULT> returnType,
        RemoteInvocationServiceCallback<? extends RESULT> callback) {

      super();
      this.callback = callback;
      this.returnType = returnType;
      this.serviceInterface = serviceInterface;
    }

  }

}
