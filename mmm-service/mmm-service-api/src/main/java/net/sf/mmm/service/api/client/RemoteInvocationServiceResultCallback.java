/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationServiceResult;

/**
 * This is the callback-interface that has to be implemented to
 * {@link #onResult(RemoteInvocationServiceResult, boolean) retrieve the result} of a
 * {@link net.sf.mmm.service.api.RemoteInvocationService} method-invocation asynchronously.<br/>
 * <b>ATTENTION:</b><br/>
 * This interface is intended for scenarios where custom extension of the transport is needed (e.g.
 * transferring additional generic context information that is not part of the methods return types). It
 * should NOT be used directly, but through an abstract base implementation. If no such customization is
 * required you should always use {@link RemoteInvocationServiceCallback} directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <RESULT> is the generic type of the {@link #onResult(RemoteInvocationServiceResult, boolean) result
 *        to receive}.
 */
public interface RemoteInvocationServiceResultCallback<RESULT extends Serializable> {

  /**
   * This method is called to receive the {@link RemoteInvocationServiceResult result} of a invocation of a
   * {@link net.sf.mmm.service.api.RemoteInvocationService} {@link java.lang.reflect.Method}.
   * 
   * @param result is the actual {@link RemoteInvocationServiceResult} of the
   *        {@link net.sf.mmm.service.api.RemoteInvocationService} {@link java.lang.reflect.Method} that has
   *        been invoked.
   * @param complete - <code>true</code> if this is the last result for a request.
   */
  void onResult(RemoteInvocationServiceResult<RESULT> result, boolean complete);

}
