/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase test-case} for
 * {@link net.sf.mmm.util.NumericUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringParserTest extends TestCase {

  /**
   * The constructor
   */
  public StringParserTest() {

    super();
  }

  private void checkSkipOver(StringParser parser, String substring, boolean ignoreCase) {

    boolean found = parser.skipOver(substring, ignoreCase);
    assertTrue(found);
    found = parser.expect("FOO", ignoreCase);
    assertTrue(found);
    found = parser.skipOver(substring, ignoreCase);
    assertTrue(found);
    String rest = parser.read(Integer.MAX_VALUE);
    if (ignoreCase) {
      rest = rest.toLowerCase();
    }
    assertEquals("theend", rest);
  }

  @Test
  public void testReadOver() {

    String substring = "xYz";
    String string = "xxYzFOOxYztheend";
    StringParser parser = new StringParser(string);
    checkSkipOver(parser, substring, false);
    parser = new StringParser(string.toLowerCase());
    checkSkipOver(parser, substring, true);
  }

}
