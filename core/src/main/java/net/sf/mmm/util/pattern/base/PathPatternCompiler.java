/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This is an implementation of the {@link PatternCompiler} interface that {@link #compile(String) compiles}
 * <em>path-glob-patterns</em>. A path-glob-pattern is like a {@link GlobPatternCompiler glob-pattern} but more specific
 * for matching directory paths:
 * <ul>
 * <li>{@code '?'} matches any character except for a slash ({@code '/'} or {@code '\\'}).</li>
 * <li>A single {@code '*'} matches a sequence of characters excluding slashes including the empty sequence.</li>
 * <li>{@code '**'} matches any sequence of characters (including slashes).</li>
 * <li>the character {@code '/'} matches a slash ({@code '/'} or {@code '\\'}).</li>
 * <li><code>'**&#47;'</code> matches any sequence terminated by a slash as well as the empty sequence.</li>
 * </ul>
 * The idea is taken from <a href="http://ant.apache.org/manual/dirtasks.html#patterns">ant</a> even though it might be
 * slightly different. <br>
 * Examples:
 * <ul>
 * <li>{@code A*bc?e} matches {@code Abcde} or {@code AFOObarbcxe} but NOT {@code abcde} or {@code Abce}.</li>
 * </ul>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PathPatternCompiler extends AbstractGlobPatternCompiler {

  /** A singleton instance of this instance. */
  public static final PatternCompiler INSTANCE = new PathPatternCompiler();

  /**
   * The constructor.
   */
  public PathPatternCompiler() {

    super();
  }

  @Override
  protected int process(char[] chars, int charIndex, java.lang.StringBuilder buffer) {

    int next = charIndex + 1;
    char c = chars[charIndex];
    if (c == '*') {
      if ((next < chars.length) && (chars[next] == '*')) {
        // '**'
        next = next + 1;
        if ((next < chars.length) && (chars[next] == '/')) {
          // '**/'
          next = next + 1;
          buffer.append("(|.*[/\\\\])");
          return next;
        }
        buffer.append(".*");
        return next;
      }
      buffer.append("[^/\\\\]+");
    } else if (c == '?') {
      buffer.append("[^/\\\\]");
    } else if (c == '/') {
      buffer.append("[/\\\\]");
    } else {
      return super.process(chars, charIndex, buffer);
    }
    return next;
  }

}
