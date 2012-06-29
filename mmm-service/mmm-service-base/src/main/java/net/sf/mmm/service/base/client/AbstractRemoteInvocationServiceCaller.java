/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueSettings;
import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

/**
 * This is the abstract base-implementation of {@link RemoteInvocationServiceCaller}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCaller extends AbstractLoggableComponent implements
    RemoteInvocationServiceCaller {

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
  @Override
  public RemoteInvocationServiceQueueImpl newQueue() {

    return newQueue(new RemoteInvocationServiceQueueSettings());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueue newQueue(String id) {

    return newQueue(new RemoteInvocationServiceQueueSettings(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue(RemoteInvocationServiceQueueSettings settings) {

    return this.currentQueue = createQueue(settings);
  }

  /**
   * This method creates a new {@link RemoteInvocationServiceQueueImpl}. If a {@link #getCurrentQueue()
   * current queue} is present, the new queue has to use it as
   * {@link RemoteInvocationServiceQueueImpl#getParentQueue() parent}.
   * 
   * @param settings are the {@link RemoteInvocationServiceQueueSettings}.
   * @return the new {@link RemoteInvocationServiceQueueImpl}.
   */
  protected RemoteInvocationServiceQueueImpl createQueue(RemoteInvocationServiceQueueSettings settings) {

    return new RemoteInvocationServiceQueueImpl(settings, getCurrentQueue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl getCurrentQueue() {

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
    List<RemoteInvocationServiceResultCallback<?>> callbackQueue = queue.getCallbackQueue();
    int size = callbackQueue.size();
    if (size != calls.length) {
      throw new IllegalStateException("Length of service calls and callbacks does NOT match!");
    }
    RemoteInvocationServiceResultCallback<?>[] callbacks = callbackQueue
        .toArray(new RemoteInvocationServiceResultCallback<?>[callbackQueue.size()]);
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
      RemoteInvocationServiceResultCallback<?>[] callbacks);

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
    private final List<RemoteInvocationServiceResultCallback<?>> callbackQueue;

    /** The current {@link AbstractRemoteInvocationServiceCaller.ServiceCallData} or <code>null</code>. */
    private ServiceCallData<?> currentCall;

    /** @see #isOpen() */
    private boolean open;

    /** @see #getParentQueue() */
    private RemoteInvocationServiceQueueImpl childQueue;

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationServiceQueueSettings settings) {

      this(settings, null);
    }

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationServiceQueueImpl(RemoteInvocationServiceQueueSettings settings,
        RemoteInvocationServiceQueueImpl parentQueue) {

      super();
      this.settings = settings;
      this.parentQueue = parentQueue;
      if (parentQueue != null) {
        parentQueue.childQueue = this;
      }
      this.callQueue = new ArrayList<RemoteInvocationServiceCall>();
      this.callbackQueue = new ArrayList<RemoteInvocationServiceResultCallback<?>>();
      this.open = true;
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
     * @return the {@link RemoteInvocationServiceQueueSettings} given when this queue has been
     *         {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) created}.
     */
    public RemoteInvocationServiceQueueSettings getSettings() {

      return this.settings;
    }

    /**
     * @return an info string with the {@link RemoteInvocationServiceQueueSettings#getId() ID} or the empty
     *         string if not present.
     */
    protected String getId() {

      String id = this.settings.getId();
      if (id == null) {
        return "";
      } else {
        return "(" + id + ")";
      }
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
    @Override
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
    public List<RemoteInvocationServiceResultCallback<?>> getCallbackQueue() {

      return this.callbackQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, RemoteInvocationServiceCallback<? extends RESULT> callback) {

      @SuppressWarnings({ "rawtypes", "unchecked" })
      RemoteInvocationServiceResultCallback<? extends RESULT> resultCallback = new RemoteInvocationServiceCallbackAdapter(
          callback);
      return getServiceClient(serviceInterface, returnType, resultCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, RemoteInvocationServiceResultCallback<? extends RESULT> callback) {

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
     * Internal method to ensure that this queue is the current active queue and no child-queue has been
     * opened and not completed.
     */
    protected void requireNoChildQueue() {

      if (this.childQueue != null) {
        throw new IllegalStateException("Child-queue" + this.childQueue.getId() + " of this queue" + getId()
            + " not completed.");
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commit() {

      requireOpen();
      requireNoCurrentCall();
      requireNoChildQueue();
      try {
        if (this.parentQueue == null) {
          performRequest(this);
        } else {
          this.parentQueue.callQueue.addAll(this.callQueue);
        }
      } finally {
        close();
      }
    }

    /**
     * This method closes this queue. It is called from {@link #commit()} and {@link #cancel()}.
     */
    private void close() {

      this.callQueue.clear();
      this.open = false;
      if (this.parentQueue != null) {
        assert (this.parentQueue.childQueue == this);
        this.parentQueue.childQueue = null;
      }
      assert (AbstractRemoteInvocationServiceCaller.this.currentQueue == this);
      AbstractRemoteInvocationServiceCaller.this.currentQueue = this.parentQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {

      requireOpen();
      if (this.childQueue != null) {
        getLogger().warn(
            "Canceling of queue" + getId() + " will also cancel open child-queue" + this.childQueue.getId() + ".");
        this.childQueue.cancel();
      }
      this.currentCall = null;
      close();
    }

  }

  /**
   * This inner class is a simple container for the data of an invocation of a {@link RemoteInvocationService}
   * method.
   * 
   * @see RemoteInvocationServiceCall
   * 
   * @param <RESULT> is the generic type of the method return-type;
   */
  private static class ServiceCallData<RESULT> {

    /** The current {@link RemoteInvocationServiceResultCallback}. */
    private final RemoteInvocationServiceResultCallback<? extends RESULT> callback;

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
        RemoteInvocationServiceResultCallback<? extends RESULT> callback) {

      super();
      this.callback = callback;
      this.returnType = returnType;
      this.serviceInterface = serviceInterface;
    }

  }

  /**
   * This inner class adapts from {@link RemoteInvocationServiceResultCallback} to
   * {@link RemoteInvocationServiceCallback}.
   * 
   * @param <RESULT> is the generic type of the {@link #onResult(RemoteInvocationServiceResult, boolean)
   *        result to receive}.
   */
  private static class RemoteInvocationServiceCallbackAdapter<RESULT extends Serializable> implements
      RemoteInvocationServiceResultCallback<RESULT> {

    /** @see #onResult(RemoteInvocationServiceResult, boolean) */
    private final RemoteInvocationServiceCallback<RESULT> delegate;

    /**
     * The constructor.
     * 
     * @param delegate is the {@link RemoteInvocationServiceCallback} to adapt.
     */
    public RemoteInvocationServiceCallbackAdapter(RemoteInvocationServiceCallback<RESULT> delegate) {

      super();
      this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResult(RemoteInvocationServiceResult<RESULT> result, boolean complete) {

      Throwable failure = result.getFailure();
      if (failure == null) {
        this.delegate.onSuccess(result.getResult(), complete);
      } else {
        this.delegate.onFailure(failure, complete);
      }
    }

  }

}
