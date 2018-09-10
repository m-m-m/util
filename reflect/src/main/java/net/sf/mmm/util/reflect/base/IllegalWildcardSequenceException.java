/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * A {@link IllegalWildcardSequenceException} is thrown if a wildcard-type given as string could NOT be parsed
 * because it contains an illegal sequence (e.g. "? implements X").
 *
 * @see ReflectionUtilImpl#toType(String)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class IllegalWildcardSequenceException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IllegalWildcardSeq";

  /**
   * The constructor.
   *
   * @param sequence is the illegal sequence that was used in a wildcard-type.
   */
  public IllegalWildcardSequenceException(String sequence) {

    super("Illegal sequence in wildcard type '" + sequence + "'!");
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
