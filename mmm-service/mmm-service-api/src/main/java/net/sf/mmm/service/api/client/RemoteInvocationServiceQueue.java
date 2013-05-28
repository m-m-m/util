/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a queue of method-calls to one or multiple
 * {@link net.sf.mmm.service.api.RemoteInvocationService}s. It acts like a transaction that can be
 * {@link #commit() committed} or {@link #cancel() cancelled}. While the queue is {@link #isOpen() open}, the
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
   * This method determines if this queue is still open or has already been closed. A
   * {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) new queue} is always
   * open. After the first call of {@link #commit()} or {@link #cancel()} the queue is closed and can NOT be
   * opened anymore.
   * 
   * @return - <code>true</code> if this queue is still open and may be {@link #commit() committed} or
   *         {@link #cancel() cancelled}, <code>false</code> otherwise (if {@link #commit()} or
   *         {@link #cancel()} has already been performed).
   */
  boolean isOpen();

  /**
   * This method completes this queue. After the call of this method the queue can NOT be used anymore.<br/>
   * If this is the root-queue all collected invocations will be send to the server immediately. Otherwise, if
   * this is a sub-queue (a queue
   * {@link RemoteInvocationServiceCaller#newQueue(RemoteInvocationServiceQueueSettings) created} while
   * another queue was {@link RemoteInvocationServiceCaller#getCurrentQueue() already open}), the collected
   * invocations will be appended to the parent queue.<br/>
   * <b>NOTE:</b><br/>
   * Committing an empty queue is cheap and will have no effect but {@link #isOpen() closing} the queue.
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

}
