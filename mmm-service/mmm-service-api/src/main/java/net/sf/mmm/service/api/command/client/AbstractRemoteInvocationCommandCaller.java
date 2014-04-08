/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.command.client;

import java.io.Serializable;
import java.util.function.Consumer;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;

/**
 * This is the abstract interface to call a {@link RemoteInvocationCommand} from the client.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AbstractRemoteInvocationCommandCaller {

  /**
   * This method invokes the given {@link RemoteInvocationCommand} on a
   * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}.
   * 
   * @param <RESULT> is the generic type of <code>returnType</code>.
   * @param command is the {@link RemoteInvocationCommand} to invoke.
   * @param successCallback is the {@link Consumer} that is asynchronously {@link Consumer#accept(Object)
   *        invoked} on success with when the result of the invoked service {@link java.lang.reflect.Method}
   *        has been received. The generic type may extend result if it is generic itself. E.g. your service
   *        might return <code>List&lt;String&gt;</code> but you can only supply <code>List.class</code> as
   *        return type.
   * @param failureCallback is the {@link Consumer} that is asynchronously {@link Consumer#accept(Object)
   *        invoked} on failure with the {@link Throwable} that occurred when calling the invoked service
   *        {@link java.lang.reflect.Method}.
   */
  <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

  /**
   * Same as {@link #callCommand(RemoteInvocationCommand, Consumer)} but using the
   * {@link net.sf.mmm.service.api.client.RemoteInvocationQueue#setDefaultFailureCallback(Consumer)
   * default failure callback}.
   * 
   * @param <RESULT> is the generic type of <code>returnType</code>.
   * @param command is the {@link RemoteInvocationCommand} to invoke.
   * @param successCallback is the {@link Consumer} that is asynchronously {@link Consumer#accept(Object)
   *        invoked} on success with when the result of the invoked service {@link java.lang.reflect.Method}
   *        has been received. The generic type may extend result if it is generic itself. E.g. your service
   *        might return <code>List&lt;String&gt;</code> but you can only supply <code>List.class</code> as
   *        return type.
   */
  <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback);

}
