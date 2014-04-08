/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.gwt.rpc.client;

import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwt;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwtAsync;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.client.AbstractRemoteInvocationServiceCallerWithClientMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.service.api.rpc.client.RemoteInvocationServiceCaller} for GWT (Google Web-Toolkit).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCallerGwt extends
    AbstractRemoteInvocationServiceCallerWithClientMap {

  /** @see #performRequest(GenericRemoteInvocationRpcRequest, RequestBuilder) */
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
  protected void performRequest(final GenericRemoteInvocationRpcRequest request, final RequestBuilder builder) {

    AsyncCallback<GenericRemoteInvocationRpcResponse> callback = new AsyncCallback<GenericRemoteInvocationRpcResponse>() {

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
      public void onSuccess(GenericRemoteInvocationRpcResponse response) {

        handleResponse(request, builder, response);
      }
    };

    this.genericService.callServices(request, callback);
  }

}
