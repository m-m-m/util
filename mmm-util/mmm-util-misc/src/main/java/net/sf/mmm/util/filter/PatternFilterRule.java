/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.pattern.base.RegexInfixPatternCompiler;

/**
 * This is an implementation of the {@link FilterRule} interface that matches
 * using a regex {@link Pattern}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PatternFilterRule implements FilterRule {

  /** @see #accept(String) */
  private final Pattern pattern;

  /** @see #accept(String) */
  private final Boolean result;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern a file has to match in order to activate this
   *        rule. Before this given string is compiled via
   *        {@link Pattern#compile(String)} the following manipulation is
   *        performed: If the pattern string does NOT start with the character
   *        <code>^</code> the implicit prefix <code>.*</code> is added. If
   *        the pattern does NOT end with the character <code>$</code> the
   *        implicit suffix <code>.*</code> is appended.
   * @param resultOnMatch is the result {@link #accept(String) returned} if the
   *        pattern matches.
   */
  public PatternFilterRule(String pattern, boolean resultOnMatch) {

    this(RegexInfixPatternCompiler.INSTANCE.compile(pattern), resultOnMatch);
  }

  /**
   * The constructor.
   * 
   * @param pattern is the pattern a file has to match in order to activate this
   *        rule.
   * @param resultOnMatch is the result {@link #accept(String) returned} if the
   *        pattern matches.
   */
  public PatternFilterRule(Pattern pattern, boolean resultOnMatch) {

    super();
    this.pattern = pattern;
    this.result = Boolean.valueOf(resultOnMatch);
  }

  /**
   * {@inheritDoc}
   */
  public Boolean accept(String string) {

    Matcher matcher = this.pattern.matcher(string);
    if (matcher.matches()) {
      return this.result;
    }
    return null;
  }

}
