/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains the abstract base-implementation of the 
 * {@link net.sf.mmm.util.event.api event-handling API}.
 * <a name="documentation"></a><h2>Event-Util Base</h2>
 * If you want to implement a class that produces {@link net.sf.mmm.util.event.api.Event events} 
 * and wants to
 * {@link net.sf.mmm.util.event.base.AbstractEventSource#fireEvent(net.sf.mmm.util.event.api.Event) send} 
 * them to all 
 * {@link net.sf.mmm.util.event.api.EventSource#addListener(net.sf.mmm.util.event.api.EventListener) registered}
 * {@link net.sf.mmm.util.event.api.EventListener listeners} you only need to 
 * extend {@link net.sf.mmm.util.event.base.AbstractEventSource} or
 * one of its sub-classes provided in this package.
 */
package net.sf.mmm.util.event.base;

