/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if an invocation of a {@link RemoteInvocationService} has failed.
 * 
 * @see net.sf.mmm.service.api.client.AbstractRemoteInvocationServiceCaller#getServiceClient(Class, Class,
 *      java.util.function.Consumer, java.util.function.Consumer)
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceCallFailedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4620028322122111115L;

  /**
   * The constructor.
   * 
   * @param cause is the {@link Throwable} that caused this error.
   * @param service is the name of the service that failed.
   * @param method is the name of the method of <code>service</code> that failed.
   */
  public RemoteInvocationServiceCallFailedException(Throwable cause, String service, String method) {

    super(cause, createBundle(NlsBundleUtilCoreRoot.class).errorInvocationFailedOn(service, method));
  }

}
