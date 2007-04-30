/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
   * @param pattern
   *        is the pattern a file has to match in order to activate this rule.
   * @param resultOnMatch
   *        is the result {@link #accept(String) returned} if the pattern
   *        matches.
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
