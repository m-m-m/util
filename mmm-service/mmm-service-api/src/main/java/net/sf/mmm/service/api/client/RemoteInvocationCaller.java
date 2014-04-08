/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client;

/**
 * This is the abstract interface for a component that allows to invoke one or multiple remote invocations to
 * a server from the client-side. As described in {@link net.sf.mmm.service.api} there are two variants
 * available:
 * <ul>
 * <li>{@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller RPC style}</li>
 * <li>{@link net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller command style}</li>
 * </ul>
 * 
 * @param <QUEUE> is the generic type of the {@link #getCurrentQueue() queues} managed by this caller.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface RemoteInvocationCaller<QUEUE extends RemoteInvocationQueue> {

  /**
   * This method gets the current {@link RemoteInvocationQueue queue}. This is the last queue that has
   * been {@link #newQueue() created} and is still {@link RemoteInvocationQueueState#OPEN open}.<br/>
   * <b>NOTE:</b><br/>
   * This method is only for specific scenarios. You should typically use {@link #newQueue()} instead, to
   * retrieve a queue and perform service invocations.
   * 
   * @return the current {@link RemoteInvocationQueue queue} or <code>null</code> if no queue is open.
   */
  QUEUE getCurrentQueue();

  /**
   * This method opens a {@link RemoteInvocationQueue queue} that collects remote invocations to send
   * to the server.
   * 
   * @see #newQueue(RemoteInvocationQueueSettings)
   * 
   * @return the new queue.
   */
  QUEUE newQueue();

  /**
   * This method opens a {@link RemoteInvocationQueue queue} that collects remote invocations to send
   * to the server.
   * 
   * @see #newQueue(RemoteInvocationQueueSettings)
   * 
   * @param id is the {@link RemoteInvocationQueue#getId() identifier} of the new
   *        {@link RemoteInvocationQueue queue}.
   * @return the new queue.
   */
  QUEUE newQueue(String id);

  /**
   * This method opens a {@link RemoteInvocationQueue queue} that collects remote invocations to send
   * to the server. If a {@link RemoteInvocationQueue queue} is {@link #getCurrentQueue() currently}
   * {@link RemoteInvocationQueueState#OPEN open} this method will create a child-queue of that queue.
   * 
   * @param settings are the {@link RemoteInvocationQueueSettings}.
   * @return the new {@link RemoteInvocationQueue queue}.
   */
  QUEUE newQueue(RemoteInvocationQueueSettings settings);

}
