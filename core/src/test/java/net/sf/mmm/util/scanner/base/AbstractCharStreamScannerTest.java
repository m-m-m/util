/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.filter.base.ListCharFilter;
import net.sf.mmm.util.scanner.api.CharScannerSyntax;
import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * This is the abstract test for implementations of {@link CharStreamScanner}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractCharStreamScannerTest extends Assertions {

  protected CharStreamScanner scanner(String string) {

    return scanner(string, false);
  }

  protected abstract CharStreamScanner scanner(String string, boolean lookahead);

  @Test
  public void testSkipWhile() {

    // given
    String string = "abc def  ghi";
    // when
    CharStreamScanner scanner = scanner(string);
    int skipCount = scanner.skipWhile(' ');
    // then
    assertThat(skipCount).isEqualTo(0);
    // and when
    String read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("abc");
    // and when
    skipCount = scanner.skipWhile(' ');
    // then
    assertThat(skipCount).isEqualTo(1);
    // and when
    read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("def");
    // and when
    skipCount = scanner.skipWhile(' ');
    // then
    assertThat(skipCount).isEqualTo(2);
    // and when
    read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("ghi");
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testSkipWhileFilter() {

    // given
    String string = "abc def \tghi\t\t \t";
    CharFilter filter = new ListCharFilter(true, ' ', '\t');
    // when
    CharStreamScanner scanner = scanner(string);
    int skipCount = scanner.skipWhile(filter);
    // then
    assertThat(skipCount).isEqualTo(0);
    // and when
    String read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("abc");
    // and when
    skipCount = scanner.skipWhile(filter);
    // then
    assertThat(skipCount).isEqualTo(1);
    // and when
    read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("def");
    // and when
    skipCount = scanner.skipWhile(filter);
    // then
    assertThat(skipCount).isEqualTo(2);
    // and when
    read = scanner.read(3);
    // then
    assertThat(read).isEqualTo("ghi");
    // and when
    skipCount = scanner.skipWhile(filter, 3);
    // then
    assertThat(skipCount).isEqualTo(3);
    // and when
    skipCount = scanner.skipWhile(filter, 5);
    // then
    assertThat(skipCount).isEqualTo(1);
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testSkipOver() {

    // given
    String substring = "xYz";
    String string = "xxYzFOOxYztheend";
    // when
    CharStreamScanner scanner = scanner(string, true);
    // then
    checkSkipOver(scanner, substring, false);
    // and when
    scanner = scanner(string.toLowerCase(), true);
    // then
    checkSkipOver(scanner, substring, true);
  }

  private void checkSkipOver(CharStreamScanner scanner, String substring, boolean ignoreCase) {

    assertThat(scanner.skipOver(substring, ignoreCase)).isTrue();
    assertThat(scanner.expect("FOO", ignoreCase)).isTrue();
    assertThat(scanner.skipOver(substring, ignoreCase)).isTrue();
    String rest = scanner.read(Integer.MAX_VALUE);
    if (ignoreCase) {
      rest = rest.toLowerCase();
    }
    assertThat(rest).isEqualTo("theend");
  }

  @Test
  public void testSkipUntil() {

    // unescaped
    CharStreamScanner scanner = scanner("string");
    assertThat(scanner.skipUntil('n')).isTrue();
    assertThat(scanner.next()).isEqualTo('g');
    assertThat(scanner.hasNext()).isFalse();

    // escaped
    String end = "12345";
    scanner = scanner("\"Quotet text with \\\" inside!\"" + end);
    assertThat(scanner.next()).isEqualTo('\"');
    assertThat(scanner.skipUntil('\"', '\\')).isTrue();
    assertThat(scanner.expect(end, false)).isTrue();
  }

  /**
   * Tests {@link CharStreamScanner#readUntil(char, boolean)}.
   */
  @Test
  public void testReadUntil() {

    // given
    String string = "string";
    CharStreamScanner scanner;
    // when (not escaped)
    scanner = scanner(string);
    // then
    assertThat(scanner.readUntil('n', false)).isEqualTo("stri");
    assertThat(scanner.next()).isEqualTo('g');
    assertThat(scanner.hasNext()).isFalse();

    // and when (no EOF)
    scanner = scanner(string);
    // then
    assertThat(scanner.readUntil('x', false)).isNull();
    assertThat(scanner.hasNext()).isFalse();

    // and when (EOF)
    scanner = scanner(string);
    // then
    assertThat(scanner.readUntil('x', true)).isEqualTo(string);
    assertThat(scanner.hasNext()).isFalse();

  }

  /**
   * Tests {@link CharStreamScanner#readUntil(char, boolean, char)}.
   */
  @Test
  public void testReadUntilWithEscape() {

    CharStreamScanner scanner;
    // when (test with different escape and stop char)
    scanner = scanner("bla\"Quotet text with \\\" and \\\\ inside!\"bla");
    // then
    assertThat(scanner.readUntil('"', false)).isEqualTo("bla");
    assertThat(scanner.readUntil('"', false, '\\')).isEqualTo("Quotet text with \" and \\ inside!");
    assertThat(scanner.readUntil('\0', true)).isEqualTo("bla");

    // and when (test with same escape and stop char)
    scanner = scanner("bla\"Quotet text with \"\" and \\ inside!\"bla");
    // then
    assertThat(scanner.readUntil('"', false)).isEqualTo("bla");
    assertThat(scanner.readUntil('"', false, '"')).isEqualTo("Quotet text with \" and \\ inside!");
    assertThat(scanner.readUntil('\0', true)).isEqualTo("bla");

  }

  /**
   * Tests {@link CharStreamScanner#readUntil(CharFilter, boolean, String, boolean, boolean)}.
   */
  @Test
  public void testReadUntilWithStopString() {

    // given
    CharFilter filter = CharFilter.NEWLINE_FILTER;
    String string = "/* comment */\n" + //
        "  /*\n" + //
        "   *   Line  1.    \n" + //
        "   * Line2  \n" + //
        "   */";
    // when
    CharStreamScanner scanner = scanner(string, true);
    // then
    assertThat(scanner.expect("/*")).isTrue();
    assertThat(scanner.readUntil(filter, false, "*/", false, true)).isEqualTo("comment");
    assertThat(scanner.expect("*/")).isTrue();
    assertThat(scanner.readLine()).isEmpty();
    assertThat(scanner.readUntil(filter, false, "/*", false, true)).isEmpty();
    assertThat(scanner.expect("/*")).isTrue();
    assertThat(scanner.skipUntil('*')).isTrue();
    assertThat(scanner.readUntil(filter, false, "*/", false, true)).isEqualTo("Line  1.");
    assertThat(scanner.skipUntil('*')).isTrue();
    assertThat(scanner.readUntil(filter, false, "*/", false, true)).isEqualTo("Line2");
    assertThat(scanner.readLine()).isEmpty();
    assertThat(scanner.readUntil(filter, false, "*/", false, false)).isEqualTo("   ");
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with quote and backslash as
   * escape char.
   */
  @Test
  public void testReadUntilWithSyntaxBackslashEscaped() {

    // given
    String end = "12345";
    String string = "\"Quotet text with \\\" inside!\"" + end;
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.next()).isEqualTo('\"');
    String result = scanner.readUntil('\"', false, syntax);
    assertThat(result).isEqualTo("Quotet text with \" inside!");
    assertThat(scanner.expect(end, false)).isTrue();
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with all quote settings to
   * single quote.
   */
  @Test
  public void testReadUntilWithSyntaxSingleQuotes() {

    // given
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    syntax.setQuote('\'');
    syntax.setQuoteEscape('\'');
    // when + then
    check('\0', true, syntax, "''a''''b'''c'", "a'b'c");
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with all quote settings to
   * single quote and {@link CharScannerSyntax#isQuoteEscapeLazy() lazy quote}.
   */
  @Test
  public void testReadUntilWithSyntaxSingleQuotesLazy() {

    // given
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    syntax.setQuote('\'');
    syntax.setQuoteEscape('\'');
    syntax.setQuoteEscapeLazy(true);
    // when + then
    check('\0', true, syntax, "''a''''b'''c'", "'a''b'c");
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with all alt-quote settings
   * to double quote.
   */
  @Test
  public void testReadUntilWithSyntaxAltDobuleQuotes() {

    // given
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    syntax.setQuote('\'');
    syntax.setQuoteEscape('\'');
    syntax.setQuoteEscapeLazy(true);
    syntax.setAltQuote('"');
    syntax.setAltQuoteEscape('"');
    // when + then
    check('\0', true, syntax, "\"\"a\"\"\"\"b\"\"\"c\"", "a\"b\"c");
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with all alt-quote settings
   * to double quote and {@link CharScannerSyntax#isAltQuoteEscapeLazy() lazy alt-quote}.
   */
  @Test
  public void testReadUntilWithSyntaxAltDobuleQuotesLazy() {

    // given
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    syntax.setEscape('\\');
    syntax.setQuote('\'');
    syntax.setQuoteEscape('\'');
    syntax.setQuoteEscapeLazy(true);
    syntax.setAltQuote('"');
    syntax.setAltQuoteEscape('"');
    syntax.setAltQuoteEscapeLazy(true);
    // when + then
    check('\0', true, syntax, "\"\"a\"\"\"\"b\"\"\"c\"", "\"a\"\"b\"c");
  }

  @Test
  public void testReadLong() {

    // given
    String string = "654321-1234567890123456789";
    // when
    CharStreamScanner scanner = scanner(string);
    long value = scanner.readLong(19);
    // then
    assertThat(value).isEqualTo(654321);
    assertThat(scanner.next()).isEqualTo('-');
    // and when
    value = scanner.readLong(19);
    // then
    assertThat(value).isEqualTo(1234567890123456789l);
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testReadDouble() {

    // given
    String string = "123456789-987654321+0.123e-10xyz";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.readDouble()).isEqualTo(123456789d);
    assertThat(scanner.readDouble()).isEqualTo(-987654321d);
    assertThat(scanner.readDouble()).isEqualTo(+0.123e-10);
    try {
      scanner.readDouble();
      failBecauseExceptionWasNotThrown(NumberFormatException.class);
    } catch (NumberFormatException e) {

    }
  }

  @Test
  public void testReadFloat() {

    // given
    String string = "123456789-987654321+0.123e-10xyz";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.readFloat()).isEqualTo(123456789f);
    assertThat(scanner.readFloat()).isEqualTo(-987654321f);
    assertThat(scanner.readFloat()).isEqualTo(+0.123e-10f);
    try {
      scanner.readDouble();
      failBecauseExceptionWasNotThrown(NumberFormatException.class);
    } catch (NumberFormatException e) {

    }
  }

  /**
   * Tests {@link CharSequenceScanner#readUntil(char, boolean, CharScannerSyntax)} with a
   * {@link CharScannerSyntax} using all features in combination.
   */
  @Test
  public void testReadUntilWithSyntaxFull() {

    // given (full syntax)
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax() {

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
    syntax.setAltQuoteStart('\'');
    syntax.setAltQuoteEnd('\'');
    syntax.setAltQuoteEscape('\'');
    syntax.setEntityStart('&');
    syntax.setEntityEnd(';');

    // when
    CharStreamScanner scanner = scanner("Hi \"$\"quote$\"\", 'a''l\\t' and \\\"esc\\'&lt;&gt;&lt;x&gt;!");
    String result = scanner.readUntil('!', false, syntax);
    // then
    assertThat(result).isEqualTo("Hi \"quote\", a'l\\t and \"esc'<><x>");
    assertThat(scanner.hasNext()).isFalse();

    // and when (with acceptEof)
    scanner = scanner("Hi 'qu''ote'");
    result = scanner.readUntil('\0', true, syntax);
    // then
    assertThat(result).isEqualTo("Hi qu'ote");
    assertThat(scanner.hasNext()).isFalse();
  }

  private CharStreamScanner check(char stop, boolean acceptEot, CharScannerSyntax syntax, String input, String expected) {

    CharStreamScanner scanner = scanner(input);
    String output = scanner.readUntil(stop, acceptEot, syntax);
    assertThat(output).isEqualTo(expected);
    return scanner;
  }

  @Test
  public void testExpectPositive() {

    // given
    String start = "hello ";
    String middle = "world";
    String end = " this is cool!";
    // when
    CharStreamScanner scanner = scanner(start + middle + end);
    // then
    assertThat(scanner.expect(start, false)).isTrue();
    assertThat(scanner.expect(middle.toUpperCase(Locale.ENGLISH), true)).isTrue();
    assertThat(scanner.expect(end.toLowerCase(Locale.ENGLISH), true)).isTrue();
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testExpectStrict() {

    // given
    String string = "Hello World!";
    // when
    CharStreamScanner scanner = scanner(string, true);
    // then
    assertThat(scanner.expectStrict("Hello WorlD", false)).isFalse();
    assertThat(scanner.expectStrict("Hello ", false)).isTrue();
    assertThat(scanner.expectStrict("WorlD!", true)).isTrue();
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testExpectNegative() {

    // given
    String string = "string";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.expect("strign", false)).isFalse();
    assertThat(scanner.read(2)).isEqualTo("ng");
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testNext() {

    // given
    String string = "0123456789";
    // when
    CharStreamScanner scanner = scanner(string);
    for (int i = 0; i < 10; i++) {
      assertThat(scanner.hasNext()).isTrue();
      char c = scanner.next();
      char expected = (char) ('0' + i);
      // then
      assertThat(c).isEqualTo(expected);
    }
    assertThat(scanner.hasNext()).isFalse();
    // and when
    try {
      scanner.next();
      failBecauseExceptionWasNotThrown(Exception.class);
    } catch (Exception e) {
    }
    // then
    assertThat(scanner.forceNext()).isEqualTo('\0');
  }

  @Test
  public void testReadWhile() {

    // given
    String string = "abc def  ghi";
    CharFilter textFilter = CharFilter.ASCII_LETTER_FILTER;
    CharFilter spaceFilter = CharFilter.WHITESPACE_FILTER;
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.readWhile(textFilter)).isEqualTo("abc");
    assertThat(scanner.readWhile(textFilter)).isEmpty();
    assertThat(scanner.readWhile(textFilter, 0)).isEmpty();
    assertThat(scanner.readWhile(spaceFilter)).isEqualTo(" ");
    assertThat(scanner.readWhile(textFilter)).isEqualTo("def");
    assertThat(scanner.readWhile(spaceFilter)).isEqualTo("  ");
    assertThat(scanner.readWhile(textFilter, 2)).isEqualTo("gh");
    assertThat(scanner.readWhile(textFilter, 2)).isEqualTo("i");
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testReadLine() {

    // given
    String string = "abc\ndef\rghi\r\njkl\n\rend";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.readLine()).isEqualTo("abc");
    assertThat(scanner.readLine()).isEqualTo("def");
    assertThat(scanner.readLine()).isEqualTo("ghi");
    assertThat(scanner.readLine()).isEqualTo("jkl");
    assertThat(scanner.readLine()).isEmpty();
    assertThat(scanner.readLine()).isEqualTo("end");
  }

  @Test
  public void testReadLineWithTrim() {

    // given
    String string = "  ab c \ndef\r ghi\r\nj k l\n \r \n  \r\n   end";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.readLine(true)).isEqualTo("ab c");
    assertThat(scanner.readLine(true)).isEqualTo("def");
    assertThat(scanner.readLine(true)).isEqualTo("ghi");
    assertThat(scanner.readLine(true)).isEqualTo("j k l");
    assertThat(scanner.readLine(true)).isEmpty();
    assertThat(scanner.readLine(true)).isEmpty();
    assertThat(scanner.readLine(true)).isEmpty();
    assertThat(scanner.readLine(true)).isEqualTo("end");
  }

  @Test
  public void testReadDigit() {

    // given
    String string = "01234567890a ";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    for (int i = 0; i < 10; i++) {
      assertThat(scanner.readDigit()).isEqualTo(i);
    }
    assertThat(scanner.readDigit()).isEqualTo(0);
    assertThat(scanner.readDigit()).isEqualTo(-1);
    assertThat(scanner.next()).isEqualTo('a');
    assertThat(scanner.readDigit()).isEqualTo(-1);
  }

  @Test
  public void testPeek() {

    // given
    String string = "abc";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.peek()).isEqualTo('a');
    assertThat(scanner.forcePeek()).isEqualTo('a');
    assertThat(scanner.next()).isEqualTo('a');

    assertThat(scanner.peek()).isEqualTo('b');
    assertThat(scanner.forcePeek()).isEqualTo('b');
    assertThat(scanner.forceNext()).isEqualTo('b');

    assertThat(scanner.peek()).isEqualTo('c');
    assertThat(scanner.forcePeek()).isEqualTo('c');
    assertThat(scanner.next()).isEqualTo('c');

    assertThat(scanner.forcePeek()).isEqualTo('\0');
    assertThat(scanner.forceNext()).isEqualTo('\0');
  }

  @Test
  public void testExpect() {

    // given
    String string = "public static final String foo;";
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.expect("public", false)).isTrue();
    assertThat(scanner.expect('$')).isFalse();
    assertThat(scanner.expect(' ')).isTrue();
    assertThat(scanner.expect("StATiC", true)).isTrue();
    assertThat(scanner.expect(' ')).isTrue();
    assertThat(scanner.expect("FINAL", false)).isFalse();
    assertThat(scanner.expect("FINAL", true)).isTrue();
    assertThat(scanner.expect(' ')).isTrue();
    assertThat(scanner.expect("string", false)).isFalse();
    assertThat(scanner.expect("String", false)).isTrue();
    assertThat(scanner.forceNext()).isEqualTo(' ');
    assertThat(scanner.expect("banana", true)).isFalse();
    assertThat(scanner.expect("foo", false)).isTrue();
    assertThat(scanner.forceNext()).isEqualTo(';');
    assertThat(scanner.hasNext()).isFalse();
  }

  @Test
  public void testEmpty() {

    // given
    String string = "";
    SimpleCharScannerSyntax syntax = new SimpleCharScannerSyntax();
    CharFilter filter = CharFilter.ACCEPT_ALL_FILTER;
    // when
    CharStreamScanner scanner = scanner(string);
    // then
    assertThat(scanner.forcePeek()).isEqualTo('\0');
    assertThat(scanner.forceNext()).isEqualTo('\0');
    assertThat(scanner.readDigit()).isEqualTo(-1);
    assertThat(scanner.read(1)).isEmpty();
    assertThat(scanner.readLine()).isNull();
    assertThat(scanner.readUntil(' ', true)).isEmpty();
    assertThat(scanner.readUntil(' ', false)).isNull();
    assertThat(scanner.readUntil(' ', true, syntax)).isEmpty();
    assertThat(scanner.readUntil(' ', false, syntax)).isNull();
    assertThat(scanner.readUntil(' ', true, '\\')).isEmpty();
    assertThat(scanner.readUntil(' ', false, '\\')).isNull();
    assertThat(scanner.readUntil(filter, true)).isEmpty();
    assertThat(scanner.readUntil(filter, false)).isNull();
    assertThat(scanner.readWhile(filter)).isEmpty();
    assertThat(scanner.skipUntil(' ')).isFalse();
    assertThat(scanner.skipUntil(' ', '\\')).isFalse();
    assertThat(scanner.skipWhile(' ')).isEqualTo(0);
    assertThat(scanner.skipWhile(filter)).isEqualTo(0);
    assertThat(scanner.skipWhileAndPeek(filter)).isEqualTo('\0');
    assertThat(scanner.skipWhileAndPeek(filter, 10)).isEqualTo('\0');
    assertThat(scanner.expect(' ')).isFalse();
    assertThat(scanner.expect("Text", true)).isFalse();
  }

  /**
   * Tests {@link CharStreamScanner#readJavaStringLiteral()}.
   */
  @Test
  public void testReadJavaStringLiteral() {

    // given
    String string = "\"Hi \\\"\\176\\477\\579\\u2022\\uuuuu2211\\\"\\n\"";
    // when
    CharStreamScanner scanner = scanner(string);
    String result = scanner.readJavaStringLiteral();
    // then
    assertThat(result).isEqualTo("Hi \"~'7/9•∑\"\n");
    assertThat(result).isEqualTo("Hi \"\176\477\579\u2022\uuuuu2211\"\n");
    assertThat(scanner.hasNext()).isFalse();
  }
}
