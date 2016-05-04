/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for an event bus. An event bus is a central place for {@link #sendEvent(Object) sending},
 * {@link #addListener(Class, EventListener) listening} to and {@link EventListener#handleEvent(Object) receiving}
 * {@link Event}s. The {@link EventBus} allows to communicate between loosely coupled components in a smart and
 * efficient way:
 * <ul>
 * <li>A component sending events only needs to know the {@link EventBus} but not the receivers of the events.</li>
 * <li>A component receiving events only needs to know the {@link EventBus} but not the sender of the events.</li>
 * </ul>
 * This way components can communicate via {@link Event}s without compile time dependency between them. All they need to
 * see is the {@link EventBus} and the {@link Event} itself. <br>
 * This interface exists only for the high abstraction needed for {@code mmm-client}. If you want to write a portable
 * client application that can run in different environments (GWT, JavaFx, etc.) then you should use this simplified but
 * normalized API. Otherwise you may want to use an external event bus implementation directly such as
 * <a href="https://code.google.com/p/guava-libraries/wiki/EventBusExplained">guava event bus</a>. <br>
 * <b>NOTE:</b><br>
 * The loose coupling makes flows less easy to see, understand and debug. You should only consider this approach for
 * components that should be decoupled by design. Do not get confused by the beauty of the event-bus pattern and avoid
 * using it where straight method calls should be preferred. <br>
 * E.g. if you have a user-interface with a navigation sub-dialog and various other dialogs they should communicate via
 * {@link EventBus} to update their views accordingly. However, a business component responsible to read and write
 * addresses may get the requirement that in case of a change of an address some logic from the domain of another
 * component should be invoked and that might even reject the change. In the latter case {@link EventBus} is the wrong
 * choice.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@ComponentSpecification
public interface EventBus {

  /**
   * This method sends an event to all {@link #addListener(Class, EventListener) registered listeners}.
   *
   * @param <E> is the generic type of the {@link Event} to send. May be any Object such as a {@link String}. However in
   *        most cases it is recommended to create real {@link Event} classes.
   * @param event is the event to send. May be any Object such as a {@link String}. However in most cases it is
   *        recommended to create real {@link Event} classes.
   */
  <E /* extends Event */> void sendEvent(E event);

  /**
   * This method registers a listener that is interested in {@link Event}s.
   *
   * @param <E> is the generic type of the {@link Event}s to listen to. May be any Object such as a {@link String}.
   *        However in most cases it is recommended to create real {@link Event} classes.
   * @param eventType is the type of the {@link Event}s to listen to (including all derived types). In limited
   *        environments such as GWT, interfaces might not be supported.
   * @param listener is the listener to add.
   */
  <E /* extends Event */> void addListener(Class<E> eventType, EventListener<E> listener);

  /**
   * This method removes a listener. If the listener was not registered before this method will have no effect.
   *
   * @param listener is the listener to remove.
   * @return {@code true} if the given {@code listener} has successfully been removed, {@code false} if the
   *         {@code listener} was NOT {@link #addListener(Class, EventListener) registered}.
   */
  boolean removeListener(EventListener<?> listener);

}
