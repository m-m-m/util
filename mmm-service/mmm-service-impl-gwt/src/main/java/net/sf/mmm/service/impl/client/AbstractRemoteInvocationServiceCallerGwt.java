/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.client;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationService;
import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallerGwt;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueGwt;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueueSettings;
import net.sf.mmm.service.api.client.RemoteInvocationServiceResultCallback;
import net.sf.mmm.service.api.client.event.RemoteInvocationServiceCallEvent;
import net.sf.mmm.service.api.client.event.RemoteInvocationServiceEvent;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationServiceCaller;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwt;
import net.sf.mmm.service.base.gwt.RemoteInvocationGenericServiceGwtAsync;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.service.api.client.RemoteInvocationServiceCaller} for GWT (Google Web-Toolkit).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationServiceCallerGwt extends AbstractRemoteInvocationServiceCaller implements
    RemoteInvocationServiceCallerGwt {

  /** @see #performRequest(RemoteInvocationServiceQueueImpl) */
  private final RemoteInvocationGenericServiceGwtAsync genericService;

  /** @see #performRequest(RemoteInvocationServiceQueueImpl) */
  private final EventBus eventBus;

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationServiceCallerGwt() {

    this(null, null);
  }

  /**
   * The constructor.
   * 
   * @param genericService is the {@link RemoteInvocationGenericServiceGwtAsync} instance.
   * @param eventBus is the {@link EventBus}.
   */
  public AbstractRemoteInvocationServiceCallerGwt(RemoteInvocationGenericServiceGwtAsync genericService,
      EventBus eventBus) {

    super();
    if (genericService == null) {
      this.genericService = GWT.create(RemoteInvocationGenericServiceGwt.class);
    } else {
      this.genericService = genericService;
    }
    this.eventBus = eventBus;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImplGwt newQueue() {

    return (RemoteInvocationServiceQueueImplGwt) super.newQueue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImplGwt newQueue(RemoteInvocationServiceQueueSettings settings) {

    // Java generics are too stupid for this - try and see...
    return (RemoteInvocationServiceQueueImplGwt) super.newQueue(settings);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteInvocationServiceQueueImplGwt getCurrentQueue() {

    return (RemoteInvocationServiceQueueImplGwt) super.getCurrentQueue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationServiceQueueImplGwt createQueue(RemoteInvocationServiceQueueSettings settings) {

    RemoteInvocationServiceQueueImplGwt currentQueue = getCurrentQueue();
    if (currentQueue == null) {
      return new RemoteInvocationServiceQueueImplGwt(settings);
    } else {
      return new RemoteInvocationServiceQueueImplGwt(settings, currentQueue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void addCall(RemoteInvocationServiceCall call, Class<?> returnType) {

    super.addCall(call, returnType);
    if (this.eventBus != null) {
      RemoteInvocationServiceCallEvent.fireEventCallQueued(this.eventBus, call);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performRequest(final RemoteInvocationGenericServiceRequest request,
      final RemoteInvocationServiceResultCallback<?>[] callbacks) {

    AsyncCallback<RemoteInvocationGenericServiceResponse> callback = new AsyncCallback<RemoteInvocationGenericServiceResponse>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(Throwable caught) {

        // TODO
        Window.alert(caught.toString());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      @SuppressWarnings({ "rawtypes", "unchecked" })
      public void onSuccess(RemoteInvocationGenericServiceResponse response) {

        if (request.getRequestId() != response.getRequestId()) {
          String source = "request-ID";
          throw new ObjectMismatchException(Integer.valueOf(response.getRequestId()), Integer.valueOf(request
              .getRequestId()), source);
        }
        if (AbstractRemoteInvocationServiceCallerGwt.this.eventBus != null) {
          RemoteInvocationServiceEvent.fireEventResponseReceived(
              AbstractRemoteInvocationServiceCallerGwt.this.eventBus, request, response);
        }
        RemoteInvocationServiceResult[] results = response.getResults();
        if (results.length != request.getCalls().length) {
          String source = "#calls/results";
          throw new ObjectMismatchException(Integer.valueOf(results.length),
              Integer.valueOf(request.getCalls().length), source);
        }
        for (int i = 0; i < results.length; i++) {
          RemoteInvocationServiceCall call = request.getCalls()[i];
          RemoteInvocationServiceResult result = results[i];
          if (AbstractRemoteInvocationServiceCallerGwt.this.eventBus != null) {
            RemoteInvocationServiceCallEvent.fireEventResultReceived(
                AbstractRemoteInvocationServiceCallerGwt.this.eventBus, call, result);
          }
          boolean complete = (i == (results.length - 1));
          callbacks[i].onResult(result, complete);
          if (AbstractRemoteInvocationServiceCallerGwt.this.eventBus != null) {
            RemoteInvocationServiceCallEvent.fireEventResultProceeded(
                AbstractRemoteInvocationServiceCallerGwt.this.eventBus, call, result);
          }
        }
        if (AbstractRemoteInvocationServiceCallerGwt.this.eventBus != null) {
          RemoteInvocationServiceEvent.fireEventResponseProceeded(
              AbstractRemoteInvocationServiceCallerGwt.this.eventBus, request, response);
        }
      }
    };

    if (this.eventBus != null) {
      RemoteInvocationServiceEvent.fireEventRequestSending(this.eventBus, request);
    }
    this.genericService.callServices(request, callback);
  }

  /**
   * This inner class is the implementation of {@link RemoteInvocationServiceQueueGwt}.
   */
  protected class RemoteInvocationServiceQueueImplGwt extends RemoteInvocationServiceQueueImpl implements
      RemoteInvocationServiceQueueGwt {

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationServiceQueueImplGwt(RemoteInvocationServiceQueueSettings settings,
        RemoteInvocationServiceQueueImplGwt parentQueue) {

      super(settings, parentQueue);
    }

    /**
     * The constructor.
     * 
     * @param settings - see {@link #getSettings()}.
     */
    public RemoteInvocationServiceQueueImplGwt(RemoteInvocationServiceQueueSettings settings) {

      super(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <SERVICE extends RemoteInvocationService, RESULT> SERVICE getServiceClient(Class<SERVICE> serviceInterface,
        Class<RESULT> returnType, AsyncCallback<? extends RESULT> callback) {

      @SuppressWarnings({ "unchecked", "rawtypes" })
      RemoteInvocationServiceResultCallback<? extends RESULT> callbackAdapter = new RemoteInvocationServiceAsyncCallbackAdapter(
          callback);
      return getServiceClient(serviceInterface, returnType, callbackAdapter);
    }

  }

  /**
   * This inner class adapts from {@link RemoteInvocationServiceResultCallback} to
   * {@link RemoteInvocationServiceCallback}.
   * 
   * @param <RESULT> is the generic type of the {@link #onResult(RemoteInvocationServiceResult, boolean)
   *        result to receive}.
   */
  private static class RemoteInvocationServiceAsyncCallbackAdapter<RESULT extends Serializable> implements
      RemoteInvocationServiceResultCallback<RESULT> {

    /** @see #onResult(RemoteInvocationServiceResult, boolean) */
    private final AsyncCallback<RESULT> delegate;

    /**
     * The constructor.
     * 
     * @param delegate is the {@link AsyncCallback} to adapt.
     */
    public RemoteInvocationServiceAsyncCallbackAdapter(AsyncCallback<RESULT> delegate) {

      super();
      this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResult(RemoteInvocationServiceResult<RESULT> result, boolean complete) {

      Throwable failure = result.getFailure();
      if (failure == null) {
        this.delegate.onSuccess(result.getResult());
      } else {
        this.delegate.onFailure(failure);
      }
    }

  }
}
