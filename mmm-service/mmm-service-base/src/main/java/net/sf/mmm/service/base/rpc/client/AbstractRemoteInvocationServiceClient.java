/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc.client;

import net.sf.mmm.service.api.rpc.RemoteInvocationService;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;

/**
 * This is the abstract base implementation for the service-client stubs of {@link RemoteInvocationService}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceClient implements RemoteInvocationService {

  /** @see #getRemoteInvocationSerivceCaller() */
  private AbstractRemoteInvocationServiceCaller remoteInvocationSerivceCaller;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceClient() {

    super();
  }

  /**
   * @param serivceCaller is the serivceCaller to set
   */
  void setRemoteInvocationSerivceCaller(AbstractRemoteInvocationServiceCaller serivceCaller) {

    this.remoteInvocationSerivceCaller = serivceCaller;
  }

  /**
   * @return the serivceCaller
   */
  protected AbstractRemoteInvocationServiceCaller getRemoteInvocationSerivceCaller() {

    return this.remoteInvocationSerivceCaller;
  }

  /**
   * @see AbstractRemoteInvocationServiceCaller#addCall(GenericRemoteInvocationRpcCall, Class)
   * 
   * @param call is the {@link GenericRemoteInvocationRpcCall} to add.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
   *        {@link java.lang.reflect.Method}.
   */
  protected void addCall(GenericRemoteInvocationRpcCall call, Class<?> returnType) {

    getRemoteInvocationSerivceCaller().addCall(call, returnType);
  }
}
