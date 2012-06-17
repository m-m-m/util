/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.api;

/**
 * This is a simple indirection to {@link Character#isWhitespace(char)} that can be replaced for GWT
 * compatibility.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
final class WhitespaceHelper {

  /**
   * Construction prohibited.
   */
  private WhitespaceHelper() {

    super();
  }

  /**
   * @param c is the character to check.
   * @return <code>true</code> if {@link Character#isWhitespace(char) whitespace}, <code>false</code>
   *         otherwise.
   */
  public static boolean isWhitespace(char c) {

    return Character.isWhitespace(c);
  }

}
