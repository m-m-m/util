/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import java.util.regex.Pattern;

import org.junit.Assert;

/**
 * This class allows junit-style assertions for matching {@link Pattern}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PatternAssert {

  /**
   * Asserts that <code>string</code> is {@link java.util.regex.Matcher#matches() matching}
   * <code>pattern</code>.
   * 
   * @param pattern is the {@link Pattern}.
   * @param string is the string to match.
   */
  public static void assertMatches(Pattern pattern, String string) {

    Assert.assertTrue("String '" + string + "' should match pattern '" + pattern.pattern() + "'!",
        pattern.matcher(string).matches());
  }

  /**
   * Asserts that <code>string</code> is NOT {@link java.util.regex.Matcher#matches() matching}
   * <code>pattern</code>.
   * 
   * @param pattern is the {@link Pattern}.
   * @param string is the string that should NOT match.
   */
  public static void assertMatchesNot(Pattern pattern, String string) {

    Assert.assertFalse("String '" + string + "' should NOT match pattern '" + pattern.pattern() + "'!", pattern
        .matcher(string).matches());
  }

}
