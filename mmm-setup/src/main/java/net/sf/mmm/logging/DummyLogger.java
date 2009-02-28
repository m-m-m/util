/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.logging;

import org.slf4j.Logger;
import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * This is an implementation of the {@link Logger} interface that does nothing.
 * Unlike {@link org.slf4j.helpers.NOPLogger} its methods are NOT final so it
 * can be extended for testing purposes.
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
  public void debug(String s) {

  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object obj) {

  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object[] aobj) {

  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Throwable throwable) {

  }

  /**
   * {@inheritDoc}
   */
  public void debug(String s, Object obj, Object obj1) {

  }

  /**
   * {@inheritDoc}
   */
  public void error(String s) {

  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object obj) {

  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object[] aobj) {

  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Throwable throwable) {

  }

  /**
   * {@inheritDoc}
   */
  public void error(String s, Object obj, Object obj1) {

  }

  /**
   * {@inheritDoc}
   */
  public void info(String s) {

  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object obj) {

  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object[] aobj) {

  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Throwable throwable) {

  }

  /**
   * {@inheritDoc}
   */
  public void info(String s, Object obj, Object obj1) {

  }

  /**
   * {@inheritDoc}
   */
  public boolean isDebugEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isErrorEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInfoEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTraceEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isWarnEnabled() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s) {

  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object obj) {

  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object[] aobj) {

  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Throwable throwable) {

  }

  /**
   * {@inheritDoc}
   */
  public void trace(String s, Object obj, Object obj1) {

  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s) {

  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object obj) {

  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object[] aobj) {

  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Throwable throwable) {

  }

  /**
   * {@inheritDoc}
   */
  public void warn(String s, Object obj, Object obj1) {

  }

}
