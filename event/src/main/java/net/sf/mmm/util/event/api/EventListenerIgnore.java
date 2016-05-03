/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

/**
 * This is an implementation of {@link EventListener} that simply ignores all events.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class EventListenerIgnore implements EventListener<Object> {

  /** @see #getInstance() */
  private static final EventListenerIgnore INSTANCE = new EventListenerIgnore();

  /**
   * The constructor.
   */
  public EventListenerIgnore() {

    super();
  }

  /**
   * @param <E> is the generic type of {@link Event}s to listen.
   * @return the instance of {@link EventListenerIgnore}.
   */
  @SuppressWarnings("unchecked")
  public static <E> EventListener<E> getInstance() {

    return (EventListener<E>) INSTANCE;
  }

  @Override
  public void handleEvent(Object event) {

    // do nothing...
  }

}
