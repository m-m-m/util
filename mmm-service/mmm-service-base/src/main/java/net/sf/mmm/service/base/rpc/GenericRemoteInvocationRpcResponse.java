/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc;

import java.util.Collection;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.base.GenericRemoteInvocationResponse;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;

/**
 * This is the generic transfer-object for the response to a {@link GenericRemoteInvocationRpcRequest}.
 * 
 * @see GenericRemoteInvocationRpcService#callServices(GenericRemoteInvocationRpcRequest)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationRpcResponse extends GenericRemoteInvocationResponse {

  /** UID for serialization. */
  private static final long serialVersionUID = -3659003282415103857L;

  /**
   * The constructor.
   */
  protected GenericRemoteInvocationRpcResponse() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationRpcResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults... transactionalResults) {

    super(requestId, xsrfToken, transactionalResults);
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationRpcResponse(int requestId, CsrfToken xsrfToken,
      Collection<GenericRemoteInvocationTransactionalResults> transactionalResults) {

    super(requestId, xsrfToken, transactionalResults);
  }

}
