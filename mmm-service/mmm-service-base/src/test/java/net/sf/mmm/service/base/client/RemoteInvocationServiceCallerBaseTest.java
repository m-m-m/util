/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.client.RemoteInvocationQueueState;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the base-class for a test-case of {@link RemoteInvocationServiceCaller}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CALLER> is the generic type of the {@link #getServiceCaller() service caller implementation to
 *        test}.
 */
public abstract class RemoteInvocationServiceCallerBaseTest<CALLER extends RemoteInvocationServiceCaller> extends
    Assert {

  /**
   * @return the {@link RemoteInvocationServiceCaller} to be tested.
   */
  protected abstract CALLER getServiceCaller();

  /**
   * This method is called if {@link RemoteInvocationServiceQueue}s have been created but been
   * {@link RemoteInvocationServiceQueue#cancel() cancelled}. Depending on the implementation of the
   * {@link RemoteInvocationServiceCaller} it should verify that no request has been performed.
   *
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   */
  protected void verifyNoRequest(CALLER caller) {

    assertNull(getCurrentRequest(caller));
  }

  /**
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   * @return the current {@link GenericRemoteInvocationRpcRequest} that has been "performed" by the caller or
   *         <code>null</code> if no request has been performed.
   */
  protected abstract GenericRemoteInvocationRpcRequest getCurrentRequest(CALLER caller);

  /**
   * Cancels the given {@link RemoteInvocationServiceQueue} and performs verifications.
   *
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   * @param queue is the {@link RemoteInvocationServiceQueue} to cancel.
   */
  protected void cancel(CALLER caller, RemoteInvocationServiceQueue queue) {

    queue.cancel();
    assertFalse(queue.getState() == RemoteInvocationQueueState.CANCELLED);
    verifyNoRequest(caller);
  }

  /**
   * This method creates a new {@link RemoteInvocationServiceQueue} and performs verifcations.
   *
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   * @return the new queue.
   */
  protected RemoteInvocationServiceQueue newQueue(CALLER caller) {

    RemoteInvocationServiceQueue queue = caller.newQueue();
    assertNotNull(queue);
    assertTrue(queue.getState() == RemoteInvocationQueueState.OPEN);
    assertSame(queue, caller.getCurrentQueue());
    return queue;
  }

  /**
   * This method tests {@link RemoteInvocationServiceQueue#cancel()} for a simple queue.
   */
  @Test
  public void testCancel() {

    CALLER caller = getServiceCaller();
    assertNull(caller.getCurrentQueue());
    RemoteInvocationServiceQueue queue = newQueue(caller);
    cancel(caller, queue);
    assertNull(caller.getCurrentQueue());
  }

  /**
   * This method tests {@link RemoteInvocationServiceQueue#cancel()} for a queue with child queues.
   */
  @Test
  public void testCancelWithChildQueues() {

    CALLER caller = getServiceCaller();
    assertNull(caller.getCurrentQueue());
    RemoteInvocationServiceQueue rootQueue = newQueue(caller);
    RemoteInvocationServiceQueue subQueue = newQueue(caller);
    RemoteInvocationServiceQueue subSubQueue = newQueue(caller);
    cancel(caller, subSubQueue);
    assertSame(subQueue, caller.getCurrentQueue());
    cancel(caller, subQueue);
    assertSame(rootQueue, caller.getCurrentQueue());
    cancel(caller, rootQueue);
    assertNull(caller.getCurrentQueue());
  }

}
