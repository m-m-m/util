/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pattern;

import java.util.regex.Pattern;

import org.junit.Test;

import static net.sf.mmm.util.pattern.PatternAssert.*;

/**
 * This is the test-case for {@link PathPatternCompiler}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PathPatternCompilerTest {

  @Test
  public void testGlobPatterns() {
    
    PatternCompiler compiler = new PathPatternCompiler();
    Pattern pattern = compiler.compile("/*/b?/**/c.d");
    assertMatches(pattern, "/aXx/bx/foo/bar/c.d");
    assertMatches(pattern, "/./by/c.d");
    assertMatchesNot(pattern, "/by/c.d");
    assertMatchesNot(pattern, "/./by/foo/cxd");
  }

  @Test
  public void testEscaping() {
    
    PatternCompiler compiler = new PathPatternCompiler();
    String string = ")]}>|<{[(&%$§\"'=!\\/-+_#^,;@~´`";
    Pattern pattern = compiler.compile(string);
    assertMatches(pattern, string);
  }
  
}
