/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

/**
 * A {@link ComponentException} is thrown if a
 * {@link ComponentProvider component} itself or reflecting on a component
 * caused an error.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentException extends IocException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3274177722975395418L;

  /**
   * @see IocException#IocException(String, Object[])
   */
  public ComponentException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see IocException#IocException(Throwable, String, Object[])
   */
  public ComponentException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
