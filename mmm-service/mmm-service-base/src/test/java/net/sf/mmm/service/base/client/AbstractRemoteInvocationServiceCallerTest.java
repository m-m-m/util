/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;

import net.sf.mmm.service.TestService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCallerTest.RemoteInvocationServiceCallerTestImpl;
import net.sf.mmm.util.lang.api.GenericBean;

import org.junit.Test;

/**
 * This is the test-case for {@link AbstractRemoteInvocationServiceCaller}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractRemoteInvocationServiceCallerTest extends
    RemoteInvocationServiceCallerBaseTest<RemoteInvocationServiceCallerTestImpl> {

  /**
   * This method tests {@link RemoteInvocationServiceQueue#commit()} for a simple queue with a single
   * invocation.
   */
  @Test
  public void testCommit() {

    RemoteInvocationServiceCallerTestImpl caller = getServiceCaller();
    assertNull(caller.getCurrentQueue());
    RemoteInvocationServiceQueue queue = newQueue(caller);
    final GenericBean<String> resultBean = new GenericBean<String>();
    RemoteInvocationServiceCallback<String> callback = new RemoteInvocationServiceCallback<String>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(String result, boolean complete) {

        resultBean.setValue(result);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(Throwable failure, boolean complete) {

        failure.printStackTrace();
        System.err.flush();
        fail(failure.getMessage());
      }
    };
    TestService serviceClient = queue.getServiceClient(TestService.class, String.class, callback);
    assertNotNull(serviceClient);
    String magicValue = serviceClient.getMagicValue();
    assertNull(magicValue);
    assertNull(resultBean.getValue());
    queue.commit();
    assertSame(TestService.MAGIC_VALUE, resultBean.getValue());
  }

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
  public static class RemoteInvocationServiceCallerTestImpl extends AbstractRemoteInvocationServiceCallerWithClientMap {

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentRequest(RemoteInvocationServiceCallerTestImpl) */
    private RemoteInvocationGenericServiceRequest currentRequest;

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentCallbacks(RemoteInvocationServiceCallerTestImpl) */
    private RemoteInvocationServiceResultCallback<?>[] currentCallbacks;

    /**
     * The constructor.
     */
    public RemoteInvocationServiceCallerTestImpl() {

      super();
      registerService(TestService.class, new TestServiceClient());
      initialize();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void performRequest(RemoteInvocationGenericServiceRequest request,
        RemoteInvocationServiceResultCallback<?>[] callbacks) {

      verifyNoRequest();
      assertNotNull(request);
      assertNotNull(callbacks);
      this.currentRequest = request;
      this.currentCallbacks = callbacks;
      RemoteInvocationServiceCall[] calls = request.getCalls();
      for (int i = 0; i < calls.length; i++) {
        if (TestService.class.getName().equals(calls[i].getServiceInterfaceName())) {
          if ("getMagicValue".equals(calls[i].getMethodName())) {
            assertEquals(0, calls[i].getArguments().length);
            RemoteInvocationServiceResult result = new RemoteInvocationServiceResult<String>(TestService.MAGIC_VALUE);
            boolean complete = i == (calls.length - 1);
            callbacks[i].onResult(result, complete);
          } else {
            fail("unknown service method: " + calls[i].getServiceInterfaceName() + "." + calls[i].getMethodName());
          }
        } else {
          fail("unknown service: " + calls[i].getServiceInterfaceName());
        }
      }
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

  /**
   * Client stub for {@link TestService}.
   */
  private static class TestServiceClient extends AbstractRemoteInvocationServiceClient implements TestService {

    /**
     * The constructor.
     */
    public TestServiceClient() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMagicValue() {

      RemoteInvocationServiceCall call = new RemoteInvocationServiceCall(TestService.class.getName(), "getMagicValue",
          RemoteInvocationServiceCall.getSignature(new String[0]), new Serializable[0]);
      addCall(call, String.class);
      return null;
    }

  }

}
