/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;

/**
 * This is the interface for a hashing algorithm for strings (in form of <code>char[]</code> or
 * {@link CharSequence}. <br>
 * <b>ATTENTION:</b><br>
 * The implementation may NOT be compatible to {@link String#hashCode()}. It only needs to guarantee, that all
 * method defined in this interface are compatible to each other. The purpose of this interface is to abstract
 * from the hash-algorithm that can be optimized for performance or to generate strong hashes (or maybe both).
 * In this context <em>performance</em> or <em>fast</em> is especially focused on methods such as
 * {@link #getHashCodes(char[], int, int, int)} rather than {@link #getHashCode(CharSequence)} which will NOT
 * be faster than {@link String#hashCode()} itself.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface StringHasher {

  /**
   * This method gets the hash-code for the given <code>string</code>. <br>
   *
   * @see #getHashCode(CharSequence, int, int)
   *
   * @param string is the {@link CharSequence} to hash.
   * @return the according hash-code.
   */
  int getHashCode(CharSequence string);

  /**
   * This method gets the hash-code for the specified {@link CharSequence#subSequence(int, int) subsequence}
   * of the given <code>string</code>. <br>
   *
   * @see Object#hashCode()
   *
   * @param string is the {@link CharSequence} containing the {@link CharSequence#subSequence(int, int)
   *        subsequence} to hash.
   * @param start is the index of the first character to include into the hash.
   * @param end is the index one before the last character to include into the hash.
   * @return the according hash-code.
   */
  int getHashCode(CharSequence string, int start, int end);

  /**
   * This method gets the hash-code for the specified {@link CharSequence#subSequence(int, int) subsequence}
   * of the given <code>string</code>. <br>
   *
   * @see Object#hashCode()
   *
   * @param string is the char-array containing the {@link String#String(char[], int, int) substring} to hash.
   * @param start is the index of the first character to include into the hash.
   * @param end is the index one before the last character to include into the hash.
   * @return the according hash-code.
   */
  int getHashCode(char[] string, int start, int end);

  /**
   * This method gets the hash-codes for all {@link CharSequence#subSequence(int, int) subsequence} of
   * <code>string</code> that have the given <code>length</code>.
   *
   * @see #getHashCodes(char[], int, int, int)
   *
   * @param string is the string as char-array.
   * @param length is the {@link CharSequence#length() length} of the
   *        {@link CharSequence#subSequence(int, int) sub-sequences} of <code>string</code> to hash.
   * @return the requested hash-codes. An empty array if the length of <code>string</code> is less than the
   *         given <code>length</code>.
   */
  int[] getHashCodes(char[] string, int length);

  /**
   * This method gets the hash-codes for all {@link CharSequence#subSequence(int, int) subsequence} of
   * <code>string</code> from <code>stringStart</code> (inclusive) until <code>stringEnd</code> (exclusive)
   * that have the given <code>length</code>. <br>
   * The implementation of this method needs to behave functionally equivalent to this code (but should be
   * more efficient):
   *
   * <pre>
   * int size = stringEnd - stringStart - length + 1;
   * if (size &lt;= 0) {
   *   return new int[0];
   * }
   * int[] result = new int[size];
   * for (int i = 0; i &lt; size; i++) {
   *   result[i] = {@link #getHashCode(CharSequence) getHashCode}(string, i, i + length);
   * }
   * return result;
   * </pre>
   *
   * @param string is the string as char-array.
   * @param length is the {@link CharSequence#length() length} of the
   *        {@link CharSequence#subSequence(int, int) sub-sequences} of <code>string</code> to hash.
   * @param stringStart is the index where to start in <code>string</code>.
   * @param stringEnd is the index where to stop in <code>string</code>.
   * @return the requested hash-codes. An empty array if the length of <code>string</code> is less than the
   *         given <code>length</code>.
   */
  int[] getHashCodes(char[] string, int length, int stringStart, int stringEnd);

  /**
   * This method gets the hash-codes for all {@link CharSequence#subSequence(int, int) subsequence} of
   * <code>string</code> that have the given <code>length</code>. <br>
   *
   * @see #getHashCodes(char[], int)
   *
   * @param string is the string as char-array.
   * @param length is the {@link CharSequence#length() length} of the
   *        {@link CharSequence#subSequence(int, int) sub-sequences} of <code>string</code> to hash.
   * @return the requested hash-codes. An empty array if the length of <code>string</code> is less than the
   *         given <code>length</code>.
   */
  int[] getHashCodes(CharSequence string, int length);

  /**
   * This method gets the hash-codes for all {@link CharSequence#subSequence(int, int) subsequence} of
   * <code>string</code> from <code>stringStart</code> (inclusive) until <code>stringEnd</code> (exclusive)
   * that have the given <code>length</code>. <br>
   *
   * @see #getHashCodes(char[], int)
   *
   * @param string is the string as char-array.
   * @param length is the {@link CharSequence#length() length} of the
   *        {@link CharSequence#subSequence(int, int) sub-sequences} of <code>string</code> to hash.
   * @param stringStart is the index where to start in <code>string</code>.
   * @param stringEnd is the index where to stop in <code>string</code>.
   * @return the requested hash-codes. An empty array if the length of <code>string</code> is less than the
   *         given <code>length</code>.
   */
  int[] getHashCodes(CharSequence string, int length, int stringStart, int stringEnd);

}
