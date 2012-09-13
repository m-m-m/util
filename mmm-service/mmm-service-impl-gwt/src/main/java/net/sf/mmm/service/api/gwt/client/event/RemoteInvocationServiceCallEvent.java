/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.gwt.client.event;

import net.sf.mmm.service.api.RemoteInvocationServiceResult;
import net.sf.mmm.service.base.RemoteInvocationServiceCall;

import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;

/**
 * This is the {@link GwtEvent} send when {@link net.sf.mmm.service.api.RemoteInvocationService} are
 * requested, according results are received or proceeded.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceCallEvent extends GwtEvent<RemoteInvocationServiceCallEventHandler> {

  /** @see #getAssociatedType() */
  public static final Type<RemoteInvocationServiceCallEventHandler> TYPE = new Type<RemoteInvocationServiceCallEventHandler>();

  /** @see #getCall() */
  private final RemoteInvocationServiceCall call;

  /** @see #getResult() */
  private final RemoteInvocationServiceResult<?> result;

  /** @see #getState() */
  private final ServiceCallState state;

  /**
   * The constructor.
   * 
   * @param state - see {@link #getState()}.
   * @param call - see #getCall()
   * @param result - see #getResult()
   */
  protected RemoteInvocationServiceCallEvent(ServiceCallState state, RemoteInvocationServiceCall call,
      RemoteInvocationServiceResult<?> result) {

    super();
    this.call = call;
    this.result = result;
    this.state = state;
  }

  /**
   * @return the {@link ServiceCallState}.
   */
  public ServiceCallState getState() {

    return this.state;
  }

  /**
   * @return the {@link RemoteInvocationServiceCall}.
   */
  public RemoteInvocationServiceCall getCall() {

    return this.call;
  }

  /**
   * @return the {@link RemoteInvocationServiceResult} or <code>null</code> if NOT available (e.g. the
   *         {@link #getState() state} is {@link ServiceCallState#CALL_QUEUED}).
   */
  public RemoteInvocationServiceResult<?> getResult() {

    return this.result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type<RemoteInvocationServiceCallEventHandler> getAssociatedType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void dispatch(RemoteInvocationServiceCallEventHandler handler) {

    handler.onServiceEvent(this);
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceCallState#CALL_QUEUED}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param call is the {@link #getCall()}.
   */
  public static void fireEventCallQueued(EventBus eventBus, RemoteInvocationServiceCall call) {

    eventBus.fireEvent(new RemoteInvocationServiceCallEvent(ServiceCallState.CALL_QUEUED, call, null));
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceCallState#RESULT_RECEIVED}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param call is the {@link #getCall() call}.
   * @param result is the {@link #getResult() result}.
   */
  public static void fireEventResultReceived(EventBus eventBus, RemoteInvocationServiceCall call,
      RemoteInvocationServiceResult<?> result) {

    eventBus.fireEvent(new RemoteInvocationServiceCallEvent(ServiceCallState.RESULT_RECEIVED, call, result));
  }

  /**
   * {@link EventBus#fireEvent(com.google.web.bindery.event.shared.Event) fire} event for
   * {@link ServiceCallState#RESULT_PROCEEDED}.
   * 
   * @param eventBus is the {@link EventBus}.
   * @param call is the {@link #getCall() call}.
   * @param result is the {@link #getResult() result}.
   */
  public static void fireEventResultProceeded(EventBus eventBus, RemoteInvocationServiceCall call,
      RemoteInvocationServiceResult<?> result) {

    eventBus.fireEvent(new RemoteInvocationServiceCallEvent(ServiceCallState.RESULT_PROCEEDED, call, result));
  }

  /** This enum contains the available states. */
  public static enum ServiceCallState {

    /**
     * Sending {@link net.sf.mmm.service.base.RemoteInvocationGenericServiceRequest}.
     */
    CALL_QUEUED,

    /**
     * {@link net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse} has been received (and
     * de-serialized).
     */
    RESULT_RECEIVED,

    /**
     * {@link net.sf.mmm.service.base.RemoteInvocationGenericServiceResponse} has been proceeded.
     */
    RESULT_PROCEEDED,

  }

}
