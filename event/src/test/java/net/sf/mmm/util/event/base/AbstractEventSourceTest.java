/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.event.api.Event;
import net.sf.mmm.util.event.api.EventListener;

/**
 * This is the test-case for {@link AbstractEventSource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractEventSourceTest extends Assertions {

  /**
   * Tests {@link AbstractEventSource#addListener(EventListener)}, {@link AbstractEventSource#fireEvent(Event)} and
   * {@link AbstractEventSource#removeListener(EventListener)}.
   */
  @Test
  public void testEvent() {

    MyEventSource source = new MyEventSource();
    source.initialize();

    MyEventListener listener1 = new MyEventListener();
    source.addListener(listener1);
    // listener 1 added, fire event 1
    MyEvent event1 = new MyEvent();
    source.fireEvent(event1);
    assertThat(listener1.getEvents()).hasSize(1);
    assertThat(listener1.getEvents().get(0)).isEqualTo(event1);
    MyEventListener listener2 = new MyEventListener();
    source.addListener(listener2);
    // listener 2 added, fire event 2
    MyEvent event2 = new MyEvent();
    source.fireEvent(event2);
    assertThat(listener1.getEvents()).hasSize(2).containsExactly(event1, event2);
    assertThat(listener2.getEvents()).hasSize(1).containsExactly(event2);
    assertThat(source.removeListener(listener1)).isTrue();
    // listener 1 removed, fire event 3
    MyEvent event3 = new MyEvent();
    source.fireEvent(event3);
    assertThat(listener1.getEvents()).hasSize(2);
    assertThat(listener2.getEvents()).hasSize(2).containsExactly(event2, event3);
    assertThat(source.removeListener(listener2)).isTrue();
    // all listeners removed, fire event 4
    MyEvent event4 = new MyEvent();
    source.fireEvent(event4);
    assertThat(listener1.getEvents()).hasSize(2).doesNotContain(event4).containsExactly(event1, event2);
    assertThat(listener2.getEvents()).hasSize(2).doesNotContain(event4).containsExactly(event2, event3);

    // test error handling...
    EvilEventListener evilListener = new EvilEventListener();
    source.addListener(evilListener);
    MyEvent event5 = new MyEvent();
    // just to be sure ...
    // testLogger.getEventList().clear();
    source.fireEvent(event5);
    // TestLogger.LogEvent logEntry = testLogger.getEventList().get(0);
    // assertThat(logEntry.getThrowable()).isSameAs(EvilEventListener.error);
  }

  protected static class MyEvent implements Event {

  }

  protected static class EvilEventListener implements EventListener<MyEvent> {

    static final IllegalStateException error = new IllegalStateException("this is only a test");

    @Override
    public void handleEvent(MyEvent event) {

      throw EvilEventListener.error;
    }

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

    @Override
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
