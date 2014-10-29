/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This is the abstract interface for a queue of method-calls to one or multiple
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}s. It acts like a transaction that can be
 * {@link #commit() committed} or {@link #cancel() cancelled}. While the queue is
 * {@link RemoteInvocationQueueState#OPEN open} , the user can add invocations for a
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService} to the queue. This can be repeated and will
 * collect the service invocations. In order to send these invocations to the server use the {@link #commit()}
 * method. This approach typically gains performance (especially reduces overall latency) if multiple service
 * methods have to be called in a row.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface RemoteInvocationQueue extends AttributeReadId<String>, AttributeWriteDefaultFailureCallback {

  /**
   * {@inheritDoc}
   *
   * It will be used as fallback if no failure callback is specified explicitly. {@link RemoteInvocationQueue
   * Queues} will inherit the default failure callback from their parent. A toplevel queue inherits from the
   * {@link RemoteInvocationCaller invocation caller}.
   */
  @Override
  Consumer<Throwable> getDefaultFailureCallback();

  /**
   * {@inheritDoc}
   *
   * It will be used for error messages, logging, etc. and will therefore help you debugging problems if you
   * specify a reasonable ID.
   *
   * @see RemoteInvocationCaller#newQueue(String)
   */
  @Override
  String getId();

  /**
   * This method gets the {@link RemoteInvocationQueueState} of this queue. The
   * {@link RemoteInvocationQueueState} of a
   * {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) new queue} is initially
   * {@link RemoteInvocationQueueState#OPEN}. After {@link #commit()} is called the status turns to
   * {@link RemoteInvocationQueueState#COMITTED}. After {@link #cancel()} is called it turns to
   * {@link RemoteInvocationQueueState#CANCELLED}. As soon as the status is closed (NOT
   * {@link RemoteInvocationQueueState#OPEN}), it can NOT be opened anymore and further adding of service
   * invocations to the queue will fail.
   *
   * @return the {@link RemoteInvocationQueueState} of this queue.
   */
  RemoteInvocationQueueState getState();

  /**
   * This method completes this queue. After the call of this method the queue can NOT be used anymore. <br>
   * If this is the root-queue all collected invocations will be send to the server immediately on
   * {@link #commit()}. Otherwise, if this is a sub-queue (a queue
   * {@link RemoteInvocationCaller#newQueue(RemoteInvocationQueueSettings) created} while another queue was
   * {@link RemoteInvocationCaller#getCurrentQueue() already open}), the collected invocations will be
   * appended to the parent queue. <br>
   * <b>NOTE:</b><br>
   * Committing an empty queue is cheap and will have no effect but {@link #getState() closing} the queue.
   * Therefore it is totally legal to open a queue in your generic infrastructure by default then perform some
   * sort of initialization that may or may not cause service invocations on that queue and finally commit the
   * queue.
   */
  void commit();

  /**
   * This method cancels this queue. All collected invocations will be discarded including those from
   * sub-queues. If this queue has a parent queue, the parent queue is NOT affected by this operation. <br>
   * <b>NOTE:</b><br>
   * This operation should only be used in very specific situations.
   */
  void cancel();

}
