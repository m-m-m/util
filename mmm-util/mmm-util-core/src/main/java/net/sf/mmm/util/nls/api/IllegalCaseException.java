/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link IllegalCaseException} is thrown if a specific case occurred that
 * should never happen. It is typically thrown in the <code>default</code>
 * -section of a <code>switch</code>-statement where all <code>case</code>s
 * should have been covered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class IllegalCaseException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5031356555598229511L;

  /** The key for the NLS message. */
  private static final String KEY_CASE = "case";

  /**
   * The constructor.
   * 
   * @param illegalCase is the case that should never occur.
   */
  public IllegalCaseException(String illegalCase) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_CASE, toMap(KEY_CASE, illegalCase));
  }

  /**
   * The constructor.
   * 
   * @param <E> is the generic type of <code>enumType</code>.
   * 
   * @param enumType is the enum-{@link Class} of <code>enumValue</code>.
   * @param enumValue is the value of the enum that was NOT covered.
   */
  public <E extends Enum<E>> IllegalCaseException(Class<E> enumType, E enumValue) {

    this(enumType.getSimpleName() + "." + enumValue.name());
  }

}
