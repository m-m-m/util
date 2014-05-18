/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for an event bus. An event bus is a central place for {@link #sendEvent(Object)
 * sending}, {@link #addListener(Class, EventListener) listening} and
 * {@link EventListener#handleEvent(Object) receiving} {@link Event}s.
 *
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@ComponentSpecification
public interface EventBus {

  /**
   * This method sends an event to all {@link #addListener(Class, EventListener) registered listeners}.
   *
   * @param event is the event to send. May be any Object such as a {@link String}. However in most cases it
   *        is recommended to create real {@link Event} classes.
   */
  void sendEvent(Object event);

  /**
   * This method registers a listener that is interested in {@link Event}s.
   *
   * @param <E> is the generic type of the {@link Event}s to listen to. May be any Object such as a
   *        {@link String}. However in most cases it is recommended to create real {@link Event} classes.
   * @param eventType is the type of the {@link Event}s to listen to.
   * @param listener is the listener to add.
   */
  <E /* extends Event */> void addListener(Class<E> eventType, EventListener<E> listener);

  /**
   * This method removes a listener. If the listener was not registered before this method will have no
   * effect.
   *
   * @param listener is the listener to remove.
   * @return <code>true</code> if the given <code>listener</code> has successfully been removed,
   *         <code>false</code> if the <code>listener</code> was NOT
   *         {@link #addListener(Class, EventListener) registered}.
   */
  boolean removeListener(EventListener<?> listener);

}
