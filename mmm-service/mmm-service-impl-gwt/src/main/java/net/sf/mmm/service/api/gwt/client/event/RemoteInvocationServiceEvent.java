/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.gwt.client.event;

import net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest;
import net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse;

import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;

/**
 * This is the {@link GwtEvent} send when {@link net.sf.mmm.service.api.RemoteInvocationService} are
 * requested, according results are received or proceeded.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceEvent extends GwtEvent<RemoteInvocationServiceEventHandler> {

  /** @see #getAssociatedType() */
  public static final Type<RemoteInvocationServiceEventHandler> TYPE = new Type<RemoteInvocationServiceEventHandler>();

  /** @see #getRequest() */
  private final RemoteInvocationGenericServiceRequest request;

  /** @see #getResponse() */
  private final RemoteInvocationGenericServiceResponse response;

  /** @see #getState() */
  private final ServiceState state;

  /**
   * The constructor.
   * 
   * @param state - see {@link #getState()}.
   * @param request - see {@link #getRequest()}.
   * @param response - see {@link #getResponse()}.
   */
  protected RemoteInvocationServiceEvent(ServiceState state, RemoteInvocationGenericServiceRequest request,
      RemoteInvocationGenericServiceResponse response) {

    super();
    this.request = request;
    this.response = response;
    this.state = state;
  }

  /**
   * @return the {@link ServiceState}.
   */
  public ServiceState getState() {

    return this.state;
  }

  /**
   * @return the {@link RemoteInvocationGenericServiceRequest}.
   */
  public RemoteInvocationGenericServiceRequest getRequest() {

    return this.request;
  }

  /**
   * @return the {@link RemoteInvocationGenericServiceResponse} or <code>null</code> if NOT available (e.g.
   *         the {@link #getState() state} is {@link ServiceState#REQUEST_SENDING}).
   */
  public RemoteInvocationGenericServiceResponse getResponse() {

    return this.response;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type<RemoteInvocationServiceEventHandler> getAssociatedType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void dispatch(RemoteInvocationServiceEventHandler handler) {

    handler.onServiceEvent(this);
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceState#REQUEST_SENDING}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param request is the {@link #getRequest()}.
   */
  public static void fireEventRequestSending(EventBus eventBus, RemoteInvocationGenericServiceRequest request) {

    eventBus.fireEvent(new RemoteInvocationServiceEvent(ServiceState.REQUEST_SENDING, request, null));
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceState#RESPONSE_RECEIVED}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param request is the {@link #getRequest() request}.
   * @param response is the {@link #getResponse() response}.
   */
  public static void fireEventResponseReceived(EventBus eventBus, RemoteInvocationGenericServiceRequest request,
      RemoteInvocationGenericServiceResponse response) {

    eventBus.fireEvent(new RemoteInvocationServiceEvent(ServiceState.RESPONSE_RECEIVED, request, response));
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceState#RESPONSE_PROCEEDED}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param request is the {@link #getRequest() request}.
   * @param response is the {@link #getResponse() response}.
   */
  public static void fireEventResponseProceeded(EventBus eventBus, RemoteInvocationGenericServiceRequest request,
      RemoteInvocationGenericServiceResponse response) {

    eventBus.fireEvent(new RemoteInvocationServiceEvent(ServiceState.RESPONSE_PROCEEDED, request, response));
  }

  /** This enum contains the available states. */
  public static enum ServiceState {

    /**
     * Sending {@link RemoteInvocationGenericServiceRequest}.
     */
    REQUEST_SENDING,

    /**
     * {@link RemoteInvocationGenericServiceResponse} has been received (and de-serialized).
     */
    RESPONSE_RECEIVED,

    /**
     * {@link RemoteInvocationGenericServiceResponse} has been proceeded.
     */
    RESPONSE_PROCEEDED,

  }

}
