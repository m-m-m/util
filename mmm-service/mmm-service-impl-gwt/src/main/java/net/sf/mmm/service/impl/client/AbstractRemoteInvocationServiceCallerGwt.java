/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.client;

import net.sf.mmm.service.api.RemoteInvocationServiceContext;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceResult;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCaller;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwt;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwtAsync;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCaller} for GWT (Google Web-Toolkit).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCallerGwt extends AbstractRemoteInvocationServiceCaller {

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
  protected void performRequest(final RemoteInvocationGenericServiceRequest request,
      final RemoteInvocationServiceCallback<?>[] callbacks) {

    AsyncCallback<RemoteInvocationGenericServiceResponse> callback = new AsyncCallback<RemoteInvocationGenericServiceResponse>() {

      /**
       * {@inheritDoc}
       */
      public void onFailure(Throwable caught) {

        // TODO
        Window.alert(caught.toString());
      }

      /**
       * {@inheritDoc}
       */
      @SuppressWarnings({ "rawtypes", "unchecked" })
      public void onSuccess(RemoteInvocationGenericServiceResponse response) {

        if (request.getRequestId() != response.getRequestId()) {
          String source = "request-ID";
          throw new ObjectMismatchException(Integer.valueOf(response.getRequestId()), Integer.valueOf(request
              .getRequestId()), source);
        }
        RemoteInvocationServiceResult[] results = response.getResults();
        if (results.length != request.getCalls().length) {
          String source = "#calls/results";
          throw new ObjectMismatchException(Integer.valueOf(results.length),
              Integer.valueOf(request.getCalls().length), source);
        }
        for (int i = 0; i < results.length; i++) {
          RemoteInvocationServiceResult risResult = results[i];
          RemoteInvocationServiceCallback risCallback = callbacks[i];
          RemoteInvocationServiceContext context = risResult.getContext();
          Throwable failure = risResult.getFailure();
          if (failure != null) {
            risCallback.onFailure(failure, context);
          } else {
            boolean complete = (i == (results.length - 1));
            risCallback.onSuccess(risResult.getResult(), context, complete);
          }
        }
      }
    };

    this.genericService.callServices(request, callback);
  }
}
