/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.gwt;

import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This is the <a href=
 * "http://code.google.com/intl/de-DE/webtoolkit/doc/latest/DevGuideServerCommunication.html" >GWT-RPC</a>
 * compliant service interface. It defines a single generic service that can
 * {@link #callServices(GenericRemoteInvocationRpcRequest) call} any
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@RemoteServiceRelativePath("service/RemoteInvocationGenericServiceGwt")
public interface RemoteInvocationGenericServiceGwt extends GenericRemoteInvocationRpcService, RemoteService {

  /**
   * This method calls one or multiple {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}
   * {@link java.lang.reflect.Method}s.
   * 
   * @param request is the {@link GenericRemoteInvocationRpcRequest}.
   * @return the according {@link GenericRemoteInvocationRpcResponse}.
   */
  GenericRemoteInvocationRpcResponse callServices(GenericRemoteInvocationRpcRequest request);

}
