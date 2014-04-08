/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationCall;
import net.sf.mmm.service.api.CsrfToken;

/**
 * This is the abstract base class for the transfer-object of a request sent from client to server for any
 * number of (transactional) remote invocations.
 *
 * @see net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService#callServices(net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest)
 * @see net.sf.mmm.service.base.command.GenericRemoteInvocationCommandService#callCommands(net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest)
 *
 * @param <CALL> is the generic type of the {@link RemoteInvocationCall}s.
 * @param <TX_CALLS> is the generic type of the {@link #getTransactionalCalls() contained}
 *        {@link GenericRemoteInvocationTransactionalCalls}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class GenericRemoteInvocationRequest<CALL extends RemoteInvocationCall, TX_CALLS extends GenericRemoteInvocationTransactionalCalls<CALL>>
    implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3858810181904299545L;

  /** @see #getRequestId() */
  private int requestId;

  /** @see #getXsrfToken() */
  private CsrfToken xsrfToken;

  /**
   * The constructor for (de)serialization.
   */
  protected GenericRemoteInvocationRequest() {

    super();
  }

  /**
   * The constructor.
   *
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}.
   */
  public GenericRemoteInvocationRequest(int requestId, CsrfToken xsrfToken) {

    super();
    this.requestId = requestId;
    this.xsrfToken = xsrfToken;
  }

  /**
   * @return the identifier for this request. This may be a simple counter from an individual client and may
   *         NOT be unique across multiple clients.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * @return the {@link CsrfToken} used for {@link net.sf.mmm.util.lang.api.concern.Security} to prevent
   *         <em>cross site request forgery</em> (XSRF) attacks. May be <code>null</code> for unsecured
   *         methods (that do not require authentication).
   */
  public CsrfToken getXsrfToken() {

    return this.xsrfToken;
  }

  /**
   * @return the {@literal <TX_CALLS>}-array with the calls grouped for transaction.
   */
  public abstract TX_CALLS[] getTransactionalCalls();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Request #" + this.requestId;
  }

}
