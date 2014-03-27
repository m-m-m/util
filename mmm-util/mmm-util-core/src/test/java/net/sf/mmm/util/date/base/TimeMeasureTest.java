/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.util.List;

import net.sf.mmm.logging.TestLogger;
import net.sf.mmm.logging.TestLogger.LogEvent;
import net.sf.mmm.logging.TestLogger.LogLevel;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.date.api.TimeMeasure;
import net.sf.mmm.util.nls.api.IllegalCaseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test case for {@link TimeMeasure}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class TimeMeasureTest extends Assert {

  /** Operation for {@link TimeMeasure#succeed()}. */
  private static final String OPERATION_SUCCEED = "succeed";

  /** Operation for {@link TimeMeasure#fail()}. */
  private static final String OPERATION_FAIL = "fail";

  /** Operation for {@link TimeMeasure#failIfNotSucceeded()}. */
  private static final String OPERATION_FAIL_IF_NOT_SUCCEEDED = "failIfNotSucceeded";

  /**
   * Test of {@link TimeMeasure} in combination with {@link TimeMeasure#succeed()}.
   */
  @Test
  public void testSucceed() {

    checkMeasure(OPERATION_SUCCEED);
  }

  /**
   * Test of {@link TimeMeasure} in combination with {@link TimeMeasure#fail()}.
   */
  @Test
  public void testFail() {

    checkMeasure(OPERATION_FAIL);
  }

  /**
   * Test of {@link TimeMeasure} in combination with {@link TimeMeasure#failIfNotSucceeded()}.
   */
  @Test
  public void testFailIfNotSucceeded() {

    checkMeasure(OPERATION_FAIL_IF_NOT_SUCCEEDED);
  }

  /**
   * Test of {@link TimeMeasure} with illegal usage of operations that require
   * {@link TimeMeasure#isCompleted() completed state}.
   */
  @Test
  public void testIllegalUseBeforeComplete() {

    TimeMeasure timeMeasure = new TimeMeasure();

    try {
      timeMeasure.getDurationAsString();
      ExceptionHelper.failExceptionExpected();
    } catch (IllegalStateException e) {
      // expected...
    }

    try {
      timeMeasure.getDurationInNanos();
      ExceptionHelper.failExceptionExpected();
    } catch (IllegalStateException e) {
      // expected...
    }
  }

  /**
   * Performs the main test of {@link TimeMeasure}.
   *
   * @param operation is the operation used to complete the {@link TimeMeasure}.
   */
  private void checkMeasure(String operation) {

    long outerStart = System.nanoTime();
    TimeMeasure measure = new TimeMeasure();
    long innerStart = System.nanoTime();
    assertFalse(measure.isCompleted());
    assertFalse(measure.isFailure());
    assertFalse(measure.isSuccess());
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }
    long innerEnd = System.nanoTime();
    if (OPERATION_SUCCEED.equals(operation)) {
      measure.succeed();
    } else if (OPERATION_FAIL.equals(operation)) {
      measure.fail();
    } else if (OPERATION_FAIL_IF_NOT_SUCCEEDED.equals(operation)) {
      measure.failIfNotSucceeded();
    } else {
      throw new IllegalCaseException(operation);
    }
    long outerEnd = System.nanoTime();
    assertTrue(measure.isCompleted());
    if (OPERATION_SUCCEED.equals(operation)) {
      assertFalse(measure.isFailure());
      assertTrue(measure.isSuccess());
      measure.failIfNotSucceeded();
      assertFalse(measure.isFailure());
      assertTrue(measure.isSuccess());
    } else {
      assertTrue(measure.isFailure());
      assertFalse(measure.isSuccess());
    }
    long innerDuration = innerEnd - innerStart;
    long outerDuration = outerEnd - outerStart;
    long measureDuration = measure.getDurationInNanos();
    assertTrue(outerDuration >= measureDuration);
    assertTrue(innerDuration <= measureDuration);
    String expectedDuration = DurationUtilImpl.getInstance().formatNanoseconds(measureDuration);
    assertEquals(expectedDuration, measure.getDurationAsString());
    TestLogger testLogger = new TestLogger();
    String measuredOperation = "foo";
    measure.log(testLogger, measuredOperation);
    List<LogEvent> eventList = testLogger.getEventList();
    assertEquals(1, eventList.size());
    LogEvent logEvent = eventList.get(0);
    LogLevel level;
    String message;
    if (OPERATION_SUCCEED.equals(operation)) {
      level = LogLevel.INFO;
      message = "Operation {} succeeded after duration of {}";
    } else {
      level = LogLevel.WARNING;
      message = "Operation {} failed after duration of {}";
    }
    assertEquals(level, logEvent.getLevel());
    Object[] arguments = logEvent.getArguments();
    assertEquals(2, arguments.length);
    assertEquals(measuredOperation, arguments[0]);
    assertEquals(expectedDuration, arguments[1]);
    assertEquals(message, logEvent.getMessage());
    assertNull(logEvent.getThrowable());
  }

}
