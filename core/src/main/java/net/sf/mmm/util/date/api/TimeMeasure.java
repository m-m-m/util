/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import org.slf4j.Logger;

import net.sf.mmm.util.date.base.DurationUtilImpl;

/**
 * This class makes it very easy to do performance monitoring. It can be used as following:
 *
 * <pre>
 * {@link TimeMeasure} measure = new {@link TimeMeasure}();
 * try {
 *   doSomething();
 *   measure.{@link #succeed()};
 * } finally {
 *   measure.{@link #log(Logger, Object) log}(getLogger(), "Something");
 * }
 * </pre>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class TimeMeasure {

  /** The start time. */
  private final long start;

  /** The end time. */
  private long duration;

  /**
   * The {@link Result} if {@link #duration} time has been set and measure is completed or {@code null} if still in
   * progress.
   */
  private Result result;

  /**
   * The constructor.
   */
  public TimeMeasure() {

    super();
    this.start = Helper.getNanoTime();
    this.duration = -1;
  }

  /**
   * This method is to be called once after the operation(s) to measure has successfully completed.
   */
  public void succeed() {

    complete(Result.SUCCESS);
  }

  /**
   * This method is to be called once after the operation(s) to measure failed (e.g. in an catch block).
   */
  public void fail() {

    complete(Result.FAILURE);
  }

  /**
   * Invokes {@link #fail()} if {@link #succeed()} has not been invoked before.
   */
  public void failIfNotSucceeded() {

    if (this.result != Result.SUCCESS) {
      fail();
    }
  }

  /**
   * @return {@code true} if {@link #fail() failed}, {@code false} otherwise.
   */
  public boolean isFailure() {

    return (this.result == Result.FAILURE);
  }

  /**
   * @return {@code true} if {@link #succeed() succeeded}, {@code false} otherwise.
   */
  public boolean isSuccess() {

    return (this.result == Result.SUCCESS);
  }

  /**
   * @return {@code true} if completed ({@link #isSuccess()} or {@link #isFailure()}), {@code false} otherwise.
   */
  public boolean isCompleted() {

    return (this.result != null);
  }

  /**
   * Calls {@link #failIfNotSucceeded()} and the logs the status and {@link #getDurationAsString() duration} of this
   * measure.
   *
   * @param logger is the {@link Logger}.
   * @param operation represents the operation that was measured. Typically a {@link String}. Its
   *        {@link Object#toString() string representation} is logged and shall be a brief description of the measured
   *        operation.
   */
  public void log(Logger logger, Object operation) {

    if (!isCompleted()) {
      failIfNotSucceeded();
    }
    if (isFailure()) {
      logger.warn("Operation {} failed after duration of {}", operation, getDurationAsString());
    } else {
      logger.info("Operation {} succeeded after duration of {}", operation, getDurationAsString());
    }
  }

  /**
   * @return the duration in nanoseconds.
   * @throws IllegalStateException if not {@link #isCompleted() completed}.
   */
  public long getDurationInNanos() throws IllegalStateException {

    requireCompleted();
    return this.duration;
  }

  /**
   * @return the duration in a human readable format.
   * @throws IllegalStateException if not {@link #isCompleted() completed}.
   */
  public String getDurationAsString() throws IllegalStateException {

    requireCompleted();
    assert (this.duration >= 0);
    return DurationUtilImpl.getInstance().formatNanoseconds(this.duration);
  }

  /**
   * Throws an {@link IllegalStateException} if not {@link #isCompleted() completed}.
   */
  private void requireCompleted() {

    if (!isCompleted()) {
      throw new IllegalStateException("Not completed!");
    }
  }

  /**
   * @param finalResult is the {@link Result} to set together with the current end time.
   */
  private void complete(Result finalResult) {

    if (this.result == null) {
      this.duration = Helper.getNanoTime() - this.start;
      this.result = finalResult;
    } else {
      throw new IllegalStateException("Already completed!");
    }
  }

  /**
   * The state of the completion.
   */
  private static enum Result {

    /** @see TimeMeasure#succeed() */
    SUCCESS,

    /** @see TimeMeasure#fail() */
    FAILURE

  }

}
