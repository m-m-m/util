/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.command.client;

import java.io.Serializable;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.api.command.client.RemoteInvocationCommandQueue;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceQueue;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationCaller;
import net.sf.mmm.service.base.client.RemoteInvocationCallerBaseTest;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandTransactionalCalls;
import net.sf.mmm.service.base.command.client.AbstractRemoteInvocationCommandCaller;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.client.AbstractRemoteInvocationServiceCaller;
import net.sf.mmm.service.impl.command.client.AbstractRemoteInvocationCommandCallerTest.RemoteInvocationCommandCallerTestImpl;
import net.sf.mmm.service.impl.rpc.client.AbstractRemoteInvocationServiceCallerTest.RemoteInvocationServiceCallerTestImpl;
import net.sf.mmm.service.test.command.GetMagicValueCommand;
import net.sf.mmm.service.test.rpc.TestService;
import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.lang.api.function.Consumer;

import org.junit.Test;

/**
 * This is the test-case for {@link AbstractRemoteInvocationServiceCaller}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractRemoteInvocationCommandCallerTest extends
    RemoteInvocationCallerBaseTest<RemoteInvocationCommandQueue, RemoteInvocationCommandCallerTestImpl> {

  /**
   * This method tests
   * {@link RemoteInvocationServiceCaller#getServiceClient(Class, Class, Consumer, Consumer)}.
   */
  @Test
  public void testAutoCommit() {

    RemoteInvocationCommandCallerTestImpl caller = getRemoteInvocationCaller();
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

    caller.callCommand(new GetMagicValueCommand(), successCallback, failureCallback);
    assertSame(TestService.MAGIC_VALUE, resultBean.getValue());
  }

  /**
   * This method tests {@link RemoteInvocationServiceQueue#commit()} for a simple queue with a single
   * invocation.
   */
  @Test
  public void testCommit() {

    RemoteInvocationCommandCallerTestImpl caller = getRemoteInvocationCaller();
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

    RemoteInvocationCommandQueue queue = newQueue(caller);
    queue.callCommand(new GetMagicValueCommand(), successCallback, failureCallback);
    assertNull(resultBean.getValue());
    queue.commit();
    assertSame(TestService.MAGIC_VALUE, resultBean.getValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationCommandCallerTestImpl getRemoteInvocationCaller() {

    return new RemoteInvocationCommandCallerTestImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationCommandRequest getCurrentRequest(RemoteInvocationCommandCallerTestImpl caller) {

    return caller.currentRequest;
  }

  /**
   * This inner class extends {@link AbstractRemoteInvocationServiceCaller} so it can be tested.
   */
  public static class RemoteInvocationCommandCallerTestImpl extends AbstractRemoteInvocationCommandCaller {

    /** @see AbstractRemoteInvocationCommandCallerTest#getCurrentRequest(RemoteInvocationServiceCallerTestImpl) */
    private GenericRemoteInvocationCommandRequest currentRequest;

    /**
     * The constructor.
     */
    public RemoteInvocationCommandCallerTestImpl() {

      super();
      initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performRequest(
        GenericRemoteInvocationCommandRequest request,
        AbstractRemoteInvocationCaller<RemoteInvocationCommandQueue, RemoteInvocationCommand<?>, GenericRemoteInvocationCommandTransactionalCalls, GenericRemoteInvocationCommandRequest>.RequestBuilder builder) {

      verifyNoRequest();
      assertNotNull(request);
      assertNotNull(builder);
      this.currentRequest = request;
      GenericRemoteInvocationCommandTransactionalCalls[] txCalls = request.getTransactionalCalls();
      GenericRemoteInvocationTransactionalResults[] txResults = new GenericRemoteInvocationTransactionalResults[txCalls.length];

      for (int txIndex = 0; txIndex < txCalls.length; txIndex++) {

        RemoteInvocationCommand<?>[] commands = txCalls[txIndex].getCalls();
        Serializable[] results = new Serializable[commands.length];
        for (int callIndex = 0; callIndex < commands.length; callIndex++) {
          results[callIndex] = processCall(commands[callIndex]);
        }
        txResults[txIndex] = new GenericRemoteInvocationTransactionalResults(results);
      }

      CsrfToken xsrfToken = null;
      GenericRemoteInvocationRpcResponse response = new GenericRemoteInvocationRpcResponse(request.getRequestId(),
          xsrfToken, txResults);

      handleResponse(request, builder, response);
    }

    /**
     * Processes the given call.
     *
     * @param call is the {@link RemoteInvocationCommand} to process.
     * @return the result of the call.
     */
    private Serializable processCall(RemoteInvocationCommand<?> call) {

      if (call instanceof GetMagicValueCommand) {
        return TestService.MAGIC_VALUE;
      } else {
        fail("unknown command: " + call);
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

}
