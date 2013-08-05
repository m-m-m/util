/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceCallFailedException;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue.AttributeTransactionMode;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue.Settings;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue.TransactionMode;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalCalls;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalResults;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.IllegalCaseException;
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
    RemoteInvocationServiceCaller, AttributeTransactionMode {

  /** @see #nextRequestId() */
  private int requestCount;

  /** The current {@link RemoteInvocationServiceQueueImpl queue} or <code>null</code> for none. */
  private RemoteInvocationServiceQueueImpl currentQueue;

  /** @see #getTransactionMode() */
  private TransactionMode transactionMode;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCaller() {

    super();
    this.transactionMode = TransactionMode.ALL_INVOCATIONS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TransactionMode getTransactionMode() {

    return this.transactionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTransactionMode(TransactionMode transactionMode) {

    this.transactionMode = transactionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue() {

    return newQueue(new Settings());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue(String id) {

    return newQueue(new Settings(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImpl newQueue(Settings settings) {

    this.currentQueue = createQueue(settings);
    return this.currentQueue;
  }

  /**
   * This method creates a new {@link RemoteInvocationServiceQueueImpl}. If a {@link #getCurrentQueue()
   * current queue} is present, the new queue has to use it as
   * {@link RemoteInvocationServiceQueueImpl#getParentQueue() parent}.
   * 
   * @param settings are the {@link Settings} for the new queue.
   * @return the new {@link RemoteInvocationServiceQueueImpl}.
   */
  protected RemoteInvocationServiceQueueImpl createQueue(Settings settings) {

    RemoteInvocationServiceQueueImpl queue = getCurrentQueue();
    if (queue != null) {
      if ((queue.autoCommit) || queue.settings.isRejectSubQueue()) {
        throw new NlsIllegalStateException();
      }
    }
    Settings newSettings = new Settings(settings);
    TransactionMode mode = newSettings.getTransactionMode();
    if (mode == null) {
      if (queue == null) {
        mode = getTransactionMode();
      } else {
        mode = queue.getSettings().getTransactionMode();
      }
      assert (mode != null);
      newSettings.setTransactionMode(mode);
    }
    return new RemoteInvocationServiceQueueImpl(newSettings, queue);
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
    RequestBuilder builder = new RequestBuilder();
    queue.collectCalls(builder, false);
    if (builder.txCallList.isEmpty()) {
      return;
    }
    RemoteInvocationGenericServiceRequest request = builder.build(nextRequestId());
    performRequest(request, builder);
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
   * @param builder is the {@link RequestBuilder} corresponding to the given <code>request</code>.
   */
  protected abstract void performRequest(RemoteInvocationGenericServiceRequest request, RequestBuilder builder);

  /**
   * This method should be called from
   * {@link #performRequest(RemoteInvocationGenericServiceRequest, RequestBuilder)} if a
   * {@link RemoteInvocationGenericServiceResponse} has been received.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} that has been
   *        {@link #performRequest(RemoteInvocationGenericServiceRequest, RequestBuilder) performed}.
   * @param builder is the {@link RequestBuilder} corresponding to the given <code>request</code>.
   * @param response is the {@link RemoteInvocationGenericServiceResponse} to handle.
   */
  protected void handleResponse(RemoteInvocationGenericServiceRequest request, RequestBuilder builder,
      RemoteInvocationGenericServiceResponse response) {

    if (request.getRequestId() != response.getRequestId()) {
      String source = "request-ID";
      throw new ObjectMismatchException(Integer.valueOf(response.getRequestId()), Integer.valueOf(request
          .getRequestId()), source);
    }
    RemoteInvocationServiceTransactionalCalls[] transactionalCalls = request.getTransactionalCalls();
    RemoteInvocationServiceTransactionalResults[] transactionalResults = response.getTransactionalResults();
    if (transactionalResults.length != transactionalCalls.length) {
      String source = "#calls/results";
      throw new ObjectMismatchException(Integer.valueOf(transactionalResults.length),
          Integer.valueOf(transactionalCalls.length), source);
    }
    builder.handleResponse(response);
  }

  /**
   * This method should be called from
   * {@link #performRequest(RemoteInvocationGenericServiceRequest, RequestBuilder)} if a general
   * {@link Throwable failure} occurred on the client side (in case of a network error or the like).
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest} that has been
   *        {@link #performRequest(RemoteInvocationGenericServiceRequest, RequestBuilder) performed}.
   * @param builder is the {@link RequestBuilder} corresponding to the given <code>request</code>.
   * @param failure is the {@link Throwable} that has been catched on the client.
   */
  protected void handleFailure(RemoteInvocationGenericServiceRequest request, RequestBuilder builder, Throwable failure) {

    builder.handleFailure(failure);
  }

  /**
   * This inner class is the implementation of {@link RemoteInvocationServiceQueue}.
   */
  protected class RemoteInvocationServiceQueueImpl implements RemoteInvocationServiceQueue {

    /** @see #getSettings() */
    private final Settings settings;

    /** @see #getParentQueue() */
    private RemoteInvocationServiceQueueImpl parentQueue;

    /** @see #getCallQueue() */
    private List<RemoteInvocationServiceQueueImpl> subQueues;

    /** @see #getCallQueue() */
    private List<ServiceCallData<?>> callQueue;

    /** @see #getDefaultFailureCallback() */
    private Consumer<Throwable> defaultFailureCallback;

    /** The current {@link AbstractRemoteInvocationServiceCaller.ServiceCallData} or <code>null</code>. */
    private ServiceCallData<?> currentCall;

    /** @see #getState() */
    private State state;

    /** @see #getParentQueue() */
    private RemoteInvocationServiceQueueImpl childQueue;

    /** @see #addCall(RemoteInvocationServiceCall, Class) */
    private boolean autoCommit;

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     */
    public RemoteInvocationServiceQueueImpl(Settings settings) {

      this(settings, null);
    }

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationServiceQueueImpl(Settings settings, RemoteInvocationServiceQueueImpl parentQueue) {

      super();
      NlsNullPointerException.checkNotNull(Settings.class, settings);
      this.settings = settings;
      this.parentQueue = parentQueue;
      if (parentQueue != null) {
        parentQueue.childQueue = this;
      }
      this.callQueue = new LinkedList<ServiceCallData<?>>();
      this.subQueues = new LinkedList<RemoteInvocationServiceQueueImpl>();
      this.state = State.OPEN;
    }

    /**
     * This method collects the {@link RemoteInvocationServiceTransactionalCalls} for this queue recursively.
     * 
     * @param requestBuilder is the {@link RequestBuilder}.
     * @param hasOpenTransaction - <code>true</code> if a {@link RequestBuilder#beginTx() transaction has
     *        already been opened}, <code>false</code> otherwise.
     * @return <code>true</code> if a {@link RequestBuilder#beginTx() transaction is currently open},
     *         <code>false</code> otherwise.
     */
    boolean collectCalls(RequestBuilder requestBuilder, boolean hasOpenTransaction) {

      boolean openTransaction = hasOpenTransaction;
      TransactionMode mode = this.settings.getTransactionMode();
      if (!this.callQueue.isEmpty()) {
        switch (mode) {
          case ALL_INVOCATIONS:
            if (!openTransaction) {
              requestBuilder.beginTx();
              openTransaction = true;
            }
            for (ServiceCallData<?> callData : this.callQueue) {
              requestBuilder.addToCurrentTx(callData);
            }
            break;
          case PER_INVOCATION:
            if (openTransaction) {
              requestBuilder.endTx();
              openTransaction = false;
            }
            for (ServiceCallData<?> callData : this.callQueue) {
              requestBuilder.addInSingleTx(callData);
            }
            break;
          case PER_QUEUE:
            if (openTransaction) {
              requestBuilder.endTx();
              openTransaction = false;
            }
            requestBuilder.beginTx();
            for (ServiceCallData<?> callData : this.callQueue) {
              requestBuilder.addToCurrentTx(callData);
            }
            requestBuilder.endTx();
            break;
          default :
            throw new IllegalCaseException(TransactionMode.class, mode);
        }
      }
      for (RemoteInvocationServiceQueueImpl child : this.subQueues) {
        assert (child.getState() == State.COMITTED);
        openTransaction = child.collectCalls(requestBuilder, openTransaction);
      }
      if ((mode == TransactionMode.ALL_INVOCATIONS) && !hasOpenTransaction) {
        requestBuilder.endTx();
        openTransaction = false;
      }
      return openTransaction;
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
     * @return the {@link Settings} given when this queue has been
     *         {@link RemoteInvocationServiceCaller#newQueue(Settings) created}.
     */
    public Settings getSettings() {

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
     * @return an info string with the {@link Settings#getId() ID} or the empty string if not present.
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
    public State getState() {

      return this.state;
    }

    /**
     * Internal method to ensure this queue is still {@link State#OPEN open}.
     */
    protected void requireOpen() {

      if (this.state != State.OPEN) {
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
          // } else {
          // this.parentQueue.callQueue.addAll(this.callQueue);
        }
        this.state = State.COMITTED;
      } finally {
        if (this.state != State.COMITTED) {
          this.state = State.FAILED;
        }
      }
    }

    /**
     * This method closes this queue. It is called from {@link #commit()} and {@link #cancel()}.
     */
    private void close() {

      // this.open = false;

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

  /**
   * This inner calls is a builder for {@link RemoteInvocationServiceTransactionalCalls}.
   */
  protected class TransactionalCallBuilder {

    /** @see #add(ServiceCallData) */
    private final List<ServiceCallData<?>> callDataList;

    /**
     * The constructor.
     */
    public TransactionalCallBuilder() {

      super();
      this.callDataList = new LinkedList<ServiceCallData<?>>();
    }

    /**
     * @param data is the {@link ServiceCallData} to add.
     */
    public void add(ServiceCallData<?> data) {

      this.callDataList.add(data);
    }

    /**
     * @return the new {@link RemoteInvocationServiceTransactionalCalls} instance for this builder.
     */
    public RemoteInvocationServiceTransactionalCalls build() {

      RemoteInvocationServiceCall[] calls = new RemoteInvocationServiceCall[this.callDataList.size()];
      int i = 0;
      for (ServiceCallData<?> data : this.callDataList) {
        calls[i++] = data.call;
      }
      return new RemoteInvocationServiceTransactionalCalls(calls);
    }

    /**
     * Processes the given <code>results</code>.
     * 
     * @param results are the {@link RemoteInvocationServiceTransactionalResults} to handle.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    void handleResults(RemoteInvocationServiceTransactionalResults results) {

      Throwable failure = results.getFailure();
      if (failure != null) {
        for (ServiceCallData<?> callData : this.callDataList) {
          callData.failureCallback.accept(failure);
        }
      } else {
        Serializable[] resultValues = results.getResults();
        if (resultValues.length != this.callDataList.size()) {
          throw new ObjectMismatchException(Integer.valueOf(resultValues.length), Integer.valueOf(this.callDataList
              .size()), "response.transactionalResults.results.length");
        }
        int i = 0;
        for (ServiceCallData callData : this.callDataList) {
          try {
            callData.successCallback.accept(resultValues[i++]);
          } catch (RuntimeException e) {
            getLogger().error("Error processing call: " + callData.call.getTitle(), e);
            throw e;
          }
        }
      }
    }
  }

  /**
   * This inner class is a builder for {@link RemoteInvocationGenericServiceRequest}.
   */
  protected class RequestBuilder {

    /** @see #build(int) */
    private final List<TransactionalCallBuilder> txCallList;

    /** @see #addToCurrentTx(ServiceCallData) */
    private TransactionalCallBuilder currentTx;

    /**
     * The constructor.
     */
    public RequestBuilder() {

      super();
      this.txCallList = new LinkedList<TransactionalCallBuilder>();
    }

    /**
     * Processes the given <code>failure</code>.
     * 
     * @param failure is the failure that occurred.
     */
    void handleFailure(Throwable failure) {

      for (TransactionalCallBuilder txCall : this.txCallList) {
        for (ServiceCallData<?> callData : txCall.callDataList) {
          RemoteInvocationServiceCall call = callData.getCall();
          callData.failureCallback.accept(new RemoteInvocationServiceCallFailedException(failure, call
              .getServiceInterfaceName(), call.getMethodName() + call.getArguments()));
        }
      }
    }

    /**
     * Processes the given <code>response</code>.
     * 
     * @param response is the {@link RemoteInvocationGenericServiceResponse} to handle.
     */
    void handleResponse(RemoteInvocationGenericServiceResponse response) {

      RemoteInvocationServiceTransactionalResults[] transactionalResults = response.getTransactionalResults();
      if (transactionalResults.length != this.txCallList.size()) {
        throw new ObjectMismatchException(Integer.valueOf(transactionalResults.length), Integer.valueOf(this.txCallList
            .size()), "response.transactionalResults.length");
      }
      int i = 0;
      for (TransactionalCallBuilder txCall : this.txCallList) {
        RemoteInvocationServiceTransactionalResults results = transactionalResults[i++];
        txCall.handleResults(results);
      }
    }

    /**
     * Begins a new {@link RemoteInvocationServiceTransactionalCalls "transaction"}.
     */
    public void beginTx() {

      if (this.currentTx != null) {
        throw new IllegalStateException();
      }
      this.currentTx = new TransactionalCallBuilder();
    }

    /**
     * @param data is the {@link ServiceCallData} to {@link TransactionalCallBuilder#add(ServiceCallData) add}
     *        to the {@link #beginTx() current transaction}.
     */
    public void addToCurrentTx(ServiceCallData<?> data) {

      if (this.currentTx == null) {
        throw new IllegalStateException();
      }
      this.currentTx.add(data);
    }

    /**
     * @param data is the {@link ServiceCallData} to {@link TransactionalCallBuilder#add(ServiceCallData) add}
     *        as a new {@link RemoteInvocationServiceTransactionalCalls "transaction"}.
     */
    public void addInSingleTx(ServiceCallData<?> data) {

      if (this.currentTx != null) {
        throw new IllegalStateException();
      }
      TransactionalCallBuilder txCallBuilder = new TransactionalCallBuilder();
      txCallBuilder.add(data);
      this.txCallList.add(txCallBuilder);
    }

    /**
     * Ends the current {@link RemoteInvocationServiceTransactionalCalls "transaction"}.
     */
    public void endTx() {

      if (this.currentTx == null) {
        throw new IllegalStateException();
      }
      if (!this.currentTx.callDataList.isEmpty()) {
        this.txCallList.add(this.currentTx);
      }
      this.currentTx = null;
    }

    /**
     * Builds the {@link RemoteInvocationGenericServiceRequest} instance.
     * 
     * @param requestId is the {@link RemoteInvocationGenericServiceRequest#getRequestId() request ID}.
     * @return the new {@link RemoteInvocationGenericServiceRequest} instance for this builder.
     */
    public RemoteInvocationGenericServiceRequest build(int requestId) {

      RemoteInvocationServiceTransactionalCalls[] transactionalCalls = new RemoteInvocationServiceTransactionalCalls[this.txCallList
          .size()];
      int i = 0;
      for (TransactionalCallBuilder txCall : this.txCallList) {
        transactionalCalls[i++] = txCall.build();
      }
      return new RemoteInvocationGenericServiceRequest(requestId, transactionalCalls);
    }

  }

}
