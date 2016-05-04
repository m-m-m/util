/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.scanner.api.CharScannerSyntax;

/**
 * This is the test-case for {@link CharSequenceScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CharSequenceScannerTest {

  private void checkSkipOver(CharSequenceScanner parser, String substring, boolean ignoreCase) {

    boolean found = parser.skipOver(substring, ignoreCase);
    Assert.assertTrue(found);
    found = parser.expect("FOO", ignoreCase);
    Assert.assertTrue(found);
    found = parser.skipOver(substring, ignoreCase);
    Assert.assertTrue(found);
    String rest = parser.read(Integer.MAX_VALUE);
    if (ignoreCase) {
      rest = rest.toLowerCase();
    }
    Assert.assertEquals("theend", rest);
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
    Assert.assertTrue(parser.skipUntil('n'));
    Assert.assertEquals(5, parser.getCurrentIndex());
    Assert.assertEquals('g', parser.next());

    // escaped
    String end = "12345";
    parser = new CharSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    Assert.assertEquals('\"', parser.next());
    Assert.assertTrue(parser.skipUntil('\"', '\\'));
    Assert.assertTrue(parser.expect(end, false));
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean)}.
   */
  @Test
  public void testReadUntil() {

    CharSequenceScanner scanner;
    // not escaped
    scanner = new CharSequenceScanner("string");
    Assert.assertEquals("stri", scanner.readUntil('n', false));
    Assert.assertEquals(5, scanner.getCurrentIndex());
    Assert.assertEquals('g', scanner.next());

    // no EOF
    scanner.setCurrentIndex(0); // also test reset
    Assert.assertSame(null, scanner.readUntil('x', false));
    Assert.assertFalse(scanner.hasNext());

    // EOF
    scanner.setCurrentIndex(0); // also test reset
    Assert.assertEquals("string", scanner.readUntil('x', true));
    Assert.assertFalse(scanner.hasNext());

  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, char)}.
   */
  @Test
  public void testReadUntilWithEscape() {

    CharSequenceScanner scanner;
    // test with different escape and stop char
    scanner = new CharSequenceScanner("bla\"Quotet text with \\\" and \\\\ inside!\"bla");
    Assert.assertEquals("bla", scanner.readUntil('"', false));
    Assert.assertEquals("Quotet text with \" and \\ inside!", scanner.readUntil('"', false, '\\'));
    Assert.assertEquals("bla", scanner.readUntil('\0', true));

    // test with same escape and stop char
    scanner = new CharSequenceScanner("bla\"Quotet text with \"\" and \\ inside!\"bla");
    Assert.assertEquals("bla", scanner.readUntil('"', false));
    Assert.assertEquals("Quotet text with \" and \\ inside!", scanner.readUntil('"', false, '"'));
    Assert.assertEquals("bla", scanner.readUntil('\0', true));

  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)}.
   */
  @Test
  public void testReadUntilWithSyntax() {

    CharSequenceScanner scanner;
    // escaped
    String end = "12345";
    scanner = new CharSequenceScanner("\"Quotet text with \\\" inside!\"" + end);
    Assert.assertEquals('\"', scanner.next());
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    String result = scanner.readUntil('\"', false, syntax);
    Assert.assertEquals("Quotet text with \" inside!", result);
    Assert.assertTrue(scanner.expect(end, false));

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

      @Override
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
    scanner = new CharSequenceScanner("Hi \"$\"quote$\"\", 'a''l\\t' and \\\"esc\\'&lt;&gt;&lt;x&gt;!");
    result = scanner.readUntil('!', false, syntax);
    Assert.assertEquals("Hi \"quote\", a'l\\t and \"esc'<><x>", result);
    Assert.assertFalse(scanner.hasNext());

    // with acceptEof
    scanner = new CharSequenceScanner("Hi 'qu''ote'");
    result = scanner.readUntil('\0', true, syntax);
    Assert.assertEquals("Hi qu'ote", result);
    Assert.assertFalse(scanner.hasNext());
  }

  private CharSequenceScanner check(char stop, boolean acceptEof, CharScannerSyntax syntax, String expected,
      String input) {

    CharSequenceScanner scanner = new CharSequenceScanner(input);
    String output = scanner.readUntil(stop, acceptEof, syntax);
    Assert.assertEquals(expected, output);
    return scanner;
  }

  @Test
  public void testExpect() {

    // positive test
    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharSequenceScanner parser = new CharSequenceScanner(start + middle + end);
    Assert.assertTrue(parser.expect(start, false));
    Assert.assertTrue(parser.expect(middle.toUpperCase(Locale.ENGLISH), true));
    Assert.assertTrue(parser.expect(end.toLowerCase(Locale.ENGLISH), true));
    Assert.assertFalse(parser.hasNext());
    // negative test
    String string = "string";
    parser = new CharSequenceScanner(string);
    Assert.assertFalse(parser.expect("strign", false));
    Assert.assertEquals(4, parser.getCurrentIndex());
  }

  @Test
  public void testBasic() {

    String string = "string";
    CharSequenceScanner parser = new CharSequenceScanner(string);
    Assert.assertEquals(0, parser.getCurrentIndex());
    Assert.assertEquals(string, parser.getOriginalString());
    Assert.assertEquals(string.length(), parser.getLength());
    Assert.assertTrue(parser.hasNext());
    Assert.assertEquals('s', parser.peek());
    Assert.assertEquals(0, parser.getCurrentIndex());
    Assert.assertEquals('s', parser.next());
    Assert.assertEquals(1, parser.getCurrentIndex());
  }

  @Test
  public void testSubstring() {

    String string = "string";
    CharSequenceScanner parser = new CharSequenceScanner(string);
    for (int i = 0; i <= string.length(); i++) {
      for (int j = i; j <= string.length(); j++) {
        // System.out.println(i + "," + j + ":" + string.substring(i, j));
        Assert.assertEquals(string.substring(i, j), parser.substring(i, j));
      }
    }
  }

  @Test
  public void testNext() {

    String string = "0123456789";
    CharSequenceScanner parser = new CharSequenceScanner(string);
    for (int i = 0; i < 10; i++) {
      Assert.assertTrue(parser.hasNext());
      char c = parser.next();
      int expected = '0' + i;
      Assert.assertEquals(expected, c);
    }
    Assert.assertFalse(parser.hasNext());
    try {
      parser.next();
      fail("Exception expected!");
    } catch (Exception e) {
    }
    Assert.assertEquals('\0', parser.forceNext());
  }

  @Test
  public void testGetReplaced() {

    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    CharSequenceScanner parser = new CharSequenceScanner(start + middle + end);
    String middleNew = "universe";
    String replaced = parser.getReplaced(middleNew, start.length(), start.length() + middle.length());
    Assert.assertEquals(start + middleNew + end, replaced);
  }

}
