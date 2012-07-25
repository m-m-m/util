/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import org.slf4j.helpers.MarkerIgnoringBase;

import com.allen_sauer.gwt.log.client.Log;

/**
 * This is a SLF4J {@link org.slf4j.Logger} for GWT clients. It adapts to {@link Log}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GwtLogger extends MarkerIgnoringBase {

  /** UID for serialization. */
  private static final long serialVersionUID = 1715317043677185014L;

  /**
   * The constructor. *
   * 
   * @param name - is the {@link #getName() name} of the logger.
   */
  public GwtLogger(String name) {

    super();
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTraceEnabled() {

    return Log.isTraceEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String message) {

    Log.trace(message);
  }

  /**
   * This is a GWT compatible implementation of {@link org.slf4j.helpers.MessageFormatter}.
   * 
   * @param format is the format string (containing "{}"-place-holders).
   * @param args are the arguments to fill in.
   * @return the formatted string.
   */
  protected String format(String format, Object... args) {

    if (format == null) {
      return null;
    }
    if (args == null) {
      return format;
    }
    StringBuilder buffer = new StringBuilder();
    int argumentIndex = 0;
    int formatStartIndex = 0;
    while (formatStartIndex >= 0) {
      if (argumentIndex >= args.length) {
        // too few arguments given - append the rest of the format string
        buffer.append(format.substring(formatStartIndex));
        formatStartIndex = -1;
      } else {
        int formatEndIndex = format.indexOf("{}");
        if (formatEndIndex == -1) {
          buffer.append(format.substring(formatStartIndex));
          formatStartIndex = -1;
        } else {
          buffer.append(format.substring(formatStartIndex, formatEndIndex));
          formatStartIndex = formatEndIndex + 2;
          buffer.append(args[argumentIndex++]);
        }
      }
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String format, Object arg) {

    if (Log.isTraceEnabled()) {
      Log.trace(format(format, arg));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String format, Object arg1, Object arg2) {

    if (Log.isTraceEnabled()) {
      Log.trace(format(format, arg1, arg2));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String format, Object[] argArray) {

    if (Log.isTraceEnabled()) {
      Log.trace(format(format, argArray));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String message, Throwable t) {

    Log.trace(message, t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDebugEnabled() {

    return Log.isDebugEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String message) {

    Log.debug(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String format, Object arg) {

    if (Log.isDebugEnabled()) {
      Log.debug(format(format, arg));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String format, Object arg1, Object arg2) {

    if (Log.isDebugEnabled()) {
      Log.debug(format(format, arg1, arg2));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String format, Object[] argArray) {

    if (Log.isDebugEnabled()) {
      Log.debug(format(format, argArray));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String message, Throwable t) {

    Log.debug(message, t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInfoEnabled() {

    return Log.isInfoEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String message) {

    Log.info(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String format, Object arg) {

    if (Log.isInfoEnabled()) {
      Log.info(format(format, arg));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String format, Object arg1, Object arg2) {

    if (Log.isInfoEnabled()) {
      Log.info(format(format, arg1, arg2));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String format, Object[] argArray) {

    if (Log.isInfoEnabled()) {
      Log.info(format(format, argArray));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String message, Throwable t) {

    Log.info(message, t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWarnEnabled() {

    return Log.isWarnEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String message) {

    Log.warn(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String format, Object arg) {

    if (Log.isWarnEnabled()) {
      Log.warn(format(format, arg));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String format, Object[] argArray) {

    if (Log.isWarnEnabled()) {
      Log.warn(format(format, argArray));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String format, Object arg1, Object arg2) {

    if (Log.isWarnEnabled()) {
      Log.warn(format(format, arg1, arg2));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String message, Throwable t) {

    Log.warn(message, t);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isErrorEnabled() {

    return Log.isErrorEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String message) {

    Log.error(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String format, Object arg) {

    if (Log.isErrorEnabled()) {
      Log.error(format(format, arg));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String format, Object arg1, Object arg2) {

    if (Log.isErrorEnabled()) {
      Log.error(format(format, arg1, arg2));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String format, Object[] argArray) {

    if (Log.isErrorEnabled()) {
      Log.error(format(format, argArray));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String message, Throwable t) {

    Log.error(message, t);
  }

}
