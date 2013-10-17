/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.logging.TestLogger;
import net.sf.mmm.util.event.api.Event;
import net.sf.mmm.util.event.api.EventListener;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * This is the test-case for {@link AbstractEventSource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractEventSourceTest {

  /**
   * Tests {@link AbstractEventSource#addListener(EventListener)}, {@link AbstractEventSource#fireEvent(Event)} and
   * {@link AbstractEventSource#removeListener(EventListener)}.
   */
  @Test
  public void testEvent() {

    MyEventSource source = new MyEventSource();
    TestLogger testLogger = source.getLogger();
    source.initialize();

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

    // test error handling...
    EvilEventListener evilListener = new EvilEventListener();
    source.addListener(evilListener);
    MyEvent event5 = new MyEvent();
    // just to be sure ...
    testLogger.getEventList().clear();
    source.fireEvent(event5);
    TestLogger.LogEvent logEntry = testLogger.getEventList().get(0);
    Assert.assertSame(EvilEventListener.error, logEntry.throwable);
  }

  protected static class MyEvent implements Event {

  }

  protected static class EvilEventListener implements EventListener<MyEvent> {

    static final IllegalStateException error = new IllegalStateException("this is only a test");

    public void handleEvent(MyEvent event) {

      throw EvilEventListener.error;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return getClass().getSimpleName();
    }
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Logger createLogger() {

      return new TestLogger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TestLogger getLogger() {

      return (TestLogger) super.getLogger();
    }

  }

}
