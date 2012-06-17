/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for the component to invoke one or multiple
 * {@link net.sf.mmm.service.api.RemoteInvocationService}s from the client-side.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface RemoteInvocationServiceCaller {

  /**
   * This method opens a {@link RemoteInvocationServiceQueue} that collects method-invocations on a
   * {@link net.sf.mmm.service.api.RemoteInvocationService}.
   * 
   * @return the new {@link RemoteInvocationServiceQueue}.
   */
  RemoteInvocationServiceQueue newQueue();

  /**
   * This method opens a {@link RemoteInvocationServiceQueue} that collects method-invocations on a
   * {@link net.sf.mmm.service.api.RemoteInvocationService}.
   * 
   * @param settings are the {@link RemoteInvocationServiceQueueSettings}.
   * @return the new {@link RemoteInvocationServiceQueue}.
   */
  RemoteInvocationServiceQueue newQueue(RemoteInvocationServiceQueueSettings settings);

}
