/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This implementation of the {@link PatternCompiler} interface compiles the <code>pattern</code> given as
 * string in a way similar to GNU-utilities like <code>sed</code> or <code>grep</code>. This means that if no
 * leading "^" (or ".*") or no trailing "$" (or ".*") is present, an according ".*" prefix and suffix is added
 * implicit. This causes that "\.xml$" matches "config.xml" and "^/etc/" matches "/etc/passwd". <br>
 * <b>ATTENTION:</b><br>
 * On the {@link Pattern#matcher(CharSequence) matcher} of the produced {@link Pattern} you may want to use
 * {@link java.util.regex.Matcher#find()} instead of {@link java.util.regex.Matcher#matches()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RegexInfixPatternCompiler extends RegexPatternCompiler {

  /** A singleton instance of this implementation. */
  public static final PatternCompiler INSTANCE = new RegexInfixPatternCompiler();

  /**
   * The constructor.
   */
  public RegexInfixPatternCompiler() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param flags are the {@link Pattern#compile(String, int) compiler flags}.
   */
  public RegexInfixPatternCompiler(int flags) {

    super(flags);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Pattern compile(String pattern) throws IllegalArgumentException {

    StringBuilder buffer = new StringBuilder(pattern.length() + 4);
    if (!pattern.startsWith("^") && !pattern.startsWith(".*")) {
      buffer.append(".*");
    }
    buffer.append(pattern);
    if (!pattern.endsWith("$") && !pattern.endsWith(".*")) {
      buffer.append(".*");
    }
    return super.compile(buffer.toString());
  }

}
