/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;



/**
 * This exception is thrown if the parsing of a value fails.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 662961335483675913L;

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public ValueParseException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public ValueParseException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
