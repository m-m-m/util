/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.gwt;

import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the GWT service-client stub of {@link RemoteInvocationGenericServiceGwt}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface RemoteInvocationGenericServiceGwtAsync {

  /**
   * @see RemoteInvocationGenericServiceGwt#callServices(RemoteInvocationGenericServiceRequest)
   * 
   * @param request is the {@link RemoteInvocationGenericServiceRequest}.
   * @param callback is the {@link AsyncCallback} for receiving the
   *        {@link RemoteInvocationGenericServiceResponse response}.
   */
  void callServices(RemoteInvocationGenericServiceRequest request,
      AsyncCallback<RemoteInvocationGenericServiceResponse> callback);

}
