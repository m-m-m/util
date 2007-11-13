/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

/**
 * This is the test-case for {@link CharacterSequenceScanner}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CharacterSequenceScannerTest {

  private void checkSkipOver(CharacterSequenceScanner parser, String substring, boolean ignoreCase) {

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
  public void testSkipOver() {

    String substring = "xYz";
    String string = "xxYzFOOxYztheend";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(string);
    checkSkipOver(parser, substring, false);
    parser = new CharacterSequenceScanner(string.toLowerCase());
    checkSkipOver(parser, substring, true);
  }

  @Test
  public void testSkipUntil() {

    // unescaped
    CharacterSequenceScanner parser = new CharacterSequenceScanner("string");
    assertTrue(parser.skipUntil('n'));
    assertEquals(5, parser.getCurrentIndex());
    assertEquals('g', parser.next());

    // escaped
    String end = "12345";
    parser = new CharacterSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    assertEquals('\"', parser.next());
    assertTrue(parser.skipUntil('\"', '\\'));
    assertTrue(parser.expect(end, false));
  }

  @Test
  public void testReadUntil() {

    // unescaped
    CharacterSequenceScanner parser = new CharacterSequenceScanner("string");
    assertEquals("stri", parser.readUntil('n', false));
    assertEquals(5, parser.getCurrentIndex());
    assertEquals('g', parser.next());

    // escaped
    String end = "12345";
    parser = new CharacterSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    assertEquals('\"', parser.next());
    assertEquals("Quotet text with \" inside!", parser.readUntil('\"', true, '\\'));
    assertTrue(parser.expect(end, false));
  }

  @Test
  public void testExpect() {

    // positive test
    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(start + middle + end);
    assertTrue(parser.expect(start, false));
    assertTrue(parser.expect(middle.toUpperCase(Locale.ENGLISH), true));
    assertTrue(parser.expect(end.toLowerCase(Locale.ENGLISH), true));
    assertFalse(parser.hasNext());
    // negative test
    String string = "string";
    parser = new CharacterSequenceScanner(string);
    assertFalse(parser.expect("strign", false));
    assertEquals(4, parser.getCurrentIndex());
  }

  @Test
  public void testBasic() {

    String string = "string";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(string);
    assertEquals(0, parser.getCurrentIndex());
    assertEquals(string, parser.getOriginalString());
    assertEquals(string.length(), parser.getLength());
    assertTrue(parser.hasNext());
    assertEquals('s', parser.peek());
    assertEquals(0, parser.getCurrentIndex());
    assertEquals('s', parser.next());
    assertEquals(1, parser.getCurrentIndex());
  }

  @Test
  public void testSubstring() {

    String string = "string";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(string);
    for (int i = 0; i <= string.length(); i++) {
      for (int j = i; j <= string.length(); j++) {
        // System.out.println(i + "," + j + ":" + string.substring(i, j));
        assertEquals(string.substring(i, j), parser.substring(i, j));
      }
    }
  }

  @Test
  public void testNext() {

    String string = "0123456789";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(string);
    for (int i = 0; i < 10; i++) {
      assertTrue(parser.hasNext());
      char c = parser.next();
      int expected = '0' + i;
      assertEquals(expected, (int) c);
    }
    assertFalse(parser.hasNext());
    try {
      parser.next();
      fail("Exception expected!");
    } catch (Exception e) {
    }
    assertEquals('\0', parser.forceNext());
  }

  @Test
  public void testGetReplaced() {

    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharacterSequenceScanner parser = new CharacterSequenceScanner(start + middle + end);
    String middleNew = "universe";
    String replaced = parser.getReplaced(middleNew, start.length(), start.length()
        + middle.length());
    assertEquals(start + middleNew + end, replaced);
  }

}