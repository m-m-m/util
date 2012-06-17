/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This is an abstract base-implementation of the {@link PatternCompiler} interface for glob-patterns.
 * 
 * @see GlobPatternCompiler
 * @see PathPatternCompiler
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractGlobPatternCompiler implements PatternCompiler {

  /** The characters that have to be escaped in a regular expression. */
  private static final char[] CHARS_TO_ESCAPE = new char[] { '.', '\\', '(', ')', '{', '}', '[', ']', '|', '&', '$',
      '+', '^' };

  /**
   * The constructor.
   */
  public AbstractGlobPatternCompiler() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Pattern compile(String pattern) throws IllegalArgumentException {

    String regexPattern = convertPattern(pattern);
    if (regexPattern == null) {
      return null;
    }
    return Pattern.compile(regexPattern);
  }

  /**
   * This method gets the flag that determines if wildcards are required.<br>
   * This implementation always returns <code>false</code>. Override to change.
   * 
   * @return <code>true</code> if wildcards are required. In that case <code>null</code> is
   *         {@link #compile(String) returned} if the given <code>pattern</code> contains no wildcard ('*' or
   *         '?'). <code>false</code> otherwise.
   */
  protected boolean isRequireWildcard() {

    return false;
  }

  /**
   * This method converts the given <code>pattern</code> to a {@link Pattern#compile(String) regex-pattern}.
   * 
   * @param pattern is the pattern to convert.
   * @return the converted regex-pattern or <code>null</code> if {@link #isRequireWildcard()} is
   *         <code>true</code> and the given <code>pattern</code> contains no wildcard ('*' or '?').
   */
  protected String convertPattern(String pattern) {

    boolean wildcard = false;
    char[] chars = pattern.toCharArray();
    StringBuilder buffer = new StringBuilder(chars.length + 8);
    int i = 0;
    while (i < chars.length) {
      char c = chars[i];
      if ((c == '*') || (c == '?')) {
        wildcard = true;
      }
      int next = process(chars, i, buffer);
      assert (next > i);
      i = next;
    }
    if (isRequireWildcard() && !wildcard) {
      return null;
    } else {
      return buffer.toString();
    }
  }

  /**
   * This method processes one or many characters from <code>chars</code> starting at the given
   * <code>charIndex</code> and adds the translated regexp-sequence to <code>buffer</code>.
   * 
   * @param chars are the characters of the original pattern string.
   * @param charIndex is the current index in <code>chars</code>.
   * @param buffer is where the translated regexp-pattern is appended to.
   * @return the index of the next character that has NOT yet been consumed (typically
   *         <code>charIndex + 1</code>).
   */
  protected int process(char[] chars, int charIndex, StringBuilder buffer) {

    char c = chars[charIndex];
    if (c == '*') {
      buffer.append(".*");
    } else if (c == '?') {
      buffer.append('.');
    } else {
      for (char esc : CHARS_TO_ESCAPE) {
        if (c == esc) {
          // escape
          buffer.append('\\');
          break;
        }
      }
      buffer.append(c);
    }
    return charIndex + 1;
  }

}
