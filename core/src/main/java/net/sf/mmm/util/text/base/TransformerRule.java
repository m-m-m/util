/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

/**
 * This is the abstract base class for a rule used to {@link #transform(String, String) replace} a given
 * string.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class TransformerRule {

  /**
   * @see net.sf.mmm.util.text.api.Singularizer#transform(String)
   * 
   * @param string is the original string to modify.
   * @param stringLowerCase is the {@code string} in {@link String#toLowerCase() lower-case}.
   * @return the replaced {@code string} or {@code null} if this rule does NOT apply and the
   *         {@code string} should NOT be replaced.
   */
  public abstract String transform(String string, String stringLowerCase);

}
