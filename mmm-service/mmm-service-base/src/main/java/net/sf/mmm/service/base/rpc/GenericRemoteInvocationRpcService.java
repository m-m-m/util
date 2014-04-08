/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc;

/**
 * This is the interface for a generic RPC-service that can
 * {@link #callServices(GenericRemoteInvocationRpcRequest) call} any
 * {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface GenericRemoteInvocationRpcService {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService";

  /**
   * This method calls one or multiple {@link net.sf.mmm.service.api.rpc.RemoteInvocationService}
   * {@link java.lang.reflect.Method}s.
   * 
   * @param request is the {@link GenericRemoteInvocationRpcRequest}.
   * @return the according {@link GenericRemoteInvocationRpcResponse}.
   */
  GenericRemoteInvocationRpcResponse callServices(GenericRemoteInvocationRpcRequest request);

}
