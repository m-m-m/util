/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * This exception is thrown if a component if
 * {@link IocContainer#getComponent(Class) requested} that does NOT exist, is
 * NOT unique or is NOT as expected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainerException extends RuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -2251034077749617447L;

  /**
   * The constructor.
   * 
   * @param message the {@link #getMessage() message}.
   * @param cause the {@link #getCause() cause}.
   */
  public IocContainerException(String message, Throwable cause) {

    super(message, cause);
  }

  /**
   * The constructor.
   * 
   * @param message the {@link #getMessage() message}.
   */
  public IocContainerException(String message) {

    super(message);
  }

  /**
   * The constructor.
   * 
   * @param cause the {@link #getCause() cause}.
   */
  public IocContainerException(Throwable cause) {

    super(cause);
  }

}
