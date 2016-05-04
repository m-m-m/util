/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern.base;

import static net.sf.mmm.util.pattern.base.PatternAssert.assertMatches;
import static net.sf.mmm.util.pattern.base.PatternAssert.assertMatchesNot;

import java.util.regex.Pattern;

import org.junit.Test;

import net.sf.mmm.util.pattern.api.PatternCompiler;

/**
 * This is the test-case for {@link GlobPatternCompiler}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class GlobPatternCompilerTest {

  @Test
  public void testGlobPatterns() {

    PatternCompiler compiler = new GlobPatternCompiler();
    Pattern pattern = compiler.compile("a*b.c?d");
    assertMatches(pattern, "ab.cxd");
    assertMatches(pattern, "a/x/b.cXd");
    assertMatchesNot(pattern, "abxcxd");
  }

  @Test
  public void testEscaping() {

    PatternCompiler compiler = new GlobPatternCompiler();
    String string = ")]}>|<{[(&%$§\"'=!\\/-+_#^,;@~´`";
    Pattern pattern = compiler.compile(string);
    assertMatches(pattern, string);
  }

}
