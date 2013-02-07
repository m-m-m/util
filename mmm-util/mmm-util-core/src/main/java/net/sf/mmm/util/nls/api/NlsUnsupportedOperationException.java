/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link NlsUnsupportedOperationException} is thrown if an operation was invoked that is NOT implemented
 * or generally NOT supported.<br>
 * <b>ATTENTION:</b><br>
 * Please always consider to avoid APIs that throw such exception and use inheritance instead (e.g. if there
 * was a <code>ReadOnlyIterator</code> such as <code>Enumeration</code> that <code>Iterator</code> extends).
 * 
 * @see UnsupportedOperationException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsUnsupportedOperationException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1008016155549441562L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "UnsupOp";

  /**
   * The constructor.
   */
  public NlsUnsupportedOperationException() {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorUnsupportedOperation());
  }

  /**
   * The constructor.
   * 
   * @param operation is a description (e.g. the {@link java.lang.reflect.Method} or a string with the name of
   *        the class and method) of the operation that is NOT supported.
   */
  public NlsUnsupportedOperationException(Object operation) {

    this(operation, null);
  }

  /**
   * The constructor.
   * 
   * @param operation is a description (e.g. the {@link java.lang.reflect.Method} or a string with the name of
   *        the class and method) of the operation that is NOT supported.
   * @param source is the source object that does not support the operation.
   */
  public NlsUnsupportedOperationException(Object operation, Object source) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorUnsupportedOperationWithName(operation, source));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
