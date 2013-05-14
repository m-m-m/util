/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import net.sf.mmm.service.TestService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
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
   * This method tests
   * {@link RemoteInvocationServiceCaller#getServiceClient(Class, Class, Consumer, Consumer)}.
   */
  @Test
  public void testAutoCommit() {

    RemoteInvocationServiceCallerTestImpl caller = getServiceCaller();
    assertNull(caller.getCurrentQueue());
    final GenericBean<String> resultBean = new GenericBean<String>();
    Consumer<String> successCallback = new Consumer<String>() {

      @Override
      public void accept(String result) {

        resultBean.setValue(result);
      }
    };
    Consumer<Throwable> failureCallback = new Consumer<Throwable>() {

      @Override
      public void accept(Throwable failure) {

        failure.printStackTrace();
        System.err.flush();
        fail(failure.getMessage());
      }
    };

    TestService serviceClient = caller.getServiceClient(TestService.class, String.class, successCallback,
        failureCallback);
    assertNotNull(serviceClient);
    assertNull(resultBean.getValue());
    String magicValue = serviceClient.getMagicValue();
    assertNull(magicValue);
    assertSame(TestService.MAGIC_VALUE, resultBean.getValue());
  }

  /**
   * This method tests {@link RemoteInvocationServiceQueue#commit()} for a simple queue with a single
   * invocation.
   */
  @Test
  public void testCommit() {

    RemoteInvocationServiceCallerTestImpl caller = getServiceCaller();
    assertNull(caller.getCurrentQueue());
    final GenericBean<String> resultBean = new GenericBean<String>();
    Consumer<String> successCallback = new Consumer<String>() {

      @Override
      public void accept(String result) {

        resultBean.setValue(result);
      }
    };
    Consumer<Throwable> failureCallback = new Consumer<Throwable>() {

      @Override
      public void accept(Throwable failure) {

        failure.printStackTrace();
        System.err.flush();
        fail(failure.getMessage());
      }
    };

    RemoteInvocationServiceQueue queue = newQueue(caller);
    TestService serviceClient = queue.getServiceClient(TestService.class, String.class, successCallback,
        failureCallback);
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
   * This inner class extends {@link AbstractRemoteInvocationServiceCaller} so it can be tested.
   */
  public static class RemoteInvocationServiceCallerTestImpl extends AbstractRemoteInvocationServiceCallerWithClientMap {

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentRequest(RemoteInvocationServiceCallerTestImpl) */
    private RemoteInvocationGenericServiceRequest currentRequest;

    /** @see AbstractRemoteInvocationServiceCallerTest#getCurrentCallbacks(RemoteInvocationServiceCallerTestImpl) */
    private List<ServiceCallData<?>> currentCalls;

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
    protected void performRequest(RemoteInvocationGenericServiceRequest request, List<ServiceCallData<?>> serviceCalls) {

      verifyNoRequest();
      assertNotNull(request);
      assertNotNull(serviceCalls);
      this.currentRequest = request;
      this.currentCalls = serviceCalls;
      RemoteInvocationServiceCall[] calls = request.getCalls();
      int i = 0;
      for (ServiceCallData<?> callData : serviceCalls) {
        // for (int i = 0; i < calls.length; i++) {
        RemoteInvocationServiceCall currentCall = calls[i++];
        if (TestService.class.getName().equals(currentCall.getServiceInterfaceName())) {
          if ("getMagicValue".equals(currentCall.getMethodName())) {
            assertEquals(0, currentCall.getArguments().length);
            RemoteInvocationServiceResult result = new RemoteInvocationServiceResult<String>(TestService.MAGIC_VALUE);
            boolean complete = i == (calls.length - 1);
            Consumer successCallback = callData.getSuccessCallback();
            handleResult(currentCall, result, successCallback, callData.getFailureCallback(), complete);
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
      assertNull(this.currentCalls);
    }

    /**
     * This method resets this test caller.
     */
    public void reset() {

      this.currentRequest = null;
      this.currentCalls = null;
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
