/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link AbstractRemoteInvocationServiceCaller}
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
    assertNull(getCurrentCallbacks(caller));
  }

  /**
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   * @return the current {@link RemoteInvocationGenericServiceRequest} that has been "performed" by the caller
   *         or <code>null</code> if no request has been performed.
   */
  protected abstract RemoteInvocationGenericServiceRequest getCurrentRequest(CALLER caller);

  /**
   * @param caller is the {@link RemoteInvocationServiceCaller}.
   * @return the current array of {@link RemoteInvocationServiceResultCallback}s that has been collected for
   *         the {@link #getCurrentRequest(RemoteInvocationServiceCaller) request} by the caller or
   *         <code>null</code> if no request has been performed.
   */
  protected abstract RemoteInvocationServiceResultCallback<?>[] getCurrentCallbacks(CALLER caller);

  protected void cancel(CALLER caller, RemoteInvocationServiceQueue queue) {

    queue.cancel();
    assertFalse(queue.isOpen());
    verifyNoRequest(caller);
  }

  protected RemoteInvocationServiceQueue newQueue(CALLER caller) {

    RemoteInvocationServiceQueue queue = caller.newQueue();
    assertNotNull(queue);
    assertTrue(queue.isOpen());
    assertSame(queue, caller.getCurrentQueue());
    return queue;
  }

  /**
   * This method tests the basic
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
   * This method tests the basic
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
