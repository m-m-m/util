/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.gwt.client;

import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueSettings;


/**
 * This is the GWT specific variant of {@link RemoteInvocationServiceCaller}. It allows to use
 * {@link com.google.gwt.user.client.rpc.AsyncCallback} as alternative to
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCallback}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceCallerGwt extends RemoteInvocationServiceCaller {

  /**
   * {@inheritDoc}
   */
  @Override
  RemoteInvocationServiceQueueGwt getCurrentQueue();

  /**
   * {@inheritDoc}
   */
  @Override
  RemoteInvocationServiceQueueGwt newQueue();

  /**
   * {@inheritDoc}
   */
  @Override
  RemoteInvocationServiceQueueGwt newQueue(RemoteInvocationServiceQueueSettings settings);

}
