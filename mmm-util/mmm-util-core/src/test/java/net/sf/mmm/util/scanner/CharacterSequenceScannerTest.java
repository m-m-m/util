/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Test;

/**
 * This is the test-case for {@link CharSequenceScanner}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CharacterSequenceScannerTest {

  private void checkSkipOver(CharSequenceScanner parser, String substring, boolean ignoreCase) {

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
    CharSequenceScanner parser = new CharSequenceScanner(string);
    checkSkipOver(parser, substring, false);
    parser = new CharSequenceScanner(string.toLowerCase());
    checkSkipOver(parser, substring, true);
  }

  @Test
  public void testSkipUntil() {

    // unescaped
    CharSequenceScanner parser = new CharSequenceScanner("string");
    assertTrue(parser.skipUntil('n'));
    assertEquals(5, parser.getCurrentIndex());
    assertEquals('g', parser.next());

    // escaped
    String end = "12345";
    parser = new CharSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    assertEquals('\"', parser.next());
    assertTrue(parser.skipUntil('\"', '\\'));
    assertTrue(parser.expect(end, false));
  }

  @Test
  public void testReadUntil() {

    CharSequenceScanner scanner;
    // unescaped
    scanner = new CharSequenceScanner("string");
    assertEquals("stri", scanner.readUntil('n', false));
    assertEquals(5, scanner.getCurrentIndex());
    assertEquals('g', scanner.next());

    // no eof
    scanner.setCurrentIndex(0); // also test reset
    assertSame(null, scanner.readUntil('x', false));
    assertFalse(scanner.hasNext());

    // eof
    scanner.setCurrentIndex(0); // also test reset
    assertEquals("string", scanner.readUntil('x', true));
    assertFalse(scanner.hasNext());

  }

  private CharSequenceScanner check(char stop, boolean acceptEof, CharScannerSyntax syntax,
      String expected, String input) {

    CharSequenceScanner scanner = new CharSequenceScanner(input);
    String output = scanner.readUntil(stop, acceptEof, syntax);
    assertEquals(expected, output);
    return scanner;
  }

  @Test
  public void testReadUntilWithSyntax() {

    CharSequenceScanner scanner;
    // escaped
    String end = "12345";
    scanner = new CharSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    assertEquals('\"', scanner.next());
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    String result = scanner.readUntil('\"', false, syntax);
    assertEquals("Quotet text with \" inside!", result);
    assertTrue(scanner.expect(end, false));

    // quote escape
    syntax.setQuote('\'');
    syntax.setQuoteEscape('\'');
    check('\0', true, syntax, "a'b'c", "''a''''b'''c'");

    // quote escape lazy
    syntax.setQuoteEscapeLazy(true);
    check('\0', true, syntax, "'a''b'c", "''a''''b'''c'");

    // alt quote escape
    syntax.setAltQuote('"');
    syntax.setAltQuoteEscape('"');
    check('\0', true, syntax, "a\"b\"c", "\"\"a\"\"\"\"b\"\"\"c\"");

    // quote escape lazy
    syntax.setAltQuoteEscapeLazy(true);
    check('\0', true, syntax, "\"a\"\"b\"c", "\"\"a\"\"\"\"b\"\"\"c\"");

    // full syntax
    syntax = new SimpleCharScannerSyntax() {

      public String resolveEntity(String entity) {

        if ("lt".equals(entity)) {
          return "<";
        } else if ("gt".equals(entity)) {
          return ">";
        }
        return super.resolveEntity(entity);
      }
    };
    syntax.setEscape('\\');
    syntax.setQuote('"');
    syntax.setQuoteEscape('$');
    syntax.setAltQuote('\'');
    syntax.setAltQuoteEscape('\'');
    syntax.setEntityStart('&');
    syntax.setEntityEnd(';');
    scanner = new CharSequenceScanner(
        "Hi \"$\"quote$\"\", 'a''l\\t' and \\\"esc\\'&lt;&gt;&lt;x&gt;!");
    result = scanner.readUntil('!', false, syntax);
    assertEquals("Hi \"quote\", a'l\\t and \"esc'<><x>", result);
    assertFalse(scanner.hasNext());

    // with acceptEof
    scanner = new CharSequenceScanner("Hi 'qu''ote'");
    result = scanner.readUntil('\0', true, syntax);
    assertEquals("Hi qu'ote", result);
    assertFalse(scanner.hasNext());
  }

  @Test
  public void testExpect() {

    // positive test
    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharSequenceScanner parser = new CharSequenceScanner(start + middle + end);
    assertTrue(parser.expect(start, false));
    assertTrue(parser.expect(middle.toUpperCase(Locale.ENGLISH), true));
    assertTrue(parser.expect(end.toLowerCase(Locale.ENGLISH), true));
    assertFalse(parser.hasNext());
    // negative test
    String string = "string";
    parser = new CharSequenceScanner(string);
    assertFalse(parser.expect("strign", false));
    assertEquals(4, parser.getCurrentIndex());
  }

  @Test
  public void testBasic() {

    String string = "string";
    CharSequenceScanner parser = new CharSequenceScanner(string);
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
    CharSequenceScanner parser = new CharSequenceScanner(string);
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
    CharSequenceScanner parser = new CharSequenceScanner(string);
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
    CharSequenceScanner parser = new CharSequenceScanner(start + middle + end);
    String middleNew = "universe";
    String replaced = parser.getReplaced(middleNew, start.length(), start.length()
        + middle.length());
    assertEquals(start + middleNew + end, replaced);
  }

}
