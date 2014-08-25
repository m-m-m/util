/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.rpc.server;

import java.io.Serializable;

import javax.inject.Inject;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcTransactionalCalls;
import net.sf.mmm.service.impl.CsrfTokenManagerDummyImpl;
import net.sf.mmm.service.test.rpc.TestService;
import net.sf.mmm.test.AbstractSpringTest;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is the test-case of {@link AbstractGenericRemoteInvocationRpcService}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ContextConfiguration(locations = { "classpath:beans-test-service-base.xml" })
public class RemoteInvocationGenericServiceTest extends AbstractSpringTest {

  /** @see #testCallServices() */
  @Inject
  private GenericRemoteInvocationRpcService genericService;

  /**
   * Tests {@link GenericRemoteInvocationRpcService#callServices(GenericRemoteInvocationRpcRequest)}.
   */
  @Test
  public void testCallServices() {

    // given
    int requestId = 42;
    GenericRemoteInvocationRpcCall call = new GenericRemoteInvocationRpcCall(TestService.class.getName(),
        "getMagicValue", GenericRemoteInvocationRpcCall.getSignature(new String[0]), new Serializable[0]);
    GenericRemoteInvocationRpcTransactionalCalls txCalls = new GenericRemoteInvocationRpcTransactionalCalls(call);
    CsrfToken xsrfToken = CsrfTokenManagerDummyImpl.DUMMY_TOKEN;
    GenericRemoteInvocationRpcRequest request = new GenericRemoteInvocationRpcRequest(requestId, xsrfToken, txCalls);

    // when
    GenericRemoteInvocationRpcResponse response = this.genericService.callServices(request);

    // then
    assertNotNull(response);
    assertEquals(requestId, response.getRequestId());
    GenericRemoteInvocationTransactionalResults[] txResults = response.getTransactionalResults();
    assertEquals(1, txResults.length);
    GenericRemoteInvocationTransactionalResults txResult = txResults[0];
    assertNotNull(txResult);
    Serializable[] results = txResult.getResults();
    assertEquals(1, results.length);
    Serializable result = results[0];
    assertNotNull(result);
    assertSame(TestService.MAGIC_VALUE, result);
  }

}
