/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

/**
 * This is the interface for a generic RPC-service that can
 * {@link #callServices(RemoteInvocationGenericServiceRequest) call} any
 * {@link net.sf.mmm.service.api.RemoteInvocationService}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface RemoteInvocationGenericService {

  /**
   * This method calls one or multiple {@link net.sf.mmm.service.api.RemoteInvocationService}
   * {@link java.lang.reflect.Method}s.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest}.
   * @return the according {@link RemoteInvocationGenericServiceResponse}.
   */
  RemoteInvocationGenericServiceResponse callServices(RemoteInvocationGenericServiceRequest request);

}
