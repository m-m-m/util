/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.gwt;

import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * This is the <a href=
 * "http://code.google.com/intl/de-DE/webtoolkit/doc/latest/DevGuideServerCommunication.html" >GWT-RPC</a>
 * compliant service interface. It defines a single generic service that can
 * {@link #callServices(RemoteInvocationGenericServiceRequest) call} any
 * {@link net.sf.mmm.service.api.RemoteInvocationService}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface RemoteInvocationGenericServiceGwt extends RemoteInvocationGenericService, RemoteService {

  /**
   * This method calls one or multiple {@link net.sf.mmm.service.api.RemoteInvocationService}
   * {@link java.lang.reflect.Method}s.
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest}.
   * @return the according {@link RemoteInvocationGenericServiceResponse}.
   */
  RemoteInvocationGenericServiceResponse callServices(RemoteInvocationGenericServiceRequest request);

}
