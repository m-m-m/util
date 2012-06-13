/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.client;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;

/**
 * This is the abstract base implementation for the service-client stubs of {@link RemoteInvocationService}s.
 * 
 * @author hohwille
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
   * @see AbstractRemoteInvocationServiceCaller#addCall(RemoteInvocationServiceCall, Class)
   * 
   * @param call is the {@link RemoteInvocationServiceCall} to add.
   * @param returnType is the {@link java.lang.reflect.Method#getReturnType() return type} of the invoked
   *        {@link java.lang.reflect.Method}.
   */
  protected void addCall(RemoteInvocationServiceCall call, Class<?> returnType) {

    getRemoteInvocationSerivceCaller().addCall(call, returnType);
  }
}
