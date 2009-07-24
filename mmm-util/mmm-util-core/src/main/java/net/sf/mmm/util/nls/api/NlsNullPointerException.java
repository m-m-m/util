/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsNullPointerException} is analog to an
 * {@link NullPointerException} but with native language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsNullPointerException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5746393435577207765L;

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   */
  public NlsNullPointerException(String argument) {

    super(NlsBundleUtilCore.ERR_ARGUMENT_NULL, argument);
  }

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsNullPointerException(String argument, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ARGUMENT_NULL, argument);
  }

  /**
   * This method checks if the given <code>object</code> is <code>null</code>.<br>
   * <b>ATTENTION:</b><br>
   * This method is only intended to be used for specific types. It then not
   * only saves you from a single <code>if</code>-statement, but also defines a
   * common pattern that is refactoring-safe.<br>
   * Anyhow you should never use this method with generic {@link Class}es for
   * <code>type</code> such as {@link Object}, {@link String}, {@link Integer},
   * etc.<br>
   * <br>
   * Here is an example:
   * 
   * <pre>
   * public void myMethod(MySpecificBusinessObject businessObject, String myName) {
   *   {@link NlsNullPointerException}.checkNotNull(MySpecificBusinessObject.class, businessObject);
   *   if (name == null) {
   *     throw new {@link NlsNullPointerException}("myName");
   *   }
   *   doTheWork();
   * }
   * </pre>
   * 
   * @param <O> is the generic type of the <code>object</code>.
   * @param type is the class reflecting the <code>object</code>. Its
   *        {@link Class#getSimpleName() simple name} will be used in the
   *        exception-message if <code>object</code> is <code>null</code>.
   * @param object is the object that is checked and should NOT be
   *        <code>null</code>.
   * @throws NlsNullPointerException if the given <code>object</code> is
   *         <code>null</code>.
   */
  public static <O> void checkNotNull(Class<O> type, O object) throws NlsNullPointerException {

    if (object == null) {
      throw new NlsNullPointerException(type.getSimpleName());
    }
  }

}
