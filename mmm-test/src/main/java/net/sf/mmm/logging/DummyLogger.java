/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.logging;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * This is an implementation of the {@link org.slf4j.Logger} interface that does nothing. Unlike
 * {@link org.slf4j.helpers.NOPLogger} its methods are NOT final so it can be extended for testing purposes.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyLogger extends MarkerIgnoringBase {

  /** UID for serialization. */
  private static final long serialVersionUID = -5472980908264693903L;

  /**
   * The constructor.
   *
   */
  public DummyLogger() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String s) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String s, Object obj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String s, Object[] aobj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String s, Throwable throwable) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void debug(String s, Object obj, Object obj1) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String s) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String s, Object obj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String s, Object[] aobj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String s, Throwable throwable) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void error(String s, Object obj, Object obj1) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String s) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String s, Object obj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String s, Object[] aobj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String s, Throwable throwable) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void info(String s, Object obj, Object obj1) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDebugEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isErrorEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInfoEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTraceEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWarnEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String s) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String s, Object obj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String s, Object[] aobj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String s, Throwable throwable) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void trace(String s, Object obj, Object obj1) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String s) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String s, Object obj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String s, Object[] aobj) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String s, Throwable throwable) {

    // ignore
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void warn(String s, Object obj, Object obj1) {

    // ignore
  }

}
