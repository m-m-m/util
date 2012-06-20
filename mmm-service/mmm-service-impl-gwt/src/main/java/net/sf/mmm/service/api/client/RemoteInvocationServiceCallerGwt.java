/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the GWT specific variant of {@link RemoteInvocationServiceCaller}. It allows to use
 * {@link AsyncCallback} as alternative to
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCallback}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceCallerGwt extends RemoteInvocationServiceCaller {

  /**
   * {@inheritDoc}
   */
  RemoteInvocationServiceQueueGwt getCurrentQueue();

  /**
   * {@inheritDoc}
   */
  RemoteInvocationServiceQueueGwt newQueue();

  /**
   * {@inheritDoc}
   */
  RemoteInvocationServiceQueueGwt newQueue(RemoteInvocationServiceQueueSettings settings);

}
