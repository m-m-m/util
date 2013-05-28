/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

/**
 * This interface allows listeners to be registered and unregistered.<br/>
 * <b>NOTE:</b><br/>
 * For event base communication between loosely coupled components we recommend to use the event-bus pattern
 * that is properly implemented by <code>guava</code>.
 * 
 * @param <E> is the templated type of the {@link Event events} to send.
 * @param <L> is the templated type of the {@link net.sf.mmm.util.event.api.EventListener listeners} that can
 *        be {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.api.EventListener#handleEvent(Event) receive} the events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface EventSource<E extends Event, L extends EventListener<E>> {

  /**
   * This method registers a listener that is interested in {@link Event}s.
   * 
   * @param listener is the listener to add.
   */
  void addListener(L listener);

  /**
   * This method removes a listener. If the listener was not registered before this method does not do any
   * change.
   * 
   * @param listener is the listener to remove.
   * @return <code>true</code> if the given <code>listener</code> has successfully been removed,
   *         <code>false</code> if the <code>listener</code> was NOT {@link #addListener(EventListener)
   *         registered}.
   */
  boolean removeListener(L listener);

}
