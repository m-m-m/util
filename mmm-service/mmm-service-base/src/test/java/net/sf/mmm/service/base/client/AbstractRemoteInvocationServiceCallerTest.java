/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import java.io.Serializable;
import java.util.function.Consumer;

import net.sf.mmm.service.TestService;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalCalls;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalResults;
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
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void performRequest(RemoteInvocationGenericServiceRequest request, RequestBuilder builder) {

      verifyNoRequest();
      assertNotNull(request);
      assertNotNull(builder);
      this.currentRequest = request;
      RemoteInvocationServiceTransactionalCalls[] txCalls = request.getTransactionalCalls();
      RemoteInvocationServiceTransactionalResults[] txResults = new RemoteInvocationServiceTransactionalResults[txCalls.length];

      for (int txIndex = 0; txIndex < txCalls.length; txIndex++) {

        RemoteInvocationServiceCall[] calls = txCalls[txIndex].getCalls();
        Serializable[] results = new Serializable[calls.length];
        for (int callIndex = 0; callIndex < calls.length; callIndex++) {
          results[callIndex] = processCall(calls[callIndex]);
        }
        txResults[txIndex] = new RemoteInvocationServiceTransactionalResults(results);
      }

      RemoteInvocationGenericServiceResponse response = new RemoteInvocationGenericServiceResponse(
          request.getRequestId(), txResults);

      handleResponse(request, builder, response);
    }

    private Serializable processCall(RemoteInvocationServiceCall currentCall) {

      if (TestService.class.getName().equals(currentCall.getServiceInterfaceName())) {
        if ("getMagicValue".equals(currentCall.getMethodName())) {
          assertEquals(0, currentCall.getArguments().length);
          return TestService.MAGIC_VALUE;
        } else {
          fail("unknown service method: " + currentCall.getServiceInterfaceName() + "." + currentCall.getMethodName());
        }
      } else {
        fail("unknown service: " + currentCall.getServiceInterfaceName());
      }
      throw new IllegalStateException();
    }

    /**
     * This method verifies that no request has been performed since last {@link #reset()}.
     */
    public void verifyNoRequest() {

      assertNull(this.currentRequest);
    }

    /**
     * This method resets this test caller.
     */
    public void reset() {

      this.currentRequest = null;
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
