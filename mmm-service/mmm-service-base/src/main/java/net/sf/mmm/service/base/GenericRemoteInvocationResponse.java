/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;
import java.util.Collection;

import net.sf.mmm.service.api.CsrfToken;

/**
 * This is the transfer-object for the generic response to a remote invocation.
 *
 * @see net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService#callServices(net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest)
 * @see net.sf.mmm.service.base.command.GenericRemoteInvocationCommandService#callCommands(net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationResponse implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3659003282415103857L;

  /** @see #getTransactionalResults() */
  private GenericRemoteInvocationTransactionalResults[] transactionalResults;

  /** @see #getRequestId() */
  private int requestId;

  /** @see #getXsrfToken() */
  private CsrfToken xsrfToken;

  /**
   * The constructor.
   */
  protected GenericRemoteInvocationResponse() {

    super();
  }

  /**
   * The constructor.
   *
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults[] transactionalResults) {

    super();
    this.requestId = requestId;
    this.xsrfToken = xsrfToken;
    this.transactionalResults = transactionalResults;
  }

  /**
   * The constructor.
   *
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationResponse(int requestId, CsrfToken xsrfToken,
      Collection<GenericRemoteInvocationTransactionalResults> transactionalResults) {

    this(requestId, xsrfToken, transactionalResults
        .toArray(new GenericRemoteInvocationTransactionalResults[transactionalResults.size()]));
  }

  /**
   * @return the {@link GenericRemoteInvocationRequest#getRequestId() ID of the request} this response
   *         corresponds to.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * Gets the {@link CsrfToken} that has been generated on the server-side. If not <code>null</code> the
   * client has to store the token and send it back to the server
   * {@link GenericRemoteInvocationRequest#getXsrfToken() within} the next
   * {@link GenericRemoteInvocationRequest request}. This is all handled by the infrastructure of mmm-service
   * but can be extended for custom needs.
   *
   * @return the {@link CsrfToken} or <code>null</code> if unchanged.
   */
  public CsrfToken getXsrfToken() {

    return this.xsrfToken;
  }

  /**
   * @return an array with the {@link GenericRemoteInvocationTransactionalResults}s.
   */
  public GenericRemoteInvocationTransactionalResults[] getTransactionalResults() {

    return this.transactionalResults;
  }

}
