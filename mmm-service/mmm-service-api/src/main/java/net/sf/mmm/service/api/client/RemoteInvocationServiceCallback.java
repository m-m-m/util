/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.io.Serializable;

/**
 * This is the callback-interface that has to be implemented by the user of a
 * {@link net.sf.mmm.service.api.RemoteInvocationService} to {@link #onSuccess(Serializable, boolean) receive
 * the result} of a method invocation asynchronously.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <RESULT> is the generic type of the {@link #onSuccess(Serializable, boolean) result to receive}.
 */
public interface RemoteInvocationServiceCallback<RESULT extends Serializable> {

  /**
   * This method is called to receive the result of a successful invocation of a
   * {@link net.sf.mmm.service.api.RemoteInvocationService} {@link java.lang.reflect.Method}.
   * 
   * @param result is the actual result of the {@link net.sf.mmm.service.api.RemoteInvocationService}
   *        {@link java.lang.reflect.Method} that has been invoked.
   * @param complete - <code>true</code> if this is the last result for a request.
   */
  void onSuccess(RESULT result, boolean complete);

  /**
   * This method is called if the invocation of a {@link net.sf.mmm.service.api.RemoteInvocationService}
   * {@link java.lang.reflect.Method} has failed. The failure can have arbitrary reasons (e.g. a network error
   * or the method failed on the server).<br/>
   * <b>ATTENTION:</b><br/>
   * Due to potential limitations for the serialization of exceptions (e.g. for GWT) the given
   * <code>failure</code> might NOT be of the same type as it has been thrown on the server.
   * 
   * @param failure is the actual failure.
   * @param complete - <code>true</code> if this is the last result for a request.
   */
  void onFailure(Throwable failure, boolean complete);

}
