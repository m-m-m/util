/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallFailedException;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueSettings;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
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

  /** @see #nextRequestId() */
  private int requestCount;

  /** The current {@link RemoteInvocationServiceQueueImpl queue} or <code>null</code> for none. */
  private RemoteInvocationServiceQueueImpl currentQueue;

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
  public RemoteInvocationServiceQueueImpl newQueue() {

    return newQueue(new RemoteInvocationServiceQueueSettings());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue(String id) {

    return newQueue(new RemoteInvocationServiceQueueSettings(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue(RemoteInvocationServiceQueueSettings settings) {

    this.currentQueue = createQueue(settings);
    return this.currentQueue;
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

    RemoteInvocationServiceQueueImpl queue = getCurrentQueue();
    if ((queue != null) && queue.autoCommit) {
      throw new NlsIllegalStateException();
    }
    return new RemoteInvocationServiceQueueImpl(settings, queue);
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
   * This method finally performs a request with the invocations collected by the given <code>queue</code>.
   * 
   * @param queue is the {@link RemoteInvocationServiceQueueImpl}.
   */
  protected void performRequest(RemoteInvocationServiceQueueImpl queue) {

    assert (queue == this.currentQueue);
    List<ServiceCallData<?>> callQueue = queue.callQueue;
    if (callQueue.isEmpty()) {
      return;
    }
    RemoteInvocationServiceCall[] calls = new RemoteInvocationServiceCall[callQueue.size()];
    int i = 0;
    for (ServiceCallData<?> callData : callQueue) {
      calls[i++] = callData.call;
    }
    RemoteInvocationGenericServiceRequest request = new RemoteInvocationGenericServiceRequest(nextRequestId(), calls);
    performRequest(request, callQueue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

    RemoteInvocationServiceQueueImpl queue = newQueue("auto-commit");
    queue.autoCommit = true;
    return queue.getServiceClient(serviceInterface, returnType, successCallback, failureCallback);
  }

  /**
   * @see net.sf.mmm.service.api.client.RemoteInvocationServiceQueue#getServiceClient(Class, Class, Consumer,
   *      Consumer)
   * 
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @return the according client-service stub.
   */
  protected abstract <SERVICE extends RemoteInvocationService> SERVICE getServiceClient(Class<SERVICE> serviceInterface);

  /**
   * This method finally performs the given <code>request</code>.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} to perform.
   * @param serviceCalls is the {@link List} of {@link ServiceCallData} corresponding to
   *        {@link RemoteInvocationGenericServiceRequest#getCalls()}.
   */
  protected abstract void performRequest(RemoteInvocationGenericServiceRequest request,
      List<ServiceCallData<?>> serviceCalls);

  /**
   * This method is called from
   * {@link #handleResponse(RemoteInvocationGenericServiceRequest, List, RemoteInvocationGenericServiceResponse)}
   * to handle an individual result.
   * 
   * @param <RESULT> is the generic type of the received result.
   * 
   * @param call is the corresponding {@link RemoteInvocationServiceCall}.
   * @param result is the received {@link RemoteInvocationServiceResult} to handle.
   * @param successCallback is the {@link Consumer} that will {@link Consumer#accept(Object) receive} the
   *        <code>result</code>.
   * @param failureCallback is the {@link Consumer} that will {@link Consumer#accept(Object) receive} a
   *        potential {@link RemoteInvocationServiceResult#getFailure() failure}.
   * @param complete - <code>true</code> if this is the last result for a request, <code>false</code>
   *        otherwise.
   */
  protected <RESULT extends Serializable> void handleResult(RemoteInvocationServiceCall call,
      RemoteInvocationServiceResult<RESULT> result, Consumer<RESULT> successCallback,
      Consumer<Throwable> failureCallback, boolean complete) {

    Throwable failure = result.getFailure();
    if (failure != null) {
      failureCallback.accept(failure);
    } else {
      successCallback.accept(result.getResult());
    }
  }

  /**
   * This method should be called from {@link #performRequest(RemoteInvocationGenericServiceRequest, List)} if
   * a {@link RemoteInvocationGenericServiceResponse} has been received.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} that has been
   *        {@link #performRequest(RemoteInvocationGenericServiceRequest, List) performed}.
   * @param serviceCalls is the list of {@link ServiceCallData} with the callbacks.
   * @param response is the {@link RemoteInvocationGenericServiceResponse} to handle.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void handleResponse(RemoteInvocationGenericServiceRequest request, List<ServiceCallData<?>> serviceCalls,
      RemoteInvocationGenericServiceResponse response) {

    if (request.getRequestId() != response.getRequestId()) {
      String source = "request-ID";
      throw new ObjectMismatchException(Integer.valueOf(response.getRequestId()), Integer.valueOf(request
          .getRequestId()), source);
    }
    RemoteInvocationServiceResult[] results = response.getResults();
    RemoteInvocationServiceCall[] calls = request.getCalls();
    if ((results.length != calls.length) || (serviceCalls.size() != calls.length)) {
      String source = "#calls/results";
      throw new ObjectMismatchException(Integer.valueOf(results.length), Integer.valueOf(calls.length), source);
    }
    int i = 0;
    for (ServiceCallData<?> callData : serviceCalls) {
      RemoteInvocationServiceCall call = calls[i];
      RemoteInvocationServiceResult result = results[i];
      Consumer successCallback = callData.successCallback;
      boolean complete = (i == (results.length - 1));
      handleResult(call, result, successCallback, callData.failureCallback, complete);
      i++;
    }
  }

  /**
   * This method should be called from {@link #performRequest(RemoteInvocationGenericServiceRequest, List)} if
   * a general {@link Throwable failure} occurred on the client side (in case of a network error or the like).
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} that has been
   *        {@link #performRequest(RemoteInvocationGenericServiceRequest, List) performed}.
   * @param serviceCalls is the list of {@link ServiceCallData} with the callbacks.
   * @param failure is the {@link Throwable} that has been catched on the client.
   */
  protected void handleFailure(RemoteInvocationGenericServiceRequest request, List<ServiceCallData<?>> serviceCalls,
      Throwable failure) {

    RemoteInvocationServiceCall[] calls = request.getCalls();
    int i = 0;
    for (ServiceCallData<?> callData : serviceCalls) {
      RemoteInvocationServiceCall call = calls[i++];
      callData.failureCallback.accept(new RemoteInvocationServiceCallFailedException(failure, call
          .getServiceInterfaceName(), call.getMethodName() + call.getArguments()));
    }
  }

  /**
   * This inner class is the implementation of {@link RemoteInvocationServiceQueue}.
   */
  protected class RemoteInvocationServiceQueueImpl implements RemoteInvocationServiceQueue {

    /** @see #getSettings() */
    private final RemoteInvocationServiceQueueSettings settings;

    /** @see #getParentQueue() */
    private RemoteInvocationServiceQueueImpl parentQueue;

    /** @see #getCallQueue() */
    private List<ServiceCallData<?>> callQueue;

    /** @see #getDefaultFailureCallback() */
    private Consumer<Throwable> defaultFailureCallback;

    /** The current {@link AbstractRemoteInvocationServiceCaller.ServiceCallData} or <code>null</code>. */
    private ServiceCallData<?> currentCall;

    /** @see #isOpen() */
    private boolean open;

    /** @see #getParentQueue() */
    private RemoteInvocationServiceQueueImpl childQueue;

    /** @see #addCall(RemoteInvocationServiceCall, Class) */
    private boolean autoCommit;

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
      NlsNullPointerException.checkNotNull(RemoteInvocationServiceQueueSettings.class, settings);
      this.settings = settings;
      this.parentQueue = parentQueue;
      if (parentQueue != null) {
        parentQueue.childQueue = this;
      }
      this.callQueue = new ArrayList<AbstractRemoteInvocationServiceCaller.ServiceCallData<?>>();
      this.open = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultFailureCallback(Consumer<Throwable> failureCallback) {

      this.defaultFailureCallback = failureCallback;
    }

    /**
     * @return the {@link #setDefaultFailureCallback(Consumer) default failure-callback}.
     */
    public Consumer<Throwable> getDefaultFailureCallback() {

      if ((this.defaultFailureCallback == null) && (this.parentQueue != null)) {
        return this.parentQueue.getDefaultFailureCallback();
      }
      return this.defaultFailureCallback;
    }

    /**
     * @return the callQueue
     */
    public List<ServiceCallData<?>> getCallQueue() {

      return this.callQueue;
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
      ServiceCallData<?> callData = this.currentCall;
      this.currentCall = null;
      callData.call = call;
      this.callQueue.add(callData);
      if (this.autoCommit) {
        commit();
      }
    }

    /**
     * @return the {@link RemoteInvocationServiceQueueSettings} given when this queue has been
     *         {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) created}.
     */
    public RemoteInvocationServiceQueueSettings getSettings() {

      return this.settings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {

      return this.settings.getId();
    }

    /**
     * @return an info string with the {@link RemoteInvocationServiceQueueSettings#getId() ID} or the empty
     *         string if not present.
     */
    protected String getIdInfo() {

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
        throw new IllegalStateException("Child-queue" + this.childQueue.getIdInfo() + " of this queue" + getIdInfo()
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

      this.open = false;

      // disconnect from parent
      if (this.parentQueue != null) {
        assert (this.parentQueue.childQueue == this);
        this.parentQueue.childQueue = null;
      }

      assert (AbstractRemoteInvocationServiceCaller.this.currentQueue == this);
      AbstractRemoteInvocationServiceCaller.this.currentQueue = this.parentQueue;

      // free resources
      this.callQueue = null;
      this.defaultFailureCallback = null;
      this.childQueue = null;
      this.parentQueue = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {

      requireOpen();
      if (this.childQueue != null) {
        getLogger().warn(
            "Canceling of queue" + getIdInfo() + " will also cancel open child-queue" + this.childQueue.getIdInfo()
                + ".");
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
  protected static class ServiceCallData<RESULT> {

    /** The callback to receive the service result on sucess. */
    private final Consumer<? extends RESULT> successCallback;

    /** The callback to receive a potential service failure. */
    private final Consumer<Throwable> failureCallback;

    /** The current return-type. */
    private final Class<RESULT> returnType;

    /** The current {@link RemoteInvocationService} interface. */
    private final Class<?> serviceInterface;

    /** The {@link RemoteInvocationServiceCall}. */
    private RemoteInvocationServiceCall call;

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

      super();
      this.successCallback = successCallback;
      this.failureCallback = failureCallback;
      this.returnType = returnType;
      this.serviceInterface = serviceInterface;
    }

    /**
     * @return the successCallback. See
     *         {@link RemoteInvocationServiceQueue#getServiceClient(Class, Class, Consumer, Consumer)}.
     */
    public Consumer<? extends RESULT> getSuccessCallback() {

      return this.successCallback;
    }

    /**
     * @return the failureCallback. See
     *         {@link RemoteInvocationServiceQueue#getServiceClient(Class, Class, Consumer, Consumer)}.
     */
    public Consumer<Throwable> getFailureCallback() {

      return this.failureCallback;
    }

    /**
     * @return the {@link RemoteInvocationServiceCall}.
     */
    protected RemoteInvocationServiceCall getCall() {

      return this.call;
    }

  }

}
