/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationService;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the GWT specific variant of {@link RemoteInvocationServiceQueue}. It allows to use
 * {@link AsyncCallback} as alternative to
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCallback}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceQueueGwt extends RemoteInvocationServiceQueue {

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
   *        {@link RemoteInvocationServiceCallback#onSuccess(Serializable, boolean) called} asynchronously
   *        when the result of the invoked {@link java.lang.reflect.Method} has been received.
   * @return the client-stub of the {@link RemoteInvocationService}.
   */
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, AsyncCallback<? extends RESULT> callback);

}
