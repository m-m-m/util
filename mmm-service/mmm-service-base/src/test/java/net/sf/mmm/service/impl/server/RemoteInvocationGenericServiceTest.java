/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import java.io.Serializable;

import javax.inject.Inject;

import net.sf.mmm.service.TestService;
import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalCalls;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalResults;
import net.sf.mmm.test.AbstractSpringTest;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * This is the test-case of {@link AbstractRemoteInvocationGenericServiceImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ContextConfiguration(locations = { "classpath:beans-test-service-base.xml" })
public class RemoteInvocationGenericServiceTest extends AbstractSpringTest {

  /** @see #testCallServices() */
  @Inject
  private RemoteInvocationGenericService genericService;

  /**
   * Tests {@link RemoteInvocationGenericService#callServices(RemoteInvocationGenericServiceRequest)}.
   */
  @Test
  public void testCallServices() {

    // given
    int requestId = 42;
    RemoteInvocationServiceCall call = new RemoteInvocationServiceCall(TestService.class.getName(), "getMagicValue",
        RemoteInvocationServiceCall.getSignature(new String[0]), new Serializable[0]);
    RemoteInvocationServiceTransactionalCalls txCalls = new RemoteInvocationServiceTransactionalCalls(call);
    RemoteInvocationGenericServiceRequest request = new RemoteInvocationGenericServiceRequest(requestId, txCalls);

    // when
    RemoteInvocationGenericServiceResponse response = this.genericService.callServices(request);

    // then
    assertNotNull(response);
    assertEquals(requestId, response.getRequestId());
    RemoteInvocationServiceTransactionalResults[] txResults = response.getTransactionalResults();
    assertEquals(1, txResults.length);
    RemoteInvocationServiceTransactionalResults txResult = txResults[0];
    assertNotNull(txResult);
    Serializable[] results = txResult.getResults();
    assertEquals(1, results.length);
    Serializable result = results[0];
    assertNotNull(result);
    assertSame(TestService.MAGIC_VALUE, result);
  }

}
