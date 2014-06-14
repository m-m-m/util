/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.RemoteInvocationCall;
import net.sf.mmm.service.api.RemoteInvocationCallFailedException;
import net.sf.mmm.service.api.client.AttributeWriteDefaultFailureCallback;
import net.sf.mmm.service.api.client.AttributeWriteTransactionMode;
import net.sf.mmm.service.api.client.RemoteInvocationCaller;
import net.sf.mmm.service.api.client.RemoteInvocationQueue;
import net.sf.mmm.service.api.client.RemoteInvocationQueueSettings;
import net.sf.mmm.service.api.client.RemoteInvocationQueueState;
import net.sf.mmm.service.api.client.TransactionMode;
import net.sf.mmm.service.base.GenericRemoteInvocationRequest;
import net.sf.mmm.service.base.GenericRemoteInvocationResponse;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This is the abstract base-implementation of {@link RemoteInvocationCaller}.
 *
 * @param <QUEUE> is the generic type of the {@link RemoteInvocationQueue queue}. The queue implementation
 *        must extend {@link AbstractRemoteInvocationQueue}.
 * @param <CALL> is the generic type of the remote invocation call. For command style this is just the
 *        {@link net.sf.mmm.service.api.command.RemoteInvocationCommand} itself, for RPC style this is
 *        {@link net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall}.
 * @param <TX_CALLS> is the generic type of the transfer object that wraps a {@literal <CALL>}-array with the
 *        calls to perform in a single transaction.
 * @param <REQUEST> is the generic type of the {@link GenericRemoteInvocationRequest} to build and send to the
 *        server.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationCaller<QUEUE extends RemoteInvocationQueue, CALL extends RemoteInvocationCall, //
