/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * This is an implementation of the {@link org.slf4j.Logger} interface that stores all log-entries in a
 * {@link #getEventList() list}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TestLogger extends MarkerIgnoringBase {

  /** UID for serialization. */
  private static final long serialVersionUID = 1819143702149344952L;

  /** @see #getEventList() */
  private final List<LogEvent> eventList;

  /**
   * The constructor.
   */
  public TestLogger() {

    super();
    this.eventList = new ArrayList<>();
  }

  /**
   * @return the eventList
   */
  public List<LogEvent> getEventList() {

    return this.eventList;
  }

  @Override
  public void debug(String s) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null));
  }

  @Override
  public void debug(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, obj));
  }

  @Override
  public void debug(String s, Object... aobj) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, aobj));
  }

  @Override
  public void debug(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, throwable));
  }

  @Override
  public void debug(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, obj, obj1));
  }

  @Override
  public void error(String s) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null));
  }

  @Override
  public void error(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, obj));
  }

  @Override
  public void error(String s, Object... aobj) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, aobj));
  }

  @Override
  public void error(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, throwable));
  }

  @Override
  public void error(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, obj, obj1));
  }

  @Override
  public void info(String s) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null));
  }

  @Override
  public void info(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, obj));
  }

  @Override
  public void info(String s, Object... aobj) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, aobj));
  }

  @Override
  public void info(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, throwable));
  }

  @Override
  public void info(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, obj, obj1));
  }

  @Override
  public boolean isDebugEnabled() {

    return true;
  }

  @Override
  public boolean isErrorEnabled() {

    return true;
  }

  @Override
  public boolean isInfoEnabled() {

    return true;
  }

  @Override
  public boolean isTraceEnabled() {

    return true;
  }

  @Override
  public boolean isWarnEnabled() {

    return true;
  }

  @Override
  public void trace(String s) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null));
  }

  @Override
  public void trace(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, obj));
  }

  @Override
  public void trace(String s, Object... aobj) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, aobj));
  }

  @Override
  public void trace(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, throwable));
  }

  @Override
  public void trace(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, obj, obj1));
  }

  @Override
  public void warn(String s) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null));
  }

  @Override
  public void warn(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, obj));
  }

  @Override
  public void warn(String s, Object... aobj) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, aobj));
  }

  @Override
  public void warn(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, throwable));
  }

  @Override
  public void warn(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, obj, obj1));
  }

  /**
   * Enumerats the loglevels.
   */
  public static enum LogLevel {

    /** @see org.slf4j.Logger#trace(String) */
    TRACE,

    /** @see org.slf4j.Logger#debug(String) */
    DEBUG,

    /** @see org.slf4j.Logger#warn(String) */
    WARNING,

    /** @see org.slf4j.Logger#info(String) */
    INFO,

    /** @see org.slf4j.Logger#error(String) */
    ERROR
  }

  /**
   * An event that tracks the log-entry.
   */
  public static class LogEvent implements Serializable {

    /** UID for serialization. */
    private static final long serialVersionUID = 6499934887162891533L;

    /** The {@link LogLevel}. */
    private final LogLevel level;

    /** The logged message. */
    private final String message;

    /** The optional arguments. */
    private final Object[] arguments;

    /** The optional {@link Throwable}. */
    private final Throwable throwable;

    /**
     * The constructor.
     *
     * @param arguments are the {@link #arguments}.
     * @param level is the {@link #level}.
     * @param message is the {@link #message}.
     * @param throwable is the {@link #throwable}.
     */
    @SuppressWarnings("all")
    public LogEvent(LogLevel level, String message, Throwable throwable, Object... arguments) {

      super();
      this.level = level;
      this.message = message;
      this.throwable = throwable;
      this.arguments = arguments;
    }

    /**
     * @return the {@link LogLevel}.
     */
    public LogLevel getLevel() {

      return this.level;
    }

    /**
     * @return the log message.
     */
    public String getMessage() {

      return this.message;
    }

    /**
     * @return the {@link Throwable} or {@code null}.
     */
    public Throwable getThrowable() {

      return this.throwable;
    }

    /**
     * @return the arguments.
     */
    public Object[] getArguments() {

      return this.arguments;
    }
  }

}
