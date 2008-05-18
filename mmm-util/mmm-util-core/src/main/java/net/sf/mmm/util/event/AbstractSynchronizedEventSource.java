/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event;

import net.sf.mmm.util.collection.QueueFactory;

/**
 * This class extends {@link AbstractEventSource} with the ability of
 * synchronization and therefore is thread-safe.
 * 
 * @param <E> is the templated type of the events to send.
 * @param <L> is the templated type of the listeners that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListener#handleEvent(Event) receive}
 *        the sent events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSynchronizedEventSource<E extends Event, L extends EventListener<E>>
    extends AbstractEventSource<E, L> {

  /**
   * The constructor.
   */
  public AbstractSynchronizedEventSource() {

    super(QueueFactory.INSTANCE_CONCURRENT_LINKED_QUEUE);
  }

}
