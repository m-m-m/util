/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.gwt.client;

import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCallerWithClientMap;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwt;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwtAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCaller} for GWT (Google Web-Toolkit).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCallerGwt extends
    AbstractRemoteInvocationServiceCallerWithClientMap {

  /** @see #performRequest(RemoteInvocationServiceQueueImpl) */
  private final RemoteInvocationGenericServiceGwtAsync genericService;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCallerGwt() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param genericService is the {@link RemoteInvocationGenericServiceGwtAsync} instance.
   */
  public AbstractRemoteInvocationServiceCallerGwt(RemoteInvocationGenericServiceGwtAsync genericService) {

    super();
    if (genericService == null) {
      this.genericService = GWT.create(RemoteInvocationGenericServiceGwt.class);
    } else {
      this.genericService = genericService;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performRequest(final RemoteInvocationGenericServiceRequest request, final RequestBuilder builder) {

    AsyncCallback<RemoteInvocationGenericServiceResponse> callback = new AsyncCallback<RemoteInvocationGenericServiceResponse>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(Throwable caught) {

        handleFailure(request, builder, caught);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onSuccess(RemoteInvocationGenericServiceResponse response) {

        handleResponse(request, builder, response);
      }
    };

    this.genericService.callServices(request, callback);
  }

}
