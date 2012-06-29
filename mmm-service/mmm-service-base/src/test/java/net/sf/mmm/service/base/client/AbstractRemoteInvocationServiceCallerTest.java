/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCallerTest.RemoteInvocationServiceCallerTestImpl;

/**
 * This is the test-case for {@link AbstractRemoteInvocationServiceCaller}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractRemoteInvocationServiceCallerTest extends
    RemoteInvocationServiceCallerBaseTest<RemoteInvocationServiceCallerTestImpl> {

  /**
   * @return the {@link AbstractRemoteInvocationServiceCaller} to be tested.
   */
  @Override
  protected RemoteInvocationServiceCallerTestImpl getServiceCaller() {

    return new RemoteInvocationServiceCallerTestImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationGenericServiceRequest getCurrentRequest(RemoteInvocationServiceCallerTestImpl caller) {

    return caller.currentRequest;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationServiceResultCallback<?>[] getCurrentCallbacks(RemoteInvocationServiceCallerTestImpl caller) {

    return caller.currentCallbacks;
  }

  /**
   * This inner class extends {@link AbstractRemoteInvocationServiceCaller} so it can be tested.
   */
  public static class RemoteInvocationServiceCallerTestImpl extends AbstractRemoteInvocationServiceCaller {

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentRequest(RemoteInvocationServiceCallerTestImpl) */
    private RemoteInvocationGenericServiceRequest currentRequest;

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentCallbacks(RemoteInvocationServiceCallerTestImpl) */
    private RemoteInvocationServiceResultCallback<?>[] currentCallbacks;

    /**
     * The constructor.
     */
    public RemoteInvocationServiceCallerTestImpl() {

      super();
      initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performRequest(RemoteInvocationGenericServiceRequest request,
        RemoteInvocationServiceResultCallback<?>[] callbacks) {

      verifyNoRequest();
      assertNotNull(request);
      assertNotNull(callbacks);
      this.currentRequest = request;
      this.currentCallbacks = callbacks;
    }

    /**
     * This method verifies that no request has been performed since last {@link #reset()}.
     */
    public void verifyNoRequest() {

      assertNull(this.currentRequest);
      assertNull(this.currentCallbacks);
    }

    /**
     * This method resets this test caller.
     */
    public void reset() {

      this.currentRequest = null;
      this.currentCallbacks = null;
    }

  }

}
