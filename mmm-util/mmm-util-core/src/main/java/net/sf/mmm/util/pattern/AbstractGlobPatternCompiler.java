/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern;

import java.util.regex.Pattern;

/**
 * This is an abstract base-implementation of the {@link PatternCompiler}
 * interface for glob-patterns.
 * 
 * @see GlobPatternCompiler
 * @see PathPatternCompiler
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGlobPatternCompiler implements PatternCompiler {

  /** The characters that have to be escaped in a regular expression. */
  private static final char[] CHARS_TO_ESCAPE = new char[] { '.', '\\', '(', ')', '{', '}', '[',
      ']', '|', '&', '$', '+', '^' };

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

    char[] chars = pattern.toCharArray();
    StringBuilder buffer = new StringBuilder(chars.length + 8);
    int i = 0;
    while (i < chars.length) {
      int next = process(chars, i, buffer);
      assert (next > i);
      i = next;
    }
    return Pattern.compile(buffer.toString());
  }

  /**
   * This method processes one or many characters from <code>chars</code>
   * starting at the given <code>charIndex</code> and adds the translated
   * regexp-sequence to <code>buffer</code>.
   * 
   * @param chars are the characters of the original pattern string.
   * @param charIndex is the current index in <code>chars</code>.
   * @param buffer is where the translated regexp-pattern is appended to.
   * @return the index of the next character that has NOT yet been consumed
   *         (typically <code>charIndex + 1</code>).
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
