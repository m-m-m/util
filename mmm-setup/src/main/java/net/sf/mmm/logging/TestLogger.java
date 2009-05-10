/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.logging;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * TODO: this class ...
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
    this.eventList = new ArrayList<LogEvent>();
  }

  /**
   * @return the eventList
   */
  public List<LogEvent> getEventList() {

    return this.eventList;
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null));
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, obj));
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object[] aobj) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, aobj));
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, throwable));
  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.DEBUG, s, null, obj, obj1));
  }

  /**
   * {@inheritDoc}
   */
  public void error(String s) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null));
  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, obj));
  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object[] aobj) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, aobj));
  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, throwable));
  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.ERROR, s, null, obj, obj1));
  }

  /**
   * {@inheritDoc}
   */
  public void info(String s) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null));
  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, obj));
  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object[] aobj) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, aobj));
  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, throwable));
  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.INFO, s, null, obj, obj1));
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDebugEnabled() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isErrorEnabled() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInfoEnabled() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTraceEnabled() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isWarnEnabled() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null));
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, obj));
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object[] aobj) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, aobj));
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, throwable));
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.TRACE, s, null, obj, obj1));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object obj) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, obj));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object[] aobj) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, aobj));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Throwable throwable) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, throwable));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object obj, Object obj1) {

    this.eventList.add(new LogEvent(LogLevel.WARNING, s, null, obj, obj1));
  }

  @SuppressWarnings("all")
  private static enum LogLevel {

    TRACE,

    DEBUG,

    WARNING,

    INFO,

    ERROR
  }

  @SuppressWarnings("all")
  public static class LogEvent {

    /**
     * The constructor.
     * 
     * @param arguments
     * @param level
     * @param message
     * @param throwable
     */
    public LogEvent(LogLevel level, String message, Throwable throwable, Object... arguments) {

      super();
      this.level = level;
      this.message = message;
      this.throwable = throwable;
      this.arguments = arguments;
    }

    public final LogLevel level;

    public final String message;

    public final Object[] arguments;

    public final Throwable throwable;
  }

}
