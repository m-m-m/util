/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * A {@link IllegalCaseException} is thrown if a specific case occurred that should never happen. It is typically thrown
 * in the {@code default} -section of a {@code switch}-statement where all {@code case}s should have been
 * covered.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class IllegalCaseException extends NlsRuntimeException {

  private static final long serialVersionUID = -5031356555598229511L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IllegalCase";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected IllegalCaseException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param illegalCase is the case that should never occur.
   */
  public IllegalCaseException(String illegalCase) {

    super(createBundle(NlsBundleUtilExceptionRoot.class).errorIllegalCase(illegalCase));
  }

  /**
   * The constructor.
   *
   * @param <E> is the generic type of {@code enumType}.
   *
   * @param enumType is the enum-{@link Class} of {@code enumValue}.
   * @param enumValue is the value of the enum that was NOT covered.
   */
  public <E extends Enum<E>> IllegalCaseException(Class<E> enumType, E enumValue) {

    this(enumType.getSimpleName() + "." + enumValue.name());
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
