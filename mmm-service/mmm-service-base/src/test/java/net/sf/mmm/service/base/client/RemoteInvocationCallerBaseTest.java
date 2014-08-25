/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.client.RemoteInvocationCaller;
import net.sf.mmm.service.api.client.RemoteInvocationQueue;
import net.sf.mmm.service.api.client.RemoteInvocationQueueState;
import net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller;
import net.sf.mmm.service.base.GenericRemoteInvocationRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the base-class for a test-case of {@link RemoteInvocationCommandCaller}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <QUEUE> is the generic type of the {@link RemoteInvocationQueue}.
 * @param <CALLER> is the generic type of the {@link #getRemoteInvocationCaller() remote invocation caller
 *        implementation to test}.
 */
public abstract class RemoteInvocationCallerBaseTest<QUEUE extends RemoteInvocationQueue, CALLER extends RemoteInvocationCaller<QUEUE>>
    extends Assert {

  /**
   * @return the {@link RemoteInvocationCaller} to be tested.
   */
  protected abstract CALLER getRemoteInvocationCaller();

  /**
   * This method is called if {@link RemoteInvocationQueue}s have been created but been
   * {@link RemoteInvocationQueue#cancel() cancelled}. Depending on the implementation of the
   * {@link RemoteInvocationCaller} it should verify that no request has been performed.
   *
   * @param caller is the {@link RemoteInvocationCaller}.
   */
  protected void verifyNoRequest(CALLER caller) {

    assertNull(getCurrentRequest(caller));
  }

  /**
   * @param caller is the {@link RemoteInvocationCaller}.
   * @return the current {@link GenericRemoteInvocationRequest} that has been "performed" by the caller or
   *         <code>null</code> if no request has been performed.
   */
  protected abstract GenericRemoteInvocationRequest<?, ?> getCurrentRequest(CALLER caller);

  /**
   * Cancels the given {@link RemoteInvocationQueue} and performs verifications.
   *
   * @param caller is the {@link RemoteInvocationCaller}.
   * @param queue is the {@link RemoteInvocationQueue} to cancel.
   */
  protected void cancel(CALLER caller, QUEUE queue) {

    queue.cancel();
    assertFalse(queue.getState() == RemoteInvocationQueueState.CANCELLED);
    verifyNoRequest(caller);
  }

  /**
   * This method creates a new {@link RemoteInvocationQueue} and performs verifications.
   *
   * @param caller is the {@link RemoteInvocationQueue}.
   * @return the new queue.
   */
  protected QUEUE newQueue(CALLER caller) {

    QUEUE queue = caller.newQueue();
    assertNotNull(queue);
    assertTrue(queue.getState() == RemoteInvocationQueueState.OPEN);
    assertSame(queue, caller.getCurrentQueue());
    return queue;
  }

  /**
   * This method tests {@link RemoteInvocationQueue#cancel()} for a simple queue.
   */
  @Test
  public void testCancel() {

    CALLER caller = getRemoteInvocationCaller();
    assertNull(caller.getCurrentQueue());
    QUEUE queue = newQueue(caller);
    cancel(caller, queue);
    assertNull(caller.getCurrentQueue());
  }

  /**
   * This method tests {@link RemoteInvocationQueue#cancel()} for a queue with child queues.
   */
  @Test
  public void testCancelWithChildQueues() {

    CALLER caller = getRemoteInvocationCaller();
    assertNull(caller.getCurrentQueue());
    QUEUE rootQueue = newQueue(caller);
    QUEUE subQueue = newQueue(caller);
    QUEUE subSubQueue = newQueue(caller);
    cancel(caller, subSubQueue);
    assertSame(subQueue, caller.getCurrentQueue());
    cancel(caller, subQueue);
    assertSame(rootQueue, caller.getCurrentQueue());
    cancel(caller, rootQueue);
    assertNull(caller.getCurrentQueue());
  }

}
