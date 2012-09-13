/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a queue of method-calls to one or multiple {@link RemoteInvocationService}s. It
 * acts like a transaction that can be {@link #commit() committed} or {@link #cancel() cancelled}. While the
 * queue is {@link #isOpen() open}, the user can
 * {@link #getServiceClient(Class, Class, RemoteInvocationServiceCallback) get a client-stub} for a
 * {@link RemoteInvocationService} and invoke a method on it. This can be repeated and will collect the
 * method-calls. In order to send these method-calls to the server, the user calls {@link #commit()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceQueue extends AttributeReadId<String> {

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
   *        {@link RemoteInvocationServiceCallback#onSuccess(java.io.Serializable, boolean) called}
   *        asynchronously when the result of the invoked {@link java.lang.reflect.Method} has been received.
   * @return the client-stub of the {@link RemoteInvocationService}.
   */
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, RemoteInvocationServiceResultCallback<? extends RESULT> callback);

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
   *        {@link RemoteInvocationServiceCallback#onSuccess(java.io.Serializable, boolean) called}
   *        asynchronously when the result of the invoked {@link java.lang.reflect.Method} has been received.
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
   * another queue was {@link RemoteInvocationServiceCaller#getCurrentQueue() already open}), the collected
   * invocations will be appended to the parent queue.
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
