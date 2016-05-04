/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

/**
 * Helper for code that is GWT incompatible and needs to be super-sourced.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
final class Helper {

  /**
   * Construction forbidden.
   */
  private Helper() {

  }

  /**
   * @return the current clock measure in nanoseconds. Will be {@link System#nanoTime()} or an emulation of it in
   *         environments where not available.
   */
  public static long getNanoTime() {

    return System.nanoTime();
  }

}
