/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command;

import java.util.Collection;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.base.GenericRemoteInvocationResponse;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;

/**
 * This is the generic transfer-object for a response to a {@link GenericRemoteInvocationCommandRequest}. It
 * contains the {@link GenericRemoteInvocationTransactionalResults} corresponding to the
 * {@link GenericRemoteInvocationCommandTransactionalCalls} from the
 * {@link GenericRemoteInvocationCommandRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationCommandResponse extends GenericRemoteInvocationResponse {

  /** UID for serialization. */
  private static final long serialVersionUID = -1124008193308050754L;

  /**
   * The constructor.
   */
  protected GenericRemoteInvocationCommandResponse() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}. May be <code>null</code>.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationCommandResponse(int requestId, CsrfToken xsrfToken,
      Collection<GenericRemoteInvocationTransactionalResults> transactionalResults) {

    super(requestId, xsrfToken, transactionalResults);
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param xsrfToken - see {@link #getXsrfToken()}. May be <code>null</code>.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public GenericRemoteInvocationCommandResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults... transactionalResults) {

    super(requestId, xsrfToken, transactionalResults);
  }

}