TX_CALLS extends GenericRemoteInvocationTransactionalCalls<CALL>, REQUEST extends GenericRemoteInvocationRequest<CALL, TX_CALLS>>
    extends AbstractLoggableComponent implements RemoteInvocationCaller<QUEUE>, AttributeWriteTransactionMode,
    AttributeWriteDefaultFailureCallback {

  /** @see #nextRequestId() */
  private int requestCount;

  /** The current {@link AbstractRemoteInvocationQueue queue} or <code>null</code> for none. */
  private QUEUE currentQueue;

  /** @see #getTransactionMode() */
  private TransactionMode transactionMode;

  /** @see #getDefaultFailureCallback() */
  private Consumer<Throwable> defaultFailureCallback;

  /** @see #getXsrfToken() */
  private CsrfToken xsrfToken;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationCaller() {

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

    getInitializationState().requireNotInitilized();
    this.transactionMode = transactionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Consumer<Throwable> getDefaultFailureCallback() {

    return this.defaultFailureCallback;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultFailureCallback(Consumer<Throwable> failureCallback) {

    getInitializationState().requireNotInitilized();
    this.defaultFailureCallback = failureCallback;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QUEUE newQueue() {

    return newQueue(new RemoteInvocationQueueSettings());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QUEUE newQueue(String id) {

    return newQueue(new RemoteInvocationQueueSettings(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QUEUE newQueue(RemoteInvocationQueueSettings settings) {

    this.currentQueue = createQueue(settings);
    return this.currentQueue;
  }

  /**
   * @return creates a new queue for {@link AbstractRemoteInvocationQueue#autoCommit auto-commit}.
   */
  @SuppressWarnings("unchecked")
  protected QUEUE newQueueForAutoCommit() {

    QUEUE queue = newQueue(new RemoteInvocationQueueSettings("auto-commit"));
    ((AbstractRemoteInvocationQueue) queue).autoCommit = true;
    return queue;
  }

  /**
   * This method creates a new {@link AbstractRemoteInvocationQueue}. If a {@link #getCurrentQueue() current
   * queue} is present, the new queue has to use it as {@link AbstractRemoteInvocationQueue#getParentQueue()
   * parent}.
   *
   * @param settings are the {@link RemoteInvocationQueueSettings} for the new queue.
   * @return the new {@link AbstractRemoteInvocationQueue}.
   */
  protected QUEUE createQueue(RemoteInvocationQueueSettings settings) {

    @SuppressWarnings("unchecked")
    AbstractRemoteInvocationQueue queue = (AbstractRemoteInvocationQueue) getCurrentQueue();
    if (queue != null) {
      if ((queue.autoCommit) || queue.settings.isRejectSubQueue()) {
        throw new NlsIllegalStateException();
      }
    }
    RemoteInvocationQueueSettings newSettings = new RemoteInvocationQueueSettings(settings);
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
    return createQueue(newSettings, queue);
  }

  /**
   * @see #createQueue(RemoteInvocationQueueSettings)
   *
   * @param settings are the {@link RemoteInvocationQueueSettings}.
   * @param parentQueue is the parent queue.
   * @return the new queue instance.
   */
  protected abstract QUEUE createQueue(RemoteInvocationQueueSettings settings, AbstractRemoteInvocationQueue parentQueue);

  /**
   * {@inheritDoc}
   */
  @Override
  public QUEUE getCurrentQueue() {

    return this.currentQueue;
  }

  /**
   * @return the next {@link GenericRemoteInvocationRequest#getRequestId() request ID}.
   */
  protected int nextRequestId() {

    return this.requestCount++;
  }

  /**
   * @return the current {@link AbstractRemoteInvocationQueue}.
   */
  protected QUEUE requireCurrentQueue() {

    if (this.currentQueue == null) {
      throw new IllegalStateException("No open queue!");
    }
    return this.currentQueue;
  }

  /**
   * @return the last received {@link CsrfToken} or <code>null</code> if not available.
   */
  public CsrfToken getXsrfToken() {

    return this.xsrfToken;
  }

  /**
   * This method finally performs a request with the invocations collected by the given <code>queue</code>.
   *
   * @param queue is the {@link AbstractRemoteInvocationQueue}.
   */
  protected void performRequest(AbstractRemoteInvocationQueue queue) {

    assert (queue == this.currentQueue);
    RequestBuilder builder = new RequestBuilder();
    queue.collectCalls(builder, false);
    if (builder.txCallList.isEmpty()) {
      return;
    }
    REQUEST request = builder.build(nextRequestId());
    performRequest(request, builder);
  }

  /**
   * Actually performs the given <code>request</code> by sending it to the server and handling the response
   * asynchronously.
   *
   * @param request is the {@link GenericRemoteInvocationRequest} to perform.
   * @param builder is the {@link RequestBuilder} that created the request and is to be used for handling the
   *        results (dispatching to the asynchronous callback functions).
   */
  protected abstract void performRequest(REQUEST request, RequestBuilder builder);

  /**
   * This method should be called from {@link #performRequest(GenericRemoteInvocationRequest, RequestBuilder)}
   * if a {@link GenericRemoteInvocationResponse} has been received.
   *
   * @param request is the {@link GenericRemoteInvocationRequest} that has been
   *        {@link #performRequest(GenericRemoteInvocationRequest, RequestBuilder) performed}.
   * @param builder is the {@link RequestBuilder} corresponding to the given <code>request</code>.
   * @param response is the {@link GenericRemoteInvocationResponse} to handle.
   */
  protected void handleResponse(REQUEST request, RequestBuilder builder, GenericRemoteInvocationResponse response) {

    if (request.getRequestId() != response.getRequestId()) {
      String source = "request-ID";
      throw new ObjectMismatchException(Integer.valueOf(response.getRequestId()), Integer.valueOf(request
          .getRequestId()), source);
    }
    TX_CALLS[] transactionalCalls = request.getTransactionalCalls();
    GenericRemoteInvocationTransactionalResults[] transactionalResults = response.getTransactionalResults();
    if (transactionalResults.length != transactionalCalls.length) {
      String source = "#calls/results";
      throw new ObjectMismatchException(Integer.valueOf(transactionalResults.length),
          Integer.valueOf(transactionalCalls.length), source);
    }
    CsrfToken token = response.getXsrfToken();
    if (token != null) {
      getLogger().debug("Received new XSRF token.");
      if (this.xsrfToken != null) {
        getLogger().debug("Replacing existing XSRF token.");
      }
      this.xsrfToken = token;
    }
    builder.handleResponse(response);
  }

  /**
   * This method should be called from {@link #performRequest(GenericRemoteInvocationRequest, RequestBuilder)}
   * if a general {@link Throwable failure} occurred on the client side (in case of a network error or the
   * like).
   *
   * @param request is the {@link GenericRemoteInvocationRequest} that has been
   *        {@link #performRequest(GenericRemoteInvocationRequest, RequestBuilder) performed}.
   * @param builder is the {@link RequestBuilder} corresponding to the given <code>request</code>.
   * @param failure is the {@link Throwable} that has been catched on the client.
   */
  protected void handleFailure(REQUEST request, RequestBuilder builder, Throwable failure) {

    builder.handleFailure(failure);
  }

  /**
   * This is the abstract base implementation of {@link RemoteInvocationQueue}.
   */
  protected abstract class AbstractRemoteInvocationQueue implements RemoteInvocationQueue {

    /** @see #getSettings() */
    private final RemoteInvocationQueueSettings settings;

    /** @see #getParentQueue() */
    private AbstractRemoteInvocationQueue parentQueue;

    /** @see #getCallQueue() */
    private List<AbstractRemoteInvocationQueue> subQueues;

    /** @see #getCallQueue() */
    private List<RemoteInvocationCallData<?, CALL>> callQueue;

    /** @see #getDefaultFailureCallback() */
    private Consumer<Throwable> queueFailureCallback;

    // /** The current {@link AbstractRemoteInvocationCaller.RemoteInvocationCallData} or <code>null</code>.
    // */
    // private RemoteInvocationCallData<?, CALL> currentCall;

    /** @see #getState() */
    private RemoteInvocationQueueState state;

    /** @see #getParentQueue() */
    private AbstractRemoteInvocationQueue childQueue;

    /** @see #addCall(RemoteInvocationCallData) */
    private boolean autoCommit;

    /**
     * The constructor.
     *
     * @param settings - see {@link #getSettings()}.
     */
    public AbstractRemoteInvocationQueue(RemoteInvocationQueueSettings settings) {

      this(settings, null);
    }

    /**
     * The constructor.
     *
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public AbstractRemoteInvocationQueue(RemoteInvocationQueueSettings settings,
        AbstractRemoteInvocationQueue parentQueue) {

      super();
      NlsNullPointerException.checkNotNull(RemoteInvocationQueueSettings.class, settings);
      this.settings = settings;
      this.parentQueue = parentQueue;
      if (parentQueue != null) {
        parentQueue.childQueue = this;
      }
      this.callQueue = new LinkedList<RemoteInvocationCallData<?, CALL>>();
      this.subQueues = new LinkedList<AbstractRemoteInvocationQueue>();
      this.state = RemoteInvocationQueueState.OPEN;
    }

    /**
     * This method collects the {@link GenericRemoteInvocationTransactionalCalls} for this queue recursively.
     *
     * @param requestBuilder is the {@link RequestBuilder}.
     * @param hasOpenTransaction - <code>true</code> if a
     *        {@link AbstractRemoteInvocationCaller.RequestBuilder#beginTx() transaction has already been
     *        opened}, <code>false</code> otherwise.
     * @return <code>true</code> if a {@link AbstractRemoteInvocationCaller.RequestBuilder#beginTx()
     *         transaction is currently open}, <code>false</code> otherwise.
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
            for (RemoteInvocationCallData<?, CALL> callData : this.callQueue) {
              requestBuilder.addToCurrentTx(callData);
            }
            break;
          case PER_INVOCATION:
            if (openTransaction) {
              requestBuilder.endTx();
              openTransaction = false;
            }
            for (RemoteInvocationCallData<?, CALL> callData : this.callQueue) {
              requestBuilder.addInSingleTx(callData);
            }
            break;
          case PER_QUEUE:
            if (openTransaction) {
              requestBuilder.endTx();
              openTransaction = false;
            }
            requestBuilder.beginTx();
            for (RemoteInvocationCallData<?, CALL> callData : this.callQueue) {
              requestBuilder.addToCurrentTx(callData);
            }
            requestBuilder.endTx();
            break;
          default :
            throw new IllegalCaseException(TransactionMode.class, mode);
        }
      }
      for (AbstractRemoteInvocationQueue child : this.subQueues) {
        assert (child.getState() == RemoteInvocationQueueState.COMITTED);
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

      this.queueFailureCallback = failureCallback;
    }

    /**
     * @return the {@link #setDefaultFailureCallback(Consumer) default failure-callback}.
     */
    public Consumer<Throwable> getDefaultFailureCallback() {

      if (this.queueFailureCallback == null) {
        if (this.parentQueue == null) {
          AbstractRemoteInvocationCaller.this.getDefaultFailureCallback();
        } else {
          return this.parentQueue.getDefaultFailureCallback();
        }
      }
      return this.queueFailureCallback;
    }

    /**
     * @return the callQueue
     */
    public List<RemoteInvocationCallData<?, CALL>> getCallQueue() {

      return this.callQueue;
    }

    /**
     * This method adds the given {@link RemoteInvocationCallData} to this queue.
     *
     * @param callData is the {@link RemoteInvocationCallData} to add.
     */
    protected void addCall(RemoteInvocationCallData<?, CALL> callData) {

      this.callQueue.add(callData);
      if (this.autoCommit) {
        commit();
      }
    }

    /**
     * @return the {@link RemoteInvocationQueueSettings} given when this queue has been
     *         {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) created}.
     */
    public RemoteInvocationQueueSettings getSettings() {

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
     * @return an info string with the {@link RemoteInvocationQueueSettings#getId() ID} or the empty string if
     *         not present.
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
    public AbstractRemoteInvocationQueue getParentQueue() {

      return this.parentQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RemoteInvocationQueueState getState() {

      return this.state;
    }

    /**
     * Internal method to ensure this queue is still
     * {@link net.sf.mmm.service.api.client.RemoteInvocationQueueState#OPEN open}.
     */
    protected void requireOpen() {

      if (this.state != RemoteInvocationQueueState.OPEN) {
        throw new IllegalStateException("Queue not open!");
      }
    }

    /**
     * Internal method to ensure that no current call is pending.
     */
    protected void requireNoCurrentCall() {

      // if (this.currentCall != null) {
      // throw new IllegalStateException("Pending call " + this.currentCall.toString() + " not completed.");
      // }
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
        this.state = RemoteInvocationQueueState.COMITTED;
      } finally {
        if (this.state != RemoteInvocationQueueState.COMITTED) {
          this.state = RemoteInvocationQueueState.FAILED;
        }
      }
    }

    /**
     * This method closes this queue. It is called from {@link #commit()} and {@link #cancel()}.
     */
    @SuppressWarnings("unchecked")
    private void close() {

      // disconnect from parent
      if (this.parentQueue != null) {
        assert (this.parentQueue.childQueue == this);
        this.parentQueue.childQueue = null;
      }

      assert (AbstractRemoteInvocationCaller.this.currentQueue == this);
      AbstractRemoteInvocationCaller.this.currentQueue = (QUEUE) this.parentQueue;

      // free resources
      this.callQueue = null;
      this.queueFailureCallback = null;
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
      // this.currentCall = null;
      close();
    }

  }

  /**
   * This inner calls is a builder for {@link GenericRemoteInvocationTransactionalCalls}.
   */
  protected class TransactionalCallBuilder {

    /** @see #add(RemoteInvocationCallData) */
    private final List<RemoteInvocationCallData<?, CALL>> callDataList;

    /**
     * The constructor.
     */
    public TransactionalCallBuilder() {

      super();
      this.callDataList = new LinkedList<RemoteInvocationCallData<?, CALL>>();
    }

    /**
     * @param data is the {@link RemoteInvocationCallData} to add.
     */
    public void add(RemoteInvocationCallData<?, CALL> data) {

      this.callDataList.add(data);
    }

    /**
     * @return the new {@link GenericRemoteInvocationTransactionalCalls} instance for this builder.
     */
    public TX_CALLS build() {

      List<CALL> calls = new ArrayList<>(this.callDataList.size());
      for (RemoteInvocationCallData<?, CALL> data : this.callDataList) {
        calls.add(data.getCall());
      }
      return createRemoteInvocationTransactionalCalls(calls);
    }

    /**
     * Processes the given <code>results</code>.
     *
     * @param results are the {@link GenericRemoteInvocationTransactionalResults} to handle.
     */
    @SuppressWarnings({ "rawtypes" })
    void handleResults(GenericRemoteInvocationTransactionalResults results) {

      Throwable failure = results.getFailure();
      if (failure != null) {
        for (RemoteInvocationCallData<?, CALL> callData : this.callDataList) {
          callData.getFailureCallback().accept(failure);
        }
      } else {
        Serializable[] resultValues = results.getResults();
        if (resultValues.length != this.callDataList.size()) {
          throw new ObjectMismatchException(Integer.valueOf(resultValues.length), Integer.valueOf(this.callDataList
              .size()), "response.transactionalResults.results.length");
        }
        int i = 0;
        for (RemoteInvocationCallData callData : this.callDataList) {
          try {
            callData.getSuccessCallback().accept(resultValues[i++]);
          } catch (RuntimeException e) {
            getLogger().error("Error processing call: {0}", callData.getCall(), e);
            throw e;
          }
        }
      }
    }
  }

  /**
   * This class is a builder for {@link GenericRemoteInvocationRequest}s.
   */
  protected class RequestBuilder {

    /** @see #build(int) */
    private final List<TransactionalCallBuilder> txCallList;

    /** @see #addToCurrentTx(RemoteInvocationCallData) */
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
        for (RemoteInvocationCallData<?, CALL> callData : txCall.callDataList) {
          CALL call = callData.getCall();
          RemoteInvocationCallFailedException exception = new RemoteInvocationCallFailedException(failure, call);
          callData.getFailureCallback().accept(exception);
        }
      }
    }

    /**
     * Processes the given <code>response</code>.
     *
     * @param response is the {@link GenericRemoteInvocationResponse} to handle.
     */
    void handleResponse(GenericRemoteInvocationResponse response) {

      GenericRemoteInvocationTransactionalResults[] transactionalResults = response.getTransactionalResults();
      if (transactionalResults.length != this.txCallList.size()) {
        throw new ObjectMismatchException(Integer.valueOf(transactionalResults.length), Integer.valueOf(this.txCallList
            .size()), "response.transactionalResults.length");
      }
      int i = 0;
      for (TransactionalCallBuilder txCall : this.txCallList) {
        GenericRemoteInvocationTransactionalResults results = transactionalResults[i++];
        txCall.handleResults(results);
      }
    }

    /**
     * Begins a new {@link GenericRemoteInvocationTransactionalCalls "transaction"}.
     */
    public void beginTx() {

      if (this.currentTx != null) {
        throw new IllegalStateException();
      }
      this.currentTx = new TransactionalCallBuilder();
    }

    /**
     * @param data is the {@link RemoteInvocationCallData} to
     *        {@link AbstractRemoteInvocationCaller.TransactionalCallBuilder#add(RemoteInvocationCallData)
     *        add} to the {@link #beginTx() current transaction}.
     */
    public void addToCurrentTx(RemoteInvocationCallData<?, CALL> data) {

      if (this.currentTx == null) {
        throw new IllegalStateException();
      }
      this.currentTx.add(data);
    }

    /**
     * @param data is the {@link RemoteInvocationCallData} to
     *        {@link AbstractRemoteInvocationCaller.TransactionalCallBuilder#add(RemoteInvocationCallData)
     *        add} as a new {@link GenericRemoteInvocationTransactionalCalls "transaction"}.
     */
    public void addInSingleTx(RemoteInvocationCallData<?, CALL> data) {

      if (this.currentTx != null) {
        throw new IllegalStateException();
      }
      TransactionalCallBuilder txCallBuilder = new TransactionalCallBuilder();
      txCallBuilder.add(data);
      this.txCallList.add(txCallBuilder);
    }

    /**
     * Ends the current {@link GenericRemoteInvocationTransactionalCalls "transaction"}.
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
     * Builds the {@link GenericRemoteInvocationRequest} instance.
     *
     * @param requestId is the {@link GenericRemoteInvocationRequest#getRequestId() request ID}.
     * @return the new {@link GenericRemoteInvocationRequest} instance for this builder.
     */
    public REQUEST build(int requestId) {

      List<TX_CALLS> transactionalCalls = new ArrayList<>(this.txCallList.size());
      for (TransactionalCallBuilder txCall : this.txCallList) {
        transactionalCalls.add(txCall.build());
      }
      return createRequest(requestId, getXsrfToken(), transactionalCalls);
    }

  }

  /**
   * @param calls is the {@link List} with the {@literal <CALL>}s.
   * @return an instance of {@literal <TX_CALLS>} wrapping the given <code>calls</code>.
   */
  protected abstract TX_CALLS createRemoteInvocationTransactionalCalls(List<CALL> calls);

  /**
   * Creates a new {@link GenericRemoteInvocationRequest} instance.
   *
   * @param requestId - see {@link GenericRemoteInvocationRequest#getRequestId()}.
   * @param token - see {@link GenericRemoteInvocationRequest#getXsrfToken()}.
   * @param transactionalCalls - see {@link GenericRemoteInvocationRequest#getTransactionalCalls()}.
   * @return the new request.
   */
  protected abstract REQUEST createRequest(int requestId, CsrfToken token, List<TX_CALLS> transactionalCalls);

}
