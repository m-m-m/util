/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.lang;

/**
 * This makes {@code CloneNotSupportedException} available to GWT clients.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CloneNotSupportedException extends Exception {

  /**
   * The constructor.
   */
  public CloneNotSupportedException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message
   */
  public CloneNotSupportedException(String message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param cause
   */
  public CloneNotSupportedException(Throwable cause) {

    super(cause);
  }

}
