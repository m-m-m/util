/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;


/**
 * This is a simple java bean with properties used to configure a
 * {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) new}
 * {@link RemoteInvocationQueue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationQueueSettings implements AttributeWriteTransactionMode {

  /** @see #getId() */
  private String id;

  /** @see #getTransactionMode() */
  private TransactionMode transactionMode;

  /** @see #isRejectSubQueue() */
  private boolean rejectSubQueue;

  /**
   * The constructor.
   */
  public RemoteInvocationQueueSettings() {

    this((String) null);
  }

  /**
   * The constructor.
   * 
   * @param id - see {@link #getId()}.
   */
  public RemoteInvocationQueueSettings(String id) {

    super();
    this.id = id;
    this.transactionMode = TransactionMode.ALL_INVOCATIONS;
  }

  /**
   * The copy-constructor.
   * 
   * @param settings2copy are the {@link RemoteInvocationQueueSettings} to create a copy of.
   */
  public RemoteInvocationQueueSettings(RemoteInvocationQueueSettings settings2copy) {

    super();
    this.id = settings2copy.id;
    this.transactionMode = settings2copy.transactionMode;
    this.rejectSubQueue = settings2copy.rejectSubQueue;
  }

  /**
   * This method gets the {@link RemoteInvocationQueue#getId() identifier} of the
   * {@link RemoteInvocationCaller} to
   * {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) create}.
   * 
   * @see RemoteInvocationQueue#getId()
   * 
   * @return the ID of the {@link RemoteInvocationQueue} to
   *         {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) create} or
   *         <code>null</code> for none.
   */
  public String getId() {

    return this.id;
  }

  /**
   * Sets the {@link #getId() ID}.
   * 
   * @param id is the ID for the {@link RemoteInvocationQueue} to
   *        {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) create}.
   */
  public void setId(String id) {

    this.id = id;
  }

  /**
   * This method gets the {@link TransactionMode} configuring the transactional behavior of the
   * {@link RemoteInvocationQueue} on the server side. The default is <code>null</code> what will
   * inherit the {@link TransactionMode} from the parent-queue or for the root-queue from the
   * {@link RemoteInvocationCaller}. The default of {@link RemoteInvocationCaller} for
   * {@link TransactionMode} is {@link TransactionMode#ALL_INVOCATIONS}. You may change the default (during
   * the startup of the client) by casting {@link RemoteInvocationCaller} to
   * {@link AttributeWriteTransactionMode}.
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
