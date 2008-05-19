/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.event.api.Event;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.base.AbstractEventSource;

/**
 * This is the test-case for {@link AbstractEventSource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractEventSourceTest {

  @Test
  public void testEvent() {

    MyEventSource source = new MyEventSource();
    MyEventListener listener1 = new MyEventListener();
    source.addListener(listener1);
    // listener 1 added, fire event 1
    MyEvent event1 = new MyEvent();
    source.fireEvent(event1);
    assertEquals(1, listener1.getEvents().size());
    assertEquals(event1, listener1.getEvents().get(0));
    MyEventListener listener2 = new MyEventListener();
    source.addListener(listener2);
    // listener 2 added, fire event 2
    MyEvent event2 = new MyEvent();
    source.fireEvent(event2);
    assertEquals(2, listener1.getEvents().size());
    assertEquals(event2, listener1.getEvents().get(1));
    assertEquals(1, listener2.getEvents().size());
    assertEquals(event2, listener2.getEvents().get(0));
    assertTrue(source.removeListener(listener1));
    // listener 1 removed, fire event 3
    MyEvent event3 = new MyEvent();
    source.fireEvent(event3);
    assertEquals(2, listener1.getEvents().size());
    assertEquals(2, listener2.getEvents().size());
    assertEquals(event3, listener2.getEvents().get(1));
    assertTrue(source.removeListener(listener2));
    // all listeners removed, fire event 4
    MyEvent event4 = new MyEvent();
    source.fireEvent(event4);
    assertEquals(2, listener1.getEvents().size());
    assertEquals(2, listener2.getEvents().size());
    assertFalse(listener1.getEvents().contains(event4));
    assertFalse(listener2.getEvents().contains(event4));
  }

  protected static class MyEvent implements Event {

  }

  protected static class MyEventListener implements EventListener<MyEvent> {

    private List<MyEvent> events;

    public MyEventListener() {

      super();
      this.events = new ArrayList<MyEvent>();
    }

    public void handleEvent(MyEvent event) {

      this.events.add(event);
    }

    public List<MyEvent> getEvents() {

      return this.events;
    }

  }

  protected static class MyEventSource extends AbstractEventSource<MyEvent, EventListener<MyEvent>> {

    @Override
    protected void fireEvent(MyEvent event) {

      super.fireEvent(event);
    }

  }

}
