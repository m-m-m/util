/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.impl;

import javax.inject.Named;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.api.QueueFactory;
import net.sf.mmm.util.event.base.AbstractEventBus;

/**
 * This is the default implementation of {@link net.sf.mmm.util.event.api.EventBus}. It is compatible to work in limited
 * environments such as GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@Named
public class EventBusImpl extends AbstractEventBus {

  private volatile boolean dispatching;

  /**
   * The constructor.
   */
  public EventBusImpl() {

    super();
    this.dispatching = false;
  }

  /**
   * The constructor. See parent constructor for parameter details.
   *
   * @param queueFactory is the {@link QueueFactory} to use.
   * @param mapFactory is {@link MapFactory} to use.
   */
  public EventBusImpl(QueueFactory queueFactory, MapFactory<?> mapFactory) {

    super(queueFactory, mapFactory);
  }

  @Override
  protected void triggerDispatchEvents() {

    // not synchronized - in the worst case we call dispatchEvents() parallel
    // since we are using a concurrent queue, events will get properly dispatched
    // in the worst case and event could overtake if all goes badly wrong...
    if (!this.dispatching) {
      this.dispatching = true;
      dispatchEvents();
      this.dispatching = false;
    }
  }

}
