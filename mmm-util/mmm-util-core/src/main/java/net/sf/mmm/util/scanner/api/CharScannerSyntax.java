/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.api;

import net.sf.mmm.util.lang.api.StringSyntax;

/**
 * This is the interface used to define the syntax to scan characters.
 * 
 * @see CharStreamScanner#readUntil(char, boolean, CharScannerSyntax)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CharScannerSyntax extends StringSyntax {

  /**
   * {@inheritDoc}
   * 
   * This allows to encode special characters like a
   * {@link CharStreamScanner#readUntil(char, boolean, CharScannerSyntax) stop-character},
   * {@link #getQuoteStart() quote-start}, {@link #getAltQuoteStart() alt-quote-start}, as well as the
   * {@link #getEscape() escape} itself.<br>
   * <b>ATTENTION:</b><br>
   * The {@link #getEscape() escape} is disabled within {@link #getQuoteStart() quotations}.
   * 
   * @see #getEntityStart()
   */
  char getEscape();

  /**
   * This method gets the character used to escape the {@link #getQuoteEnd() quote-end} character within a
   * quotation. This may be the {@link #getQuoteEnd() quote-end} itself so a duplicate {@link #getQuoteEnd()
   * quote-end} represents a single occurrence of that character within a quotation. Otherwise the escape may
   * be any other character.<br>
   * Please note that this escaping is only active within a quotation opened by {@link #getQuoteStart()
   * quote-start} and only escapes the {@link #getQuoteEnd() quote-end} character and nothing else so in any
   * other case the {@link #getQuoteEscape() quote-escape} is treated as a regular character.<br>
   * <table border="1">
   * <tr>
   * <th>{@link #getQuoteStart() quote-start}</th>
   * <th>{@link #getQuoteEnd() quote-end}</th>
   * <th>{@link #getQuoteEscape() quote-escape}</th>
   * <th>input</th>
   * <th>output</th>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>a'bc'd</td>
   * <td>abcd</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>a'b''c'd</td>
   * <td>ab'cd</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>\</td>
   * <td>a'b\c\'d\\'e'f</td>
   * <td>ab\c'd\'ef</td>
   * </tr>
   * </table>
   * 
   * @return the character used to escape the {@link #getQuoteEnd() quote-end} character or <code>'\0'</code>
   *         to disable.
   */
  char getQuoteEscape();

  /**
   * If {@link #getQuoteStart() quote-start}, {@link #getQuoteEnd() quote-end} and {@link #getQuoteEscape()
   * quote-escape} all point to the same character (which is NOT <code>'\0'</code>), then this method
   * determines if {@link #getQuoteEscape() quotation escaping} is <em>lazy</em>. This means that outside a
   * quotation a double occurrence of the quote character is NOT treated as quotation but as escaped quote
   * character. Otherwise if NOT lazy, the double quote character is treated as quotation representing the
   * empty sequence.<br>
   * Here are some examples: <code><table border="1">
   * <tr>
   * <th>{@link #getQuoteStart() quote-start}</th>
   * <th>{@link #getQuoteEnd() quote-end}</th>
   * <th>{@link #getQuoteEscape() quote-escape}</th>
   * <th>{@link #isQuoteEscapeLazy() quote-escape-lazy}</th>
   * <th>input</th>
   * <th>output</th>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>true</td>
   * <td>''</td>
   * <td>'</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>false</td>
   * <td>''</td>
   * <td>&nbsp;</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>true</td>
   * <td>''''</td>
   * <td>''</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>false</td>
   * <td>''''</td>
   * <td>'</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>true</td>
   * <td>'''a'</td>
   * <td>'a</td>
   * </tr>
   * <tr>
   * <td>'</td>
   * <td>'</td>
   * <td>'</td>
   * <td>false</td>
   * <td>'''a'</td>
   * <td>'a</td>
   * </tr>
   * </table>
   * </code><br>
   * Please note that for <code>'''a'</code> the complete sequence is treated as quote if
   * {@link #isQuoteEscapeLazy() quote-escape-lazy} is <code>false</code> and otherwise just the trailing
   * <code>'a'</code>.
   * 
   * @return <code>true</code> if quote-escaping is lazy, <code>false</code> otherwise.
   */
  boolean isQuoteEscapeLazy();

  /**
   * This method gets the alternative character used to start a quotation that should be terminated by a
   * {@link #getAltQuoteEnd() alt-quote-end} character. The text inside the quote is taken as is (without the
   * quote characters).
   * 
   * @see #getQuoteStart()
   * 
   * @return the alternative character used to start a quotation or <code>'\0'</code> to disable.
   */
  char getAltQuoteStart();

  /**
   * This method gets the alternative character used to end a quotation.
   * 
   * @see #getAltQuoteStart()
   * 
   * @return the alternative character used to end a quotation.
   */
  char getAltQuoteEnd();

  /**
   * This method gets the character used to escape the {@link #getAltQuoteEnd() alt-quote-end} character
   * within an quotation opened by {@link #getAltQuoteStart() alt-quote-start}.
   * 
   * @see #getQuoteEscape()
   * 
   * @return the character used to escape the {@link #getQuoteEnd() quote-end} character or <code>'\0'</code>
   *         to disable.
   */
  char getAltQuoteEscape();

  /**
   * If {@link #getAltQuoteStart() alt-quote-start}, {@link #getAltQuoteEnd() alt-quote-end} and
   * {@link #getAltQuoteEscape() alt-quote-escape} all point to the same character (which is NOT
   * <code>'\0'</code>), then this method determines if {@link #getAltQuoteEscape() alt-quotation escaping} is
   * <em>lazy</em>.
   * 
   * @see #isQuoteEscapeLazy()
   * 
   * @return <code>true</code> if alt-quote-escaping is lazy, <code>false</code> otherwise.
   */
  boolean isAltQuoteEscapeLazy();

  /**
   * This method gets the character used to start an entity. An entity is a specific encoded string surrounded
   * with {@link #getEntityStart() entity-start} and {@link #getEntityEnd() entity-end}. It will be decoded by
   * {@link #resolveEntity(String)}.
   * 
   * @return the character used to start an entity or <code>'\0'</code> to disable.
   */
  char getEntityStart();

  /**
   * This method gets the character used to end an entity.
   * 
   * @see #getEntityStart()
   * 
   * @return the character used to end an entity.
   */
  char getEntityEnd();

  /**
   * This method resolves the given <code>entity</code>.<br>
   * E.g. if {@link #getEntityStart() entity-start} is <code>'&'</code> and {@link #getEntityEnd()} is ';'
   * then if the string <code>"&lt;"</code> is scanned, this method is called with <code>"lt"</code> as
   * <code>entity</code> argument and may return <code>"&lt;"</code>.
   * 
   * @param entity is the entity string that was found surrounded by {@link #getEntityStart() entity-start}
   *        and {@link #getEntityEnd() entity-end} excluding these characters.
   * @return the decoded entity.
   */
  String resolveEntity(String entity);

}
