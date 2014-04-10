/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.RemoteInvocationCall;
import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This is a simple container for the data corresponding to a {@link RemoteInvocationCall}.
 *
 * @param <RESULT> is the generic type of the method return-type.
 * @param <CALL> is the generic type of the {@link #getCall() call} data.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationCallData<RESULT, CALL extends RemoteInvocationCall> {

  /** The callback to receive the service result on success. */
  private final Consumer<? extends RESULT> successCallback;

  /** The callback to receive a potential service failure. */
  private final Consumer<Throwable> failureCallback;

  /** @see #getCall() */
  private CALL call;

  /**
   * The constructor.
   *
   * @param successCallback is the callback that {@link Consumer#accept(Object) receives} the result on
   *        success.
   * @param failureCallback is the callback that {@link Consumer#accept(Object) receives} the failure on
   *        error.
   */
  public RemoteInvocationCallData(Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

    super();
    this.successCallback = successCallback;
    this.failureCallback = failureCallback;
  }

  /**
   * @return the successCallback.
   */
  public Consumer<? extends RESULT> getSuccessCallback() {

    return this.successCallback;
  }

  /**
   * @return the failureCallback.
   */
  public Consumer<Throwable> getFailureCallback() {

    return this.failureCallback;
  }

  /**
   * @return the actual call data (either {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}
   *         itself or {@link net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall}).
   */
  public CALL getCall() {

    return this.call;
  }

  /**
   * @param call is the new value of {@link #getCall()}.
   */
  public void setCall(CALL call) {

    assert (this.call == null);
    assert (call != null);
    this.call = call;
  }
}
