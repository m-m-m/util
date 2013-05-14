/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.util.function.Consumer;

import net.sf.mmm.service.api.RemoteInvocationService;

/**
 * This is the abstract interface to call a {@link RemoteInvocationService} from the client.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AbstractRemoteInvocationServiceCaller {

  /**
   * This method gets a client-stub for calling exactly <em>one single method</em> on a
   * {@link RemoteInvocationService}. After this method has been called, the intended method (with the given
   * <code>returnType</code>) has to be invoked on the resulting client-stub exactly once. This records the
   * desired method invocation and returns a dummy result (typically <code>null</code>) that shall be ignored.
   * 
   * @param <SERVICE> is the generic type of <code>serviceInterface</code>.
   * @param <RESULT> is the generic type of <code>returnType</code>.
   * @param serviceInterface is the interface of the {@link RemoteInvocationService}.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the
   *        {@link java.lang.reflect.Method} to invoke.
   * @param successCallback is the {@link Consumer} that is asynchronously {@link Consumer#accept(Object)
   *        invoked} on success with when the result of the invoked service {@link java.lang.reflect.Method}
   *        has been received. The generic type may extend result if it is generic itself. E.g. your service
   *        might return <code>List&lt;String&gt;</code> but you can only supply <code>List.class</code> as
   *        return type.
   * @param failureCallback is the {@link Consumer} that is asynchronously {@link Consumer#accept(Object)
   *        invoked} on failure with the {@link Throwable} that occurred when calling the invoked service
   *        {@link java.lang.reflect.Method}.
   * @return the client-stub of the {@link RemoteInvocationService}.
   */
  <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
      Class<RESULT> returnType, Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

}
