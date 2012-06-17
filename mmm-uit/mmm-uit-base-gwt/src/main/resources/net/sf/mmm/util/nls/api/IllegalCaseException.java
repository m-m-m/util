/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

public class IllegalCaseException extends RuntimeException {

  /**
   * The constructor.
   */
  public IllegalCaseException(Class type, Object value) {

    super("Illegal case: " + value);
  }

}
