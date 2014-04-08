/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command;

import java.util.List;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.base.GenericRemoteInvocationRequest;

/**
 * This is the generic transfer-object for a request sent from client to server for any number of
 * {@link #getTransactionalCalls() transactional calls} of
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationCommandRequest extends
    GenericRemoteInvocationRequest<RemoteInvocationCommand<?>, GenericRemoteInvocationCommandTransactionalCalls> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8864724626310080016L;

  /** @see #getTransactionalCalls() */
  private GenericRemoteInvocationCommandTransactionalCalls[] transactionalCalls;

  /**
   * The constructor for (de)serialization.
   */
  protected GenericRemoteInvocationCommandRequest() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}. May be <code>null</code>.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public GenericRemoteInvocationCommandRequest(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationCommandTransactionalCalls... transactionalCalls) {

    super(requestId, xsrfToken);
    this.transactionalCalls = transactionalCalls;
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}. May be <code>null</code>.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public GenericRemoteInvocationCommandRequest(int requestId, CsrfToken xsrfToken,
      List<GenericRemoteInvocationCommandTransactionalCalls> transactionalCalls) {

    this(requestId, xsrfToken, transactionalCalls
        .toArray(new GenericRemoteInvocationCommandTransactionalCalls[transactionalCalls.size()]));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericRemoteInvocationCommandTransactionalCalls[] getTransactionalCalls() {

    return this.transactionalCalls;
  }

}
