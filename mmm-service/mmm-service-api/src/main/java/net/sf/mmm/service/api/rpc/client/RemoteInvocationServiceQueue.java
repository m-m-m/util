/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.rpc.client;

import java.util.function.Consumer;

import net.sf.mmm.service.api.client.RemoteInvocationQueue;
import net.sf.mmm.service.api.rpc.RemoteInvocationService;

/**
 * This is the interface for a {@link RemoteInvocationQueue remote invocation queue} based on
 * {@link RemoteInvocationServiceCaller}.
 * 
 * @see RemoteInvocationQueue
 * @see #getServiceClient(Class, Class, Consumer, Consumer)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceQueue extends RemoteInvocationQueue,
    AbstractRemoteInvocationServiceCaller {

  /**
   * {@inheritDoc}
   * 
   * If such callback has been set, invocations of {@link #getServiceClient(Class, Class, Consumer, Consumer)}
   * may omit the failure callback by providing <code>null</code>.
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
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback);

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * After the service method has been called, no technical request will be send to the server unless this
   * queue is {@link #commit() committed}. This allows multiple invocations of this method and subsequent
   * service method calls in order to collect service invocations that shall be send to the server within the
   * same technical request.
   */
  @Override
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

}
