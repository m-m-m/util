/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

/**
 * This is the marker interface for a <em>remote service</em>. Such service is defined by an interface
 * extending this one. All methods defined by the interface can be called remotely from anywhere on the
 * network. The service itself is provided by an implementation that runs on a server. Whenever a client wants
 * to invoke a method of a {@link RemoteInvocationService}, he technically sends a
 * {@link net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest request} to that server, containing
 * the {@link net.sf.mmm.service.base.RemoteInvocationServiceCall invocation call data including the
 * method-parameters}. The server receives the request and invokes the method on the service implementation.
 * Then the server sends a {@link net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse response}
 * with the {@link net.sf.mmm.service.base.RemoteInvocationServiceResult result} of the method back to the
 * client.<br/>
 * This API aims to be a simplification for the RPC (Remote Procedure Call) mechanism of GWT (Google Web
 * Toolkit). However, it may also be used for other scenarios.
 * 
 * @see net.sf.mmm.service.api.client.RemoteInvocationServiceCaller
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface RemoteInvocationService {

  // just a marker interface...
}
