/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsParseException;

/**
 * This is a rewrite of the awkward {@link java.util.StringTokenizer} provided by the JDK. This implementation
 * {@link #next() returns} an empty {@link String} if a duplicate delimiter is detected. Further it implements
 * {@link Iterable} and can be used in enhanced for-loops. <br>
 * <b>ATTENTION:</b><br>
 * Returning an empty {@link String} also for duplicated delimited might NOT always be desired (especially
 * when delimiter is whitespace).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class StringTokenizer extends AbstractIterator<String> implements Iterable<String> {

  /** The string to be tokenized */
  private final char[] string;

  /** The characters that will be detected as delimiters. */
  private final char[] delimiters;

  /** The string to start escaping of a token. */
  private final char[] escapeStart;

  /** The string to end escaping of a token. */
  private final char[] escapeEnd;

  /** The current index in {@link #string}. */
  private int index;

  /**
   * The constructor.
   *
   * @param string is the string to be tokenized.
   * @param delimiters are the characters that will be detected as delimiters.
   */
  public StringTokenizer(String string, char... delimiters) {

    this(string.toCharArray(), delimiters);
  }

  /**
   * The constructor.
   *
   * @param string is the string to be tokenized.
   * @param delimiters is a {@link String} with all the characters that will be detected as delimiters.
   */
  public StringTokenizer(String string, String delimiters) {

    this(string.toCharArray(), delimiters.toCharArray());
  }

  /**
   * The constructor that allows escaping. The escaping can be cascaded:
   *
   * <pre>
   * new StringTokenizer("{[foo,{[bar,thing]}]},some", "{[", "]}", ',').next()
   * </pre>
   *
   * will return "foo,{[bar,thing]}".
   *
   * @param string is the string to be tokenized.
   * @param escapeStart is the string used to start escaping of a token. The string has to be free of
   *        {@code delimiters}.
   * @param escapeEnd is the string used to end escaping of a token. The string has to be free of
   *        {@code delimiters}.
   * @param delimiters are the characters that will be detected as delimiters.
   * @throws NlsIllegalArgumentException if {@code escapeStart} or {@code escapeEnd} is an empty
   *         string or contains a character of {@code delimiters}, or one of them is {@code null}
   *         while the other is not, or both are not {@code null} but {@link Object#equals(Object) equal}
   *         to each other.
   *
   * @since 2.0.0
   */
  public StringTokenizer(String string, String escapeStart, String escapeEnd, char... delimiters)
      throws NlsIllegalArgumentException {

    this(string.toCharArray(), escapeStart, escapeEnd, delimiters);
  }

  /**
   * The constructor.
   *
   * @see #StringTokenizer(String, String, String, char...)
   *
   * @param string is the string to be tokenized.
   * @param escapeStart is the string used to start escaping of a token. May NOT be the empty string. The
   *        string has to be free of {@code delimiters}.
   * @param escapeEnd is the string used to end escaping of a token. May NOT be the empty string.The string
   *        has to be free of {@code delimiters}.
   * @param delimiters are the characters that will be detected as delimiters.
   * @throws NlsIllegalArgumentException if {@code escapeStart} or {@code escapeEnd} is an empty
   *         string or contains a character of {@code delimiters}, or one of them is {@code null}
   *         while the other is not, or both are not {@code null} but {@link Object#equals(Object) equal}
   *         to each other.
   *
   * @since 2.0.0
   */
  public StringTokenizer(char[] string, String escapeStart, String escapeEnd, char... delimiters)
      throws NlsIllegalArgumentException {

    super();
    this.string = string;
    this.delimiters = delimiters;
    if (escapeStart == null) {
      if (escapeEnd != null) {
        throw new NlsIllegalArgumentException(escapeEnd, "escapeEnd (escapeStart=null)");
      }
      this.escapeStart = null;
      this.escapeEnd = null;
    } else {
      if (escapeStart.equals(escapeEnd)) {
        throw new NlsIllegalArgumentException(escapeEnd, "escapeStart=escapeEnd");
      }
      if (escapeStart.length() == 0) {
        throw new NlsIllegalArgumentException(escapeStart, "escapeStart");
      }
      if ((escapeEnd == null) || (escapeEnd.length() == 0)) {
        throw new NlsIllegalArgumentException(escapeEnd, "escapeEnd");
      }
      this.escapeStart = escapeStart.toCharArray();
      this.escapeEnd = escapeEnd.toCharArray();
      if (containsDelimiter(this.escapeStart, delimiters)) {
        throw new NlsIllegalArgumentException(escapeStart, "escapeStart");
      }
      if (containsDelimiter(this.escapeEnd, delimiters)) {
        throw new NlsIllegalArgumentException(escapeEnd, "escapeEnd");
      }
    }
    this.index = -1;
    findFirst();
  }

  /**
   * The constructor.
   *
   * @param string is the string to be tokenized.
   * @param delimiters are the characters that will be detected as delimiters.
   */
  public StringTokenizer(char[] string, char... delimiters) {

    this(string, null, null, delimiters);
  }

  /**
   * This method checks that the given {@code escape} sequence does NOT contain any of the
   * {@code delimiters}.
   *
   * @param escape is the escape-sequence to check.
   * @param delimiters are the delimiters that should NOT be contained in {@code escape}.
   * @return {@code true} if {@code escape} contains a character of {@code delimiters},
   *         {@code false} otherwise.
   */
  private static boolean containsDelimiter(char[] escape, char[] delimiters) {

    for (char c : escape) {
      for (char d : delimiters) {
        if (d == c) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public Iterator<String> iterator() {

    return this;
  }

  @Override
  protected String findNext() {

    if ((this.index >= this.string.length) || (this.string.length == 0)) {
      return null;
    }
    this.index++;
    int start;
    int end;
    // a token can be escaped, then it has to be in the form
    // <escapeStart><token><escapeEnd>
    // and is followed by a delimiter or the end of the string.
    // be aware that <token> itself can also recursively contain any sequence of
    // <escapeStart><token><escapeEnd>
    if ((this.escapeStart != null) && containsSubstring(this.escapeStart, this.index)) {
      // token is escaped...
      int rawStart = this.index;
      this.index = this.index + this.escapeStart.length;
      start = this.index;
      end = start;
      int escapeDeepth = 1;
      while (this.index < this.string.length) {
        if (containsSubstring(this.escapeStart, this.index)) {
          escapeDeepth++;
          this.index = this.index + this.escapeStart.length;
        } else if ((this.escapeEnd != null) && containsSubstring(this.escapeEnd, this.index)) {
          escapeDeepth--;
          if (escapeDeepth == 0) {
            end = this.index;
            this.index = this.index + this.escapeEnd.length;
            break;
          }
          this.index = this.index + this.escapeEnd.length;
        } else {
          this.index++;
        }
      }
      if (escapeDeepth != 0) {
        // missing escapeEnd
        StringBuilder format = new StringBuilder();
        format.append(this.escapeStart);
        format.append('*');
        format.append(this.escapeEnd);
        throw new NlsParseException(new String(this.string, rawStart, this.index - rawStart), format, "token");
      }
      if (this.index < this.string.length) {
        char c = this.string[this.index];
        boolean isDelimiter = false;
        for (char delimiter : this.delimiters) {
          if (c == delimiter) {
            isDelimiter = true;
            break;
          }
        }
        if (!isDelimiter) {
          throw new NlsParseException(new String(this.string, rawStart, this.index + 1 - rawStart), new String(
              this.escapeEnd) + this.delimiters[0], "token");
        }
      }
    } else {
      start = this.index;
      while (this.index < this.string.length) {
        char c = this.string[this.index];
        boolean isDelimiter = false;
        for (char delimiter : this.delimiters) {
          if (c == delimiter) {
            isDelimiter = true;
            break;
          }
        }
        if (isDelimiter) {
          break;
        }
        this.index++;
      }
      end = this.index;
    }
    return new String(this.string, start, end - start);
  }

  /**
   * This method tests if the string to tokenize contains the given {@code substring} starting at the
   * given {@code index}.
   *
   * @param substring is the substring to check for.
   * @param startIndex is the start index in the string to tokenize.
   * @return {@code true} if the given {@code substring} was found at {@code index}.
   */
  protected boolean containsSubstring(char[] substring, int startIndex) {

    if (substring.length < (this.string.length - startIndex)) {
      for (int i = 0; i < substring.length; i++) {
        if (substring[i] != this.string[startIndex + i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * @see java.util.StringTokenizer#hasMoreTokens()
   *
   * @return {@code true} if {@link #next()} is available, {@code false} otherwise.
   */
  public boolean hasMoreTokens() {

    return hasNext();
  }

  /**
   * @see java.util.StringTokenizer#nextToken()
   *
   * @return the {@link #next() next} token.
   */
  public String nextToken() {

    return next();
  }

  @Override
  public String toString() {

    if (this.index < this.string.length) {
      return new String(this.string, this.index, this.string.length - this.index);
    } else {
      return "";
    }
  }

}
