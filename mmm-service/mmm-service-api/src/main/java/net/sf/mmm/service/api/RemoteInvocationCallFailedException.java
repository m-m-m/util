/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;

/**
 * This exception is thrown if {@link RemoteInvocationCall} has failed.
 * 
 * @see net.sf.mmm.service.api.client.RemoteInvocationCaller
 * @see net.sf.mmm.service.api.client.RemoteInvocationQueue#commit()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationCallFailedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4620028322122111115L;

  /**
   * The constructor.
   * 
   * @param cause is the {@link Throwable} that caused this error.
   * @param operation is the {@link RemoteInvocationCall} that failed.
   */
  public RemoteInvocationCallFailedException(Throwable cause, RemoteInvocationCall operation) {

    super(cause, createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailedOf(operation));
  }

}
