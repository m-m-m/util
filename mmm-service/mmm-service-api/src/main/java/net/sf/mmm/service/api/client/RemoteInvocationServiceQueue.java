/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.service.api.RemoteInvocationService;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceQueue {

  /**
   * This method gets a client-stub for calling exactly <em>one single method</em> on a
   * {@link RemoteInvocationService}. After this method has been called, the intended method (with the given
   * <code>returnType</code>) has to be invoked on the resulting client-stub exactly once. This records the
   * desired method invocation and returns a dummy result (typically <code>null</code>) that shall be ignored.
   * After that, this method may be called again in order to collect an additional invocation that shall be
   * send to the server within the same technical request or the queue can be {@link #commit() committed} (or
   * {@link #cancel() cancelled}).
   * 
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param <RESULT> is the generic type of <code>returnType</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the
   *        {@link java.lang.reflect.Method} to invoke.
   * @param callback is the {@link RemoteInvocationServiceCallback} that will be
   *        {@link RemoteInvocationServiceCallback#onSuccess(Object, net.sf.mmm.service.api.RemoteInvocationServiceContext, boolean)
   *        called} asynchronously when the result of the invoked {@link java.lang.reflect.Method} has been
   *        received.
   * @return the client-stub of the {@link RemoteInvocationService}.
   */
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, RemoteInvocationServiceCallback<? extends RESULT> callback);

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
   * another queue was already open), the collected invocations will be appended to the parent queue.
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
