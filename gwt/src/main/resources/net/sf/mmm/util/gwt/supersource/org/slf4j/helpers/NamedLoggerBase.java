/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
// CHECKSTYLE:OFF (GWT compatible copy)
package org.slf4j.helpers;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GWT compatible version of this class.
 *
 * @author Ceki Gulcu
 * @since 1.5.3
 */
abstract class NamedLoggerBase implements Logger, Serializable {

  private static final long serialVersionUID = 7535258609338176893L;

  /** @see #getName() */
  protected String name;

  @Override
  public String getName() {
    return this.name;
  }

  protected Object readResolve() {
    // using getName() instead of this.name works even for
    // NOPLogger
    return LoggerFactory.getLogger(getName());
  }

}
// CHECKSTYLE:ON
