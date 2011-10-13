/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.security.api;

import java.util.Map;

import net.sf.mmm.data.api.ContentException;

/**
 * This exception is used if a {@link net.sf.mmm.data.api.ContentObject
 * content-object} was accessed violating a security constraint.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SecurityException extends ContentException {

  /** uid for serialization */
  private static final long serialVersionUID = 3257285846510940725L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage
   * @param arguments
   */
  public SecurityException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
    // TODO Auto-generated constructor stub
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage
   */
  public SecurityException(String internationalizedMessage) {

    super(internationalizedMessage);
    // TODO Auto-generated constructor stub
  }

  /**
   * The constructor.
   * 
   * @param nested
   * @param internationalizedMessage
   * @param arguments
   */
  public SecurityException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
    // TODO Auto-generated constructor stub
  }

  /**
   * The constructor.
   * 
   * @param nested
   * @param internationalizedMessage
   */
  public SecurityException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
    // TODO Auto-generated constructor stub
  }

}
