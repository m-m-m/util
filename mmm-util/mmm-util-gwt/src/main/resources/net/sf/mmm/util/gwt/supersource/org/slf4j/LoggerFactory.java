/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
// CHECKSTYLE:OFF (GWT compatible copy)
package org.slf4j;

import net.sf.mmm.util.logging.impl.GwtLogger;

/**
 * This is the GWT compatible implementation of {@link org.slf4j.LoggerFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class LoggerFactory {

  /**
   * This method creates a {@link Logger} for the given {@link Class}.
   *
   * @param type is the {@link Class} the logger will be named after.
   * @return the new {@link Logger}.
   */
  @SuppressWarnings("rawtypes")
  public static Logger getLogger(Class type) {

    return getLogger(type.getName());
  }

  /**
   * This method creates a {@link Logger} for the given <code>name</code>.
   *
   * @param name is the {@link Logger#getName() name} of the new logger.
   * @return the new {@link Logger}.
   */
  public static Logger getLogger(String name) {

    return new GwtLogger(name);
  }

}
// CHECKSTYLE:OFF
