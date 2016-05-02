/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * A {@link NlsNullPointerException} is analog to an {@link NullPointerException} but with native language support.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsNullPointerException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5746393435577207765L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "NPE";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsNullPointerException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param argument is the argument that is illegal. May be {@code null}.
   */
  public NlsNullPointerException(String argument) {

    this(argument, null);
  }

  /**
   * The constructor.
   *
   * @param argument is the argument that is illegal. May be {@code null}.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsNullPointerException(String argument, Throwable nested) {

    super(nested, createBundle(NlsBundleUtilExceptionRoot.class).errorArgumentNull(argument));
  }

  /**
   * This method checks if the given {@code object} is {@code null}. <br>
   * <b>ATTENTION:</b><br>
   * This method is only intended to be used for specific types. It then not only saves you from a single
   * {@code if}-statement, but also defines a common pattern that is refactoring-safe. <br>
   * Anyhow you should never use this method with generic {@link Class}es for {@code type} such as {@link Object},
   * {@link String}, {@link Integer}, etc. <br>
   * <br>
   * Here is an example:
   *
   * <pre>
   * public void myMethod(MySpecificBusinessObject businessObject, String someParameter) {
   *   {@link NlsNullPointerException}.checkNotNull(MySpecificBusinessObject.class, businessObject);
   *   {@link NlsNullPointerException}.checkNotNull("someParameter", someParameter);
   *   doTheWork();
   * }
   * </pre>
   *
   * @param <O> is the generic type of the {@code object}.
   * @param type is the class reflecting the {@code object}. Its {@link Class#getSimpleName() simple name} will be
   *        used in the exception-message if {@code object} is {@code null}.
   * @param object is the object that is checked and should NOT be {@code null}.
   * @throws NlsNullPointerException if the given {@code object} is {@code null}.
   */
  public static <O> void checkNotNull(Class<O> type, O object) throws NlsNullPointerException {

    if (object == null) {
      throw new NlsNullPointerException(type.getSimpleName());
    }
  }

  /**
   * This method checks if the given {@code object} is {@code null}. <br>
   * Look at the following example:
   *
   * <pre>
   * {@link NlsNullPointerException}.checkNotNull("someParameter", someParameter);
   * </pre>
   *
   * This is equivalent to this code:
   *
   * <pre>
   * if (someParameter == null) {
   *   throw new {@link NlsNullPointerException}("someParameter");
   * }
   * </pre>
   *
   *
   * @param objectName is the (argument-)name of the given {@code object}.
   * @param object is the object that is checked and should NOT be {@code null}.
   * @throws NlsNullPointerException if the given {@code object} is {@code null}.
   * @since 2.0.0
   */
  public static void checkNotNull(String objectName, Object object) throws NlsNullPointerException {

    if (object == null) {
      throw new NlsNullPointerException(objectName);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
