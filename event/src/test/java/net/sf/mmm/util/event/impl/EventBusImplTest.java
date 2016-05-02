/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.util.event.api.EventBus;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.exception.api.GlobalExceptionHandler;

import org.junit.Test;

/**
 * This is the test-case for {@link EventBus} and its implementation.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class EventBusImplTest extends EventBusTest {

  /**
   * @return the {@link EventBus} instance to test.
   */
  @Override
  protected EventBus getEventBus() {

    EventBusImpl eventBus = new EventBusImpl();
    eventBus.initialize();
    return eventBus;
  }

  /**
   * Tests the error handling of {@link EventBusImpl}.
   */
  @Test
  public void testErrors() {

    // given
    EventBusImpl eventBusImpl = new EventBusImpl();

    final List<Throwable> errorList = new LinkedList<>();
    final String errorEvent = "error";
    final RuntimeException error = new IllegalStateException(errorEvent);
    GlobalExceptionHandler errorHandler = new GlobalExceptionHandler() {

      @Override
      public void handleErrors(Object context, Throwable... errors) {

        assertNotNull(errors);
        assertSame(errorEvent, context);
        assertEquals(1, errors.length);
        assertEquals(error, errors[0]);
        errorList.add(errors[0]);
      }
    };
    eventBusImpl.setGlobalExceptionHandler(errorHandler);
    eventBusImpl.initialize();
    final EventBus eventBus = eventBusImpl;

    EventListener<String> listener = new EventListener<String>() {

      @Override
      public void handleEvent(String event) {

        if (event == errorEvent) {
          throw error;
        }
      }
    };
    eventBus.addListener(String.class, listener);

    // when + then
    eventBus.sendEvent("foo");
    assertEquals(0, errorList.size());
    eventBus.sendEvent(errorEvent);
    assertEquals(1, errorList.size());
    eventBus.sendEvent("bar");
    assertEquals(1, errorList.size());
    eventBus.sendEvent(errorEvent);
    assertEquals(2, errorList.size());
  }

}
