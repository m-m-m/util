/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the term component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleTermCore extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleTermCore() {

    super();
  }

  /**
   * exception message for illegal function argument count.
   * 
   * @see net.sf.mmm.term.api.IllegalArgumentCountException
   */
  public static final String ERR_ILLEGAL_ARG_COUNT = "Illegal argument count \"{1}\" for function \"{0}\": must be in the rage of \"{2}\"-\"{3}\"!";

  /**
   * exception message for illegal unary function argument.
   * 
   * @see net.sf.mmm.term.api.IllegalArgumentTypeException
   */
  public static final String ERR_ILLEGAL_ARGUMENTS = "The function \"{0}\" is not applicable for the arguments \"{1}\"!";

  /**
   * exception message for illegal function signature.
   * 
   * @see net.sf.mmm.term.api.IllegalArgumentTypeException
   */
  public static final String ERR_ILLEGAL_SIGNATURE = "The function \"{0}\" is not applicable for with the signature \"{1}\"!";

  /**
   * exception message if expression in expression variable was
   * <code>null</code>.
   * 
   * @see net.sf.mmm.term.impl.ExpressionVariable
   */
  public static final String ERR_EXPR_VAR_NULL = "The expression variable \"{0}\" can not be resolved, internal expression was null.";

  /**
   * exception message if term expression could NOT be parsed.
   * 
   * @see net.sf.mmm.term.api.TermParseException
   */
  public static final String ERR_TERM_PARSE = "The expression variable \"{0}\" can not be resolved, internal expression was null.";

  /**
   * exception message if partial function implementation has method signature
   * that is already registered.
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_DUPLICATE_SIGNATURE = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "The signature \"{2}\" is already registered for this function!";

  /**
   * exception message if partial function implementation method has illegal
   * exception type.
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_ILLEGAL_EXCEPTION_TYPE = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "Method with signature \"{2}\" has forbidden exception type \"{3}\"!";

  /**
   * exception message if partial function implementation method has illegal
   * exception type.
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_ILLEGAL_METHOD = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "Method with signature \"{2}\" must be non-void, public and static!";

  /**
   * exception message if partial function implementation is illegal by itself
   * (declaration).
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_ILLEGAL_CLASS = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "Type must be a public and non-abstract class!";

  /**
   * exception message if partial function implementation declares NO legal
   * method.
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_EMPTY = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "No appropriate method found!";

  /**
   * exception message if partial function implementation is missing a required
   * (public static final) field..
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_FIELD = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "Must declare public static final field \"{2}\" of type \"{3}\"!";

  /**
   * exception message if partial function implementation is missing a required
   * (public static final) field.
   * 
   * @see net.sf.mmm.term.impl.GenericFunction#addImplementation(Class)
   */
  public static final String ERR_FCT_WRONG_SYMBOL = "Failed to register partial implementation \"{0}\" of generic function \"{1}\": "
      + "Wrong operator symbol \"{2}\"!";

  /**
   * exception message for illegal cast (conversion of a value to another type).
   * 
   * @see net.sf.mmm.term.api.IllegalCastException
   */
  public static final String ERR_ILLEGAL_CAST = "Can not cast value \"{0}\" to \"{1}\"!";

  /**
   * exception message if string was multiplied with negative integer.
   * 
   * @see net.sf.mmm.term.impl.function.FctMultiply
   */
  public static final String ERR_FCT_MUL_STR_NEG = "Can not multiply string with negative number!";

  /**
   * exception message if string multiplication results in string longer than
   * maximum allowed limit.
   * 
   * @see net.sf.mmm.term.impl.function.FctMultiply
   */
  public static final String ERR_FCT_MUL_STR_MAX = "String multiply must not exceed result string longer than \"{0}\"!";

  /**
   * exception message on division by zero.
   * 
   * @see net.sf.mmm.term.impl.function.FctDivide
   */
  public static final String ERR_FCT_DIV_ZERO = "Division by zero!";

}
