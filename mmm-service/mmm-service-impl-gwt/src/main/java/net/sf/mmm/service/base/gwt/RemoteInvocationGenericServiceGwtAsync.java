/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.gwt;

import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the GWT service-client stub of {@link RemoteInvocationGenericServiceGwt}.
 * 
 * TODO hohwille: Offer alternative concept to GWT-RPC because it is
 * <ul>
 * <li>cryptic and hard to read or debug</li>
 * <li>asymmetric</li>
 * <li>too slow in DevMode</li>
 * </ul>
 * Concept:
 * <ul>
 * <li>Require to use interfaces for TOs and have JavaBean only types as default implementations that can be
 * created with new.</li>
 * <li>Provide specific JSON parser on server-side using reflection (maybe an existing OSS component).</li>
 * <li>Define a TypeFactory for datatypes and others so the user can use custom types.</li>
 * <li>On the client side parse as regular JSon and then use generated code that maps this result back to the
 * interfaces.</li>
 * <li>Consider reusing existing stuff from autobean where possible</li>
 * </ul>
 * 
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationGenericServiceGwtAsync {

  /**
   * @see RemoteInvocationGenericServiceGwt#callServices(GenericRemoteInvocationRpcRequest)
   * 
   * @param request is the {@link GenericRemoteInvocationRpcRequest}.
   * @param callback is the {@link AsyncCallback} for receiving the
   *        {@link GenericRemoteInvocationRpcResponse response}.
   */
  void callServices(GenericRemoteInvocationRpcRequest request,
      AsyncCallback<GenericRemoteInvocationRpcResponse> callback);

}
