/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This interface defines the syntax for a decoding and encoding values from/to {@link String}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface StringSyntax {

  /**
   * This method gets the character used to start a quotation that should be terminated by a {@link #getQuoteEnd()
   * quote-end} character. The text inside the quote is taken as is (without the quote characters). <br>
   * Common examples for quote characters are the single quotes ({@code '}) and double quotes ({@code "}).
   *
   * @return the character used to start a quotation or {@code '\0'} to disable.
   */
  char getQuoteStart();

  /**
   * This method gets the character used to end a quotation.
   *
   * @see #getQuoteStart()
   *
   * @return the character used to end a quotation or {@code '\0'} to disable.
   */
  char getQuoteEnd();

  /**
   * This method gets the character used as escape. It is used to mark special characters like {@link #getQuoteStart()}
   * to allow these characters also in the payload. The escape itself is removed on decoding while the next character is
   * taken as is without any special interpretation. <br>
   * The most common escape character is the backslash ({@code \}). <br>
   * Here are some examples for decoding:
   * <table border="1">
   * <tr>
   * <th>{@link #getEscape() escape}</th>
   * <th>input</th>
   * <th>output</th>
   * </tr>
   * <tr>
   * <td>\</td>
   * <td>a\b\\c</td>
   * <td>ab\c</td>
   * </tr>
   * <tr>
   * <td>~</td>
   * <td>a~b~~~c</td>
   * <td>ab~c</td>
   * </tr>
   * </table>
   *
   * @return the escape character or {@code '\0'} for no escaping.
   */
  char getEscape();

}
