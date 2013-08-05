/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a queue of method-calls to one or multiple
 * {@link net.sf.mmm.service.api.RemoteInvocationService}s. It acts like a transaction that can be
 * {@link #commit() committed} or {@link #cancel() cancelled}. While the queue is {@link State#OPEN open}, the
 * user can {@link #getServiceClient(Class, Class, java.util.function.Consumer, java.util.function.Consumer)
 * get a client-stub} for a {@link net.sf.mmm.service.api.RemoteInvocationService} and invoke a service method
 * on it. This can be repeated and will collect the method-calls. In order to send these method-calls to the
 * server use the {@link #commit()} method. This approach typically gains performance (especially reduces
 * overall latency) if multiple service methods have to be called in a row.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceQueue extends AttributeReadId<String>, AbstractRemoteInvocationServiceCaller {

  /**
   * This method sets the default callback to {@link Consumer#accept(Object) handle} failures that occurred on
   * service invocations. If such callback has been set, invocations of
   * {@link #getServiceClient(Class, Class, Consumer, Consumer)} may omit the failure callback by providing
   * <code>null</code>.
   * 
   * @param failureCallback is the default failure callback for this queue.
   */
  void setDefaultFailureCallback(Consumer<Throwable> failureCallback);

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * After the service method has been called, no technical request will be send to the server unless this
   * queue is {@link #commit() committed}. This allows multiple invocations of this method and subsequent
   * service method calls in order to collect service invocations that shall be send to the server within the
   * same technical request.
   * 
   * @see #setDefaultFailureCallback(Consumer)
   */
  @Override
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

  /**
   * {@inheritDoc}
   * 
   * It will be used for error messages, logging, etc. and will therefore help you debugging problems if you
   * specify a reasonable ID.
   * 
   * @see RemoteInvocationServiceCaller#newQueue(String)
   */
  @Override
  String getId();

  /**
   * This method gets the {@link State} of this queue. The {@link State} of a
   * {@link RemoteInvocationServiceCaller#newQueue(Settings) new queue} is initially {@link State#OPEN}. After
   * {@link #commit()} is called the status turns to {@link State#COMITTED}. After {@link #cancel()} is called
   * it turns to {@link State#CANCELLED}. As soon as the status is closed (NOT {@link State#OPEN}), it can NOT
   * be opened anymore and further calls to {@link #getServiceClient(Class, Class, Consumer, Consumer)} or
   * {@link #commit()} will fail.
   * 
   * @return the {@link State} of this queue.
   */
  State getState();

  /**
   * This method completes this queue. After the call of this method the queue can NOT be used anymore.<br/>
   * If this is the root-queue all collected invocations will be send to the server immediately on
   * {@link #commit()}. Otherwise, if this is a sub-queue (a queue
   * {@link RemoteInvocationServiceCaller#newQueue(Settings) created} while another queue was
   * {@link RemoteInvocationServiceCaller#getCurrentQueue() already open}), the collected invocations will be
   * appended to the parent queue.<br/>
   * <b>NOTE:</b><br/>
   * Committing an empty queue is cheap and will have no effect but {@link #getState() closing} the queue.
   * Therefore it is totally legal to open a queue in your generic infrastructure by default then perform some
   * sort of initialization that may or may not cause service invocations on that queue and finally commit the
   * queue.
   */
  void commit();

  /**
   * This method cancels this queue. All collected invocations will be discarded including those from
   * sub-queues. If this queue has a parent queue, the parent queue is NOT affected by this operation.<br/>
   * <b>NOTE:</b><br/>
   * This operation should only be used in very specific situations.
   */
  void cancel();

  /**
   * This enum contains the available states of a {@link RemoteInvocationServiceQueue}.
   */
  public enum State {

    /**
     * The initial state until {@link RemoteInvocationServiceQueue#commit()} or
     * {@link RemoteInvocationServiceQueue#cancel()} is called for the first time.
     */
    OPEN,

    /**
     * The state if the {@link RemoteInvocationServiceQueue} has been
     * {@link RemoteInvocationServiceQueue#commit() committed}.
     */
    COMITTED,

    /**
     * The state if the {@link RemoteInvocationServiceQueue} has been
     * {@link RemoteInvocationServiceQueue#cancel() cancelled}.
     */
    CANCELLED,

    /**
     * The state if the {@link RemoteInvocationServiceQueue} was to be
     * {@link RemoteInvocationServiceQueue#commit() committed} but this failed. This may only happen for the
     * toplevel-queue.
     */
    FAILED,

  }

  /**
   * This is a simple java bean with properties used to configure a
   * {@link RemoteInvocationServiceCaller#newQueue(Settings) new} {@link RemoteInvocationServiceQueue}.
   * 
   * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
   * @since 1.0.0
   */
  public class Settings implements AttributeTransactionMode {

    /** @see #getId() */
    private String id;

    /** @see #getTransactionMode() */
    private TransactionMode transactionMode;

    /** @see #isRejectSubQueue() */
    private boolean rejectSubQueue;

    /**
     * The constructor.
     */
    public Settings() {

      this((String) null);
    }

    /**
     * The constructor.
     * 
     * @param id - see {@link #getId()}.
     */
    public Settings(String id) {

      super();
      this.id = id;
      this.transactionMode = TransactionMode.ALL_INVOCATIONS;
    }

    /**
     * The copy-constructor.
     * 
     * @param settings2copy are the {@link Settings} to create a copy of.
     */
    public Settings(Settings settings2copy) {

      super();
      this.id = settings2copy.id;
      this.transactionMode = settings2copy.transactionMode;
      this.rejectSubQueue = settings2copy.rejectSubQueue;
    }

    /**
     * This method gets the {@link RemoteInvocationServiceQueue#getId() identifier} of the
     * {@link RemoteInvocationServiceQueue} to {@link RemoteInvocationServiceCaller#newQueue(Settings) create}
     * .
     * 
     * @see RemoteInvocationServiceQueue#getId()
     * 
     * @return the ID of the {@link RemoteInvocationServiceQueue} to
     *         {@link RemoteInvocationServiceCaller#newQueue(Settings) create} or <code>null</code> for none.
     */
    public String getId() {

      return this.id;
    }

    /**
     * Sets the {@link #getId() ID}.
     * 
     * @param id is the ID for the {@link RemoteInvocationServiceQueue} to
     *        {@link RemoteInvocationServiceCaller#newQueue(Settings) create}.
     */
    public void setId(String id) {

      this.id = id;
    }

    /**
     * This method gets the {@link TransactionMode} configuring the transactional behavior of the
     * {@link RemoteInvocationServiceQueue} on the server side. The default is <code>null</code> what will
     * inherit the {@link TransactionMode} from the parent-queue or for the root-queue from the
     * {@link RemoteInvocationServiceCaller}. The default of {@link RemoteInvocationServiceCaller} for
     * {@link TransactionMode} is {@link TransactionMode#ALL_INVOCATIONS}. You may change the default (during
     * the startup of the client) by casting {@link RemoteInvocationServiceCaller} to
     * {@link AttributeTransactionMode}.
     * 
     * @return the {@link TransactionMode} or <code>null</code> to inherit.
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
     * @return <code>true</code> if the creating of sub-queues shall be prohibited (by an exception),
     *         <code>false</code> otherwise (default).
     */
    public boolean isRejectSubQueue() {

      return this.rejectSubQueue;
    }

    /**
     * @param rejectNestedInvocation is the new value for {@link #isRejectSubQueue()}.
     */
    public void setRejectSubQueue(boolean rejectNestedInvocation) {

      this.rejectSubQueue = rejectNestedInvocation;
    }

  }

  /**
   * This enum contains the available mode for the transactional behavior of a
   * {@link RemoteInvocationServiceQueue}.
   * 
   * @see Settings#getTransactionMode()
   */
  public enum TransactionMode {

    /**
     * Process all invocations to a {@link net.sf.mmm.service.api.RemoteInvocationService} collected in the
     * configured queue and recursively in all sub-queues with the same {@link TransactionMode} in a single
     * transaction on the server. However, sub-queues with a different {@link TransactionMode} will create
     * their own transaction(s).
     */
    ALL_INVOCATIONS,

    /**
     * Process each invocation to a {@link net.sf.mmm.service.api.RemoteInvocationService} in a separate
     * transaction on the server.
     */
    PER_INVOCATION,

    /**
     * Process all invocations to a {@link net.sf.mmm.service.api.RemoteInvocationService} collected in the
     * configured queue in a single transaction on the server. However sub-queues will be processed in
     * separate transactions according to their {@link TransactionMode}.
     * 
     */
    PER_QUEUE
  }

  /**
   * The interface for a {@link net.sf.mmm.util.pojo.api.Pojo} with the attribute/property
   * {@link #getTransactionMode() transactionMode}.
   */
  public interface AttributeTransactionMode {

    /**
     * This method gets the {@link TransactionMode}.
     * 
     * @return the {@link TransactionMode}.
     */
    TransactionMode getTransactionMode();

    /**
     * @param transactionMode is the new value of {@link #getTransactionMode()}.
     */
    void setTransactionMode(TransactionMode transactionMode);

  }

}
