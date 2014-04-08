/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc;

import java.util.Collection;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.base.GenericRemoteInvocationRequest;

/**
 * This is the generic transfer-object for a request sent from client to server for any number of
 * {@link #getTransactionalCalls() transactional calls} to a
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationRpcRequest extends
    GenericRemoteInvocationRequest<GenericRemoteInvocationRpcCall, GenericRemoteInvocationRpcTransactionalCalls> {

  /** UID for serialization. */
  private static final long serialVersionUID = -3858810181904299545L;

  /** @see #getTransactionalCalls() */
  private GenericRemoteInvocationRpcTransactionalCalls[] transactionalCalls;

  /**
   * The constructor for GWT (de)serialization.
   */
  protected GenericRemoteInvocationRpcRequest() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public GenericRemoteInvocationRpcRequest(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationRpcTransactionalCalls... transactionalCalls) {

    super(requestId, xsrfToken);
    this.transactionalCalls = transactionalCalls;
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public GenericRemoteInvocationRpcRequest(int requestId, CsrfToken xsrfToken,
      Collection<GenericRemoteInvocationRpcTransactionalCalls> transactionalCalls) {

    this(requestId, xsrfToken, transactionalCalls
        .toArray(new GenericRemoteInvocationRpcTransactionalCalls[transactionalCalls.size()]));
  }

  /**
   * @return an array with the {@link GenericRemoteInvocationRpcTransactionalCalls}s.
   */
  @Override
  public GenericRemoteInvocationRpcTransactionalCalls[] getTransactionalCalls() {

    return this.transactionalCalls;
  }

}
